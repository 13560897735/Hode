package cn.smbms.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

public class ServiceAop {
    private Logger logger =Logger.getLogger(ServiceAop.class);
    /**
     * 前置通知
     * @param jop
     */
    public  void  before(JoinPoint jop){
       logger.info("前置通知");
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
