package life.majiang.community.dto;

import life.majiang.community.exception.CustomizeErrorCodeI;
import life.majiang.community.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;
//采用这样的方法是因为静态的函数不需要实例化对象，直接ResultDTO.errorOf()就可以调用
    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCodeI noLogin) {
        return errorOf(noLogin.getCode(),noLogin.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }
}
