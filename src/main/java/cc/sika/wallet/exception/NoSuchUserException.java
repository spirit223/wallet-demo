package cc.sika.order.exception;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 18:38
 */
public class NoSuchUserException extends Exception{

    private final String message;

    public NoSuchUserException() {
        message = "未找到该用户信息";
    }

    public NoSuchUserException(String message, String message1) {
        super(message);
        this.message = message1;
    }
}
