package cn.smbms.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ServiceAop2 {
    private Logger logger =Logger.getLogger(ServiceAop2.class);
    @Pointcut
    public void ccc(){}
    /**
     * 前置通知
     * @param jop
     */
    @Before("execution(* cn.smbms.service.user.*.*(..))")
    public  void  before(JoinPoint jop)
    {
       logger.info("前置通知:"+"方法名字:"+jop.getSignature().getName()+"参数:"+ Arrays.toString(jop.getArgs()));
    }

    /**
     * 后置通知
     * @param jop
     */

    public  void  after(JoinPoint jop){
        logger.info("最终通知");
    }

    /**
     * 返回通知
     */
    public  void afterReturning(JoinPoint joinPoint,Object result){
        logger.info(result+"返回通知");
    }
    /**
     * 异常通知
     *
     */
    public  void afterThrowing(JoinPoint joinPoint,Exception e){
       e.printStackTrace();
    }
}
