package ru.gb.springdemo.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RecoverExceptionAspect {

    @Around("@annotation(recoverException)")
    public Object handleRecoverException(ProceedingJoinPoint pjp, RecoverException recoverException) throws Throwable {
        try {
            return pjp.proceed();
        } catch (RuntimeException ex) {
            for (Class<? extends RuntimeException> nonRecoverable:recoverException.noRecoverFor()) {
                if (nonRecoverable.isInstance(ex)) {
                    throw ex;
                }
            }
            return getDefaultReturnValue(pjp.getSignature().getClass());
        }
    }

    public Object getDefaultReturnValue(Class<?> returnType) {
        if (returnType.isPrimitive()) {
            if (returnType == boolean.class)
                return false;
            if (returnType == char.class)
                return '\u0000';
            if (returnType == byte.class || returnType == short.class || returnType == int.class || returnType == long.class)
                return 0;
            if (returnType == float.class)
                return 0f;
            if (returnType == double.class)
                return 0d;
        }
        return null;
    }
}
