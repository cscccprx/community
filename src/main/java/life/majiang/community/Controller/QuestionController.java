package life.majiang.community.Controller;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.service.CommentService;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,
                           Model model){
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        questionService.incView(id);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESIONT);

        model.addAttribute("relatedQuestions",relatedQuestions);
        model.addAttribute("comments",comments);
        model.addAttribute("question",questionDTO);
        return "question";

    }
}
