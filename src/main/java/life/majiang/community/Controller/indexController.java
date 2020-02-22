package life.majiang.community.Controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class indexController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize){
    

        //分页
        Page page=PageHelper.startPage(pageNum,pageSize);
        List<QuestionDTO> questions = questionService.list();
//        System.out.println(questions);
        //需要封装一下，否则total 会和页大小相同
        PageInfo<QuestionDTO> pageInfo = new PageInfo<>(page.getResult());
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

        //需要展示的东西
        model.addAttribute("questions",questions);
        return "index";
    }
}
