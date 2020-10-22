package cn.bdqn;

import cn.smbms.service.user.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test03 {

    @Test
    public  void  test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationBean_config.xml");
        UserService userService2=(UserService) context.getBean("userservicetest");
        userService2.getUserById("2").getUserPassword();


    }
}
