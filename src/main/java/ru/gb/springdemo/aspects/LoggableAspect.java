package ru.gb.springdemo.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 1. Создать аннотацию замера времени исполнения метода (Timer). Она может ставиться над методами или классами.
Аннотация работает так: после исполнения метода (метода класса) с такой аннотацией, необходимо в лог записать следующее:
className - methodName #(количество секунд выполнения)

 2.* Создать аннотацию RecoverException, которую можно ставить только над методами.
<code>
@interface RecoverException {
    Class<? extends RuntimeException>[] noRecoverFor() default {};
}
</code>
У аннотации должен быть параметр noRecoverFor, в котором можно перечислить другие классы исключений.
Аннотация работает так: если во время исполнения метода был экспешн, то не прокидывать его выше
и возвращать из метода значение по умолчанию (null, 0, false, ...).
При этом, если тип исключения входит в список перечисленных в noRecoverFor исключений,
то исключение НЕ прерывается и прокидывается выше.
 3.*** Параметр noRecoverFor должен учитывать иерархию исключений.

Сдавать ссылкой на файл-аспект в проекте на гитхабе.
*/

@Slf4j
@Aspect
@Component
public class LoggableAspect {

//    @Pointcut("@annotation(ru.gb.springdemo.aspects.Timer)")
//    public void methodsAnnotationWith() {
//
//    }

    //@Around("methodsAnnotatedWith()")
    @Around("@annotation(ru.gb.springdemo.aspects.Timer )")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long resTime = endTime - startTime;
        //System.out.println("Метод" + joinPoint.getSignature() + " выполнялся " + ((endTime - startTime)/1000) + " секунд.");
        log.atWarn().log("{} - {} #({} msec)", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), resTime);
        return result;
    }



}
