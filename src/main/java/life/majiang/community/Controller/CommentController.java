package life.majiang.community.Controller;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.exception.CustomizeErrorCodeI;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.User;
import life.majiang.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.PanelUI;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
            HttpServletRequest request){

        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(CustomizeErrorCodeI.NO_LOGIN);
        }
        System.out.println("登陆了");

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        System.out.println("zaizheli");
        commentService.insert(comment);

        return ResultDTO.okOf();
    }
}
