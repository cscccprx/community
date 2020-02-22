package life.majiang.community.Controller;

import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.model.Question;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,
                           Model model){
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        return "question";

    }
}
