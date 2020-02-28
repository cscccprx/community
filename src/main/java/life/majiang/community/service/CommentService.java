package life.majiang.community.service;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.enums.NotificationStatusEnum;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.exception.CustomizeErrorCodeI;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.Notification;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId()==0 || comment.getParentId() == null){
            throw new CustomizeException(CustomizeErrorCodeI.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCodeI.TARGET_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if (dbComment == null){
                throw new CustomizeException(CustomizeErrorCodeI.COMMENT_NOT_FOUNT);
            }
            //找到问题
            Question question = questionMapper.getQuestionById(dbComment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCodeI.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);

            Long parentId = comment.getParentId();
            Comment comment1 = commentMapper.selectById(parentId);
            commentMapper.updateCommentCount(parentId, comment1.getCommentCount()+1);
            //加入通知

            createNotify(comment, dbComment.getCommentator(),commentator.getName(),dbComment.getContent(),NotificationTypeEnum.REPLY_COMMENT, question.getId());
        }else {
            //回复问题
            Question question = questionMapper.getQuestionById(comment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCodeI.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.updateCommentCount(question.getId(),question.getCommentCount()+1);
            //创建通知
            createNotify(comment,question.getCreator(), commentator.getName(),question.getTitle(),NotificationTypeEnum.REPLY_QUESTION, question.getId());
        }
    }

    private void createNotify(Comment comment, Long commentator, String commentatorName, String ourterTitle, NotificationTypeEnum replyComment, Long parentId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotifier(comment.getCommentator());
        notification.setOuterId(parentId);
        notification.setReceiver(commentator);
        notification.setType(replyComment.getType());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setOuterTitle(ourterTitle);
        notification.setNotifierName(commentatorName);
        notificationMapper.insertReply(notification);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        List<Comment> commemnts = commentMapper.getTargetByid(id, type.getType());
        if (commemnts.size()==0){
            return new ArrayList<>();
        }
        //找到去重的评论人
        Set<Long> commentators = commemnts.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<User> users = commentators.stream().map(commentator -> {
            return userMapper.findById(commentator);
        }).collect(Collectors.toList());
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        
        //找到commentDTO列表
        List<CommentDTO> commentDTOs = commemnts.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOs;
    }
}
