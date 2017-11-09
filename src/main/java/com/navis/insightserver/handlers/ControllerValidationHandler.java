package com.navis.insightserver.handlers;

import com.navis.insightserver.constants.MessageType;
import com.navis.insightserver.dto.MessageDTO;
import com.navis.insightserver.dto.ResourceNotFoundExceptionDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

/**
 * Created by darrell-shofstall on 8/10/17.
 */

@ControllerAdvice
public class ControllerValidationHandler {
    private static final Logger log = LoggerFactory.getLogger(ControllerValidationHandler.class);

    @Autowired
    private MessageSource msgSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageDTO processExceptionError(Exception ex) {

        MessageDTO message  = new MessageDTO(MessageType.ERROR, ex.getMessage());

        log.error(ex.getMessage());
        return message;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();

        return processFieldError(error);
    }

    private MessageDTO processFieldError(FieldError error) {
        MessageDTO message = null;
        if (error != null) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            String msg = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
            message = new MessageDTO(MessageType.ERROR, msg);
            log.info(msg);
        }
        return message;
    }

    @ExceptionHandler(ResourceNotFoundExceptionDTO.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    public MessageDTO processResourceNotFoundError(ResourceNotFoundExceptionDTO ex) {

        Locale currentLocale = LocaleContextHolder.getLocale();

        String msg = null;
        try {
            msg = msgSource.getMessage(ex.getMessage(), null, currentLocale);
        } catch (NoSuchMessageException e) {
            msg = ex.getMessage();
        }

        StringBuilder sb = new StringBuilder().append(msg).append(ex.getResourceId());
        MessageDTO message = new MessageDTO(MessageType.ERROR, sb.toString());

        log.info(sb.toString());
        return message;
    }
}
