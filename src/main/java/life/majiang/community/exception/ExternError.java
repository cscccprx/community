package life.majiang.community.exception;

public enum ExternError {
    EXTERN_ERROR_4XX("你这个请求错误啦，换个姿势？"),
    EXTERN_ERROR_5XX("不好意思服务器出错啦");

    private String message;

    public String getMessage() {
        return message;
    }

    ExternError(String message) {
        this.message = message;
    }
}
