package life.majiang.community.advice;

import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.exception.CustomizeErrorCodeI;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.exception.ExternError;
import life.majiang.community.exception.ExternErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model,
                        HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        ResultDTO resultDTO;
        if ("application/json".equals(contentType)){
            if (e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            }else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCodeI.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }else {
            if (e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCodeI.SYS_ERROR);
            }
            return new ModelAndView("error");
        }
    }
}
