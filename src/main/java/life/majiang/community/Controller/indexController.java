package life.majiang.community.Controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class indexController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                        @RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
                        @RequestParam(value = "search",required = false)String search){
    

        //分页
        if (StringUtils.isNotBlank(search)){
            String[] split = search.split(" ");
            String collect = Arrays.stream(split).collect(Collectors.joining("|"));

            Page page=PageHelper.startPage(pageNum,pageSize);
            List<QuestionDTO> relatedQuestions = questionService.getRelatedQuestionList(search);

            PageInfo<QuestionDTO> pageInfo = new PageInfo<>(page.getResult());

            page(model, relatedQuestions, pageInfo);

            //需要展示的东西
            model.addAttribute("questions",relatedQuestions);
            model.addAttribute("search",search);
            return "index";
        }else{
            Page page=PageHelper.startPage(pageNum,pageSize);
            List<QuestionDTO> questions = questionService.list();

            PageInfo<QuestionDTO> pageInfo = new PageInfo<>(page.getResult());
            model.addAttribute("pageInfo",pageInfo);
            //获取页数
            page(model, questions, pageInfo);

            //需要展示的东西
            model.addAttribute("questions",questions);
            return "index";
        }

    }

    private <T>void page(Model model, List<T> relatedQuestions, PageInfo<T> pageInfo) {
        if (relatedQuestions.size() != 0){
            model.addAttribute("pageInfo",pageInfo);
            //获取页数
            model.addAttribute("pageNum",pageInfo.getPageNum());
            //第一页显示的条数
            model.addAttribute("pageSize",pageInfo.getPageSize());
            //是否是第一页
            model.addAttribute("isFirstPage",pageInfo.isIsFirstPage());
            //获得总页数

            model.addAttribute("totalPages",pageInfo.getPages());
            //是否是最后一页
            model.addAttribute("isLastPage",pageInfo.isIsLastPage());

        }else {
            model.addAttribute("isFirstPage","true");
            model.addAttribute("isLastPage",true);
            model.addAttribute("totalPages",1);
            model.addAttribute("pageNum",1);
        }
    }
}
