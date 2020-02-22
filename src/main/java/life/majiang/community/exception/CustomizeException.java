package life.majiang.community.exception;

public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;

    public CustomizeException(ICustomErrorCode error){
        this.code = error.getCode();
        this.message = error.getMessage();
    }


    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
