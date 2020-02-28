package life.majiang.community.exception;

public enum CustomizeErrorCodeI implements ICustomErrorCode {
    //2001系统级的错误
    QUESTION_NOT_FOUND(2001,"这个问题不在了，要不要换一个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题进行回复"),
    NO_LOGIN(2003,"未登录不能进行评论，请先登录"),
    SYS_ERROR(2004,"服务器冒烟了"),
    TARGET_PARAM_WRONG(2005,"评论不正确或不存在"),
    COMMENT_NOT_FOUNT(2006,"评论不存在"),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"不能读别人的信息哦~"),
    NOTIFICATION_NOT_FOUND(2009,"消息莫非不翼而飞了？");




    @Override
    public String getMessage(){
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private String message;
    private Integer code;

    CustomizeErrorCodeI(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    CustomizeErrorCodeI(String message){
        this.message = message;
    }
}
