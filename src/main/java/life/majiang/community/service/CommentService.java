package life.majiang.community.service;

import life.majiang.community.dto.ResultDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizeErrorCodeI;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public void insert(Comment comment) {
        if (comment.getParentId()==0 || comment.getParentId() == null){
            throw new CustomizeException(CustomizeErrorCodeI.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCodeI.TARGET_PARAM_WRONG);
        }
        System.out.println("成功");
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByParentId(comment.getParentId());
            if (dbComment == null){
                throw new CustomizeException(CustomizeErrorCodeI.COMMENT_NOT_FOUNT);
            }
            commentMapper.insert(comment);
        }else {
            //回复问题
            Question question = questionMapper.getQuestionById(comment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCodeI.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.updateCommentCount(question.getId(),question.getCommentCount()+1);
        }
    }
}
