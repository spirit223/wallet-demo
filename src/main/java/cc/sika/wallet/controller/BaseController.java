package cc.sika.wallet.controller;

import cc.sika.wallet.vo.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 18:25
 */
@Slf4j
public abstract class BaseController {

    @Resource
    private HttpServletRequest request;

    /**
     * 封装成功返回的的方法, 泛型的确定见
     * {@link BaseResponse}
     *
     * @param data VO或DTO, 详见 {@link BaseResponse}
     * @return 默认成功的返回消息对象
     */
    protected <T> BaseResponse<T> responseSuccess(T data) {
        return responseSuccess(data, HttpStatus.OK.getReasonPhrase());
    }

    protected BaseResponse responseSuccess() {
        return responseSuccess(null);
    }

    /**
     * 封装成功返回的方法, 携带成功信息
     *
     * @param data    VO或DTO, 详见 {@link BaseResponse}
     * @param message 要传递的成功信息, 默认为状态码枚举对应信息
     * @return 成功的返回消息对象
     */
    protected <T> BaseResponse<T> responseSuccess(T data, String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        if (data instanceof HttpStatus) {
            response.setCode(((HttpStatus) data).value());
        } else {
            response.setData(data);
            response.setCode(HttpStatus.OK.value());
        }
        return response;
    }

    /**
     * 系统内部错误引起的失败返回, 默认500
     * @return 返回500状态码与系统错误提示
     */
    @SuppressWarnings("rawtypes")
    protected BaseResponse responseFailBySystemError() {
        return responseFail(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 通过状态枚举来设置失败返回的消息, 详见 {@link HttpStatus}
     * @param status 详见 {@link HttpStatus}
     * @return 包装了错误信息的失败返回对象
     */
    @SuppressWarnings("rawtypes")
    protected BaseResponse responseFail(HttpStatus status) {
        return responseFail(status, "");
    }

    /**
     * 通过状态枚举和自定义失败信息来设置失败返回的消息
     * @param status status – 详见 {@link HttpStatus}
     * @param message 自定义错误返回消息
     * @return 如果消息为空字符串返回 状态枚举携带的信息
     */
    @SuppressWarnings("rawtypes")
    protected BaseResponse responseFail(HttpStatus status, String message) {
        BaseResponse failResponse = new BaseResponse();
        failResponse.setSuccess(false);
        failResponse.setCode(status.value());
        if (StringUtils.hasText(message)) {
            failResponse.setMessage(message);
        } else {
            failResponse.setMessage(status.getReasonPhrase());
        }
        return failResponse;
    }

    protected void processErrorLog(Exception exception) {
        String requestMethod = request.getMethod();
        if ("GET".equals(requestMethod)) {
            log.error("错误产生URL:{}, 查询参数为: {}", request.getRequestURL(),
                    request.getRequestURL().substring(request.getRequestURL().lastIndexOf("/") + 1));
        } else {
            log.error("错误产生URL:{}", request.getRequestURL());
        }
        log.error("异常信息: {}, 异常对象: {}", exception.getMessage(), exception);
        log.error("远程异常ip: {},主机名 {}", request.getRemoteAddr(), request.getRemoteHost());
    }

}
