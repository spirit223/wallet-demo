package cc.sika.order.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 18:27
 */
@Data
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean success;
    private T Data;
    private int code;
    private String message;
}
