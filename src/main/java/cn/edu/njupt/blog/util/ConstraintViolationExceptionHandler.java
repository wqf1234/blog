package cn.edu.njupt.blog.util;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;

/**
 * ConstraintViolationExceptionHandler处理器
 */
public class ConstraintViolationExceptionHandler {

    public static String getMessage(ConstraintViolationException e){
        List<String> msgList = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()){
            msgList.add(constraintViolation.getMessage());
        }
        String messages = StringUtils.join(msgList.toArray(), ";");
        return messages;
    }
}
