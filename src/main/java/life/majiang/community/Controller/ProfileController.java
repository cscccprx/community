package life.majiang.community.Controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    QuestionMapper questionMapper;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action, Model model,
                          @RequestParam(defaultValue = "1")Integer pageNum,
                          @RequestParam(defaultValue = "5")Integer pageSize,
                          HttpServletRequest request){
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if (user == null)
            return "redirect:/";
        //分页   user.getId() 正常应该是这个  记得更改
        Page page = PageHelper.startPage(pageNum,pageSize);
        List<Question> questionDTOList = questionMapper.getListById(user.getId());
        PageInfo<Question> pageInfo = new PageInfo<>(page.getResult());
        //这里还没有处理没发布问题
        if (page != null){
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
            model.addAttribute("questions",questionDTOList);

            model.addAttribute("action",action);
        }else {
            model.addAttribute("isFirstPage","true");
            model.addAttribute("isLastPage",true);
            model.addAttribute("totalPages",1);
            model.addAttribute("pageNum",1);
        }


        return "profile";
    }
}
