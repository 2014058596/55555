package cn.com.code.common.bean;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.PrintWriter;
import java.io.StringWriter;

/** 
* @ClassName: StandardResult
* @Description: 标准自定义响应结构
* @author: 55555
* @date: 2020年03月03日 10:26 上午
*/
@Data
@ApiModel(value = "StandardResult", description = "标准自定义响应结构")
public class StandardResult<T> {


    @ApiModelProperty(value = "响应业务状态")
    private boolean state;

    @ApiModelProperty(value = "响应消息")
    private String msg;

    @ApiModelProperty(value = "响应中的数据")
    private T data;

    @ApiModelProperty(value = "响应状态码  200 响应成功   500响应失败")
    private int code;

    /**
     * 错误栈信息
     */
    @JsonIgnore
    private String errorStackInfo;

    public StandardResult(boolean state, String msg, T data, int code, String errorStackInfo) {
        super();
        this.state = state;
        this.msg = msg;
        this.data = data;
        this.code = code;
        this.errorStackInfo = errorStackInfo;
    }

    /**
     * 构造参数
     * @param httpStatus
     * @param data
     */
    public StandardResult(HttpStatus httpStatus, T data) {
        this.code = httpStatus.code();
        this.state = httpStatus.success();
        this.msg = httpStatus.reasonPhraseCN();
        this.data = data;
    }


    public StandardResult() {
        super();
    }

    /**
     * 成功
     * @param msg
     * @param data
     * @return
     */
    public static <T> StandardResult<T> ok(String msg, T data) {

        return new StandardResult<>(HttpStatus.OK.success(), msg, data, HttpStatus.OK.code(),null);
    }


    /**
     * 成功
     * @param msg
     * @return
     */
    public static <T> StandardResult<T> ok(String msg) {

        return StandardResult.ok(msg, null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static <T> StandardResult<T> ok(T data) {

        return StandardResult.ok(null, data);
    }

    /**
     * 成功
     * @return
     */
    public static <T> StandardResult<T> ok() {
        return StandardResult.ok(null);
    }

    /**
     * 失败
     * @param msg
     * @return
     */
    public static StandardResult faild(String msg) {

        if (msg == null) {
            msg = "";
        }

        return new StandardResult(false, msg, new Object(), HttpStatus.INTERNAL_SERVER_ERROR.code(), msg);
    }

    /**
     * 失败
     * @param msg
     * @param ex
     * @return
     */
    public static StandardResult faild(String msg, Exception ex) {
        if (msg == null) {
            msg = "";
        }
        if (ex == null) {
            return new StandardResult(false, msg, new Object(), HttpStatus.INTERNAL_SERVER_ERROR.code(), msg);
        } else {
            return new StandardResult(false, msg, new Object(), HttpStatus.INTERNAL_SERVER_ERROR.code(), printStackTraceToString(ex));
        }
    }

    /**
     * 失败
     * @param ex
     * @return
     */
    public static StandardResult faild(Exception ex) {
        if (ex instanceof CommonException) {
            return faild(ex.getMessage(), ex);
        } else if (ex.getCause() instanceof CommonException) {
            return faild(ex.getCause().getMessage(), ex);
        } else if (ex.getCause() != null && ex.getCause().getCause() instanceof CommonException){
            return faild(ex.getCause().getCause().getMessage(), ex);
        }
        return faild(HttpStatus.INTERNAL_SERVER_ERROR.reasonPhraseCN(), ex);
    }


    /**
     * 打印栈信息
     * @param t 异常
     * @return json
     */
    public static String printStackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }


}

