package cn.com.code.common.bean;

/** 
* @ClassName: ExceptionDTO
* @Description: TODO
* @author: 55555
* @date: 2020年03月30日 9:24 上午
*/
public class ExceptionDto {

    private String message;

    private int code;

    private StackTraceElement[] stackTrace;

    private String exceptionClassName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public StackTraceElement[] getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public void setExceptionClassName(String exceptionClassName) {
        this.exceptionClassName = exceptionClassName;
    }
}
