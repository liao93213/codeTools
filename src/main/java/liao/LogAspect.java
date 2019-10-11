package liao;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Aspect
@Order(Integer.MAX_VALUE)
@Component
public class LogAspect implements InitializingBean, DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    BufferedWriter param;
    BufferedWriter performance;
    ScheduledThreadPoolExecutor executorService;

    @Pointcut("execution(* com.basic.ofc..*.*(..))")
    private void anyMethod() {
    }

    @Around("anyMethod()")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        //String argsMessage = Arrays.toString(args);
        //writeLog(param, pjp, "param:" + argsMessage);
        long startTime = System.nanoTime();
        Object result = pjp.proceed();
        writeLog(performance, pjp, "耗时" + (System.nanoTime() - startTime) / 1000000.0 + "ms" + System.lineSeparator());
        return result;
    }

    @AfterReturning(returning = "result", value = "anyMethod()")
    public Object AfterExec(JoinPoint pjp, Object result) {
        if(result instanceof List){
            writeLog(param, pjp, "result:" + ((List) result).size());
        }
        return result;
    }

    protected String getOutputFile() {
        return "C:\\Users\\" + "cheng" + "\\Desktop\\output.log";
    }

    private void writeLog(BufferedWriter writer, JoinPoint pjp, String message) {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        try {
            writer.write(nowTime() + " " + pjp.getTarget().getClass().getCanonicalName() + "." + method.getName() + System.lineSeparator());
            writer.write("          " + message + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String nowTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return dateFormat.format(new Date());
    }

    @Override
    public void destroy() throws Exception {
        param.close();
        performance.close();
        executorService.shutdown();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        param = new BufferedWriter(new FileWriter(getOutputFile()));
        performance = new BufferedWriter(new FileWriter("C:\\Users\\" + "cheng" + "\\Desktop\\performance.log"));
        executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                try {
                    performance.flush();
                    param.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1l, 1l, TimeUnit.SECONDS);
    }
}
