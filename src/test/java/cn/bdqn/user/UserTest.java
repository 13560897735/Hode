package cn.bdqn.user;

import cn.smbms.pojo.User;
import cn.smbms.service.user.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * 测试用户全部方法
 */
public class UserTest {
    ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationBean_config.xml");


    /**
     * 测试添加用户
     *
     */
    @Test
    public  void  add(){
        User user =new User();
        user.setUserName("刘华");
        user.setUserPassword("123456");
        user.setGender(1);
        user.setBirthday(new Date());
        user.setPhone("13560897735");
        user.setAddress("北大青鸟");
        user.setUserRole(3);
        user.setCreatedBy(1);
        UserService userServiceImpl = context.getBean("userServiceImpl", UserService.class);
        System.out.println(userServiceImpl.add(user));
    }
    @Test
    public  void  login(){
        UserService userServiceImpl = context.getBean("userServiceImpl", UserService.class);
        System.out.println(userServiceImpl.login("admin","1234567"));
    }

}
