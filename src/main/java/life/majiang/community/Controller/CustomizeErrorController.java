package life.majiang.community.Controller;

import life.majiang.community.exception.ExternError;
import life.majiang.community.exception.ExternErrorException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, Model model){
        HttpStatus status = getStatus(request);
        if (status.is4xxClientError()){
//            model.addAttribute("message", ExternError.EXTERN_ERROR_4XX);
            throw new ExternErrorException(ExternError.EXTERN_ERROR_4XX);
        }
        if (status.is5xxServerError()){
            throw new ExternErrorException(ExternError.EXTERN_ERROR_5XX);
//            model.addAttribute("message",ExternError.EXTERN_ERROR_5XX.getMessage());
        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
