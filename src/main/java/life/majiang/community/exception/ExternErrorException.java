package life.majiang.community.exception;

public class ExternErrorException extends RuntimeException{
    private String message;

    public ExternErrorException(ExternError error){
        this.message = error.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
