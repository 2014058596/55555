package cn.com.code.common.handler;

import cn.com.code.base.bean.CommonException;
import cn.com.code.base.bean.ExceptionDto;
import cn.com.code.base.bean.HttpStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: ExceptionHandler
 * @Description: TODO
 * @author: 55555
 * @date: 2020年04月04日 8:02 下午
 */
@RestControllerAdvice
@Log4j2
public class ExceptionHandler {


    /**
     * 全局Exception处理
     * @param e
     * @param response
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ExceptionDto handlerException(Exception e, HttpServletResponse response) {
        log.error("全局异常拦截器：{0}", e);
        //获取根异常
        Throwable cause = e.getCause();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.code());
        ExceptionDto exceptionDTO = new ExceptionDto();
        exceptionDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.code());

        if (cause != null) {
            exceptionDTO.setMessage(cause.getMessage());
            exceptionDTO.setStackTrace(cause.getStackTrace());
        } else {
            exceptionDTO.setMessage(e.getMessage());
            exceptionDTO.setStackTrace(e.getStackTrace());
        }

        exceptionDTO.setExceptionClassName(Exception.class.getName());
        return exceptionDTO;
    }

    /**
     * 全局CommonException处理
     * @param e
     * @param response
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(CommonException.class)
    public ExceptionDto handlerCommonException(CommonException e, HttpServletResponse response) {
        log.error("全局异常拦截器：{0}", e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.code());
        ExceptionDto exceptionDTO = new ExceptionDto();
        exceptionDTO.setCode(e.getCode());
        exceptionDTO.setMessage(e.getMessage());
        exceptionDTO.setStackTrace(e.getStackTrace());
        exceptionDTO.setExceptionClassName(CommonException.class.getName());
        return exceptionDTO;
    }
}
