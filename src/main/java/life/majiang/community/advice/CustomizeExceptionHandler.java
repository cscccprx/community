package life.majiang.community.advice;

import life.majiang.community.exception.CustomizeException;
import life.majiang.community.exception.ExternError;
import life.majiang.community.exception.ExternErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model){
        if (e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }else if (e instanceof ExternErrorException){
            model.addAttribute("message", e.getMessage());
        }else
            model.addAttribute("message","什么也不是哦");
        return new ModelAndView("error");
    }
}
