package life.majiang.community.exception;

public class CustomizeException extends RuntimeException{
    private String message;

    public CustomizeException(ICustomErrorCode error){
        this.message = error.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
