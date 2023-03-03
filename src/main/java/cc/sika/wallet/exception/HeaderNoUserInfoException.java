package cc.sika.order.exception;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 18:45
 */
public class HeaderNoUserInfoException extends Exception{
    private final String message;

    public HeaderNoUserInfoException() {
        this.message = "请求头中未携带用户id";
    }

    public HeaderNoUserInfoException(String message, String message1) {
        super(message);
        this.message = message1;
    }
}
