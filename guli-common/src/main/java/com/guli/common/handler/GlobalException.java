package com.guli.common.handler;

import com.guli.common.entity.Result;
import com.guli.common.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 声明异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalException {

    /**
     * 特殊异常的处理
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result exceptionArithmeticExceptionQuery(Exception e){
        e.printStackTrace();
        /**
         * 在企业中：从栈中获取异常的信息
         * 发送邮件和短信通知运维人员或者开发者人员
         */
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().message("除数不能为0");
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public Result exceptionGuliExceptionQuery(GuliException e){
        e.printStackTrace();
        /**
         * 在企业中：从栈中获取异常的信息
         * 发送邮件和短信通知运维人员或者开发者人员
         */
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().message(e.getMessage()).code(e.getCode());
    }

    /**
     * 处理ExceptionHandler：声明那个异常的时候来捕获
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandlerQuery(Exception e){
        e.printStackTrace();
        /**
         * 在企业中：从栈中获取异常的信息
         * 发送邮件和短信通知运维人员或者开发者人员
         */
        log.error(ExceptionUtil.getMessage(e));
        return Result.error();
    }



}
