package life.majiang.community.exception;

public enum CustomizeErrorCodeI implements ICustomErrorCode {
    QUESTION_NOT_FOUND("这个问题不在了，要不要换一个试试？");

    @Override
    public String getMessage(){
        return message;
    }

    private String message;

    CustomizeErrorCodeI(String message){
        this.message = message;
    }
}
