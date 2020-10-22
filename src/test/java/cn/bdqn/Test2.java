package cn.bdqn;

import cn.smbms.dao.user.UserDao;
import cn.smbms.pojo.User;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.SqlSessionUtil;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class Test2 {
   @Test
    public  void  test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationBean_config.xml");
       UserService  userService2=(UserService) context.getBean("userService");
        System.out.println(userService2.getUserById("2").getUserPassword());


    }
    @Test
    public  void  test02(){
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationBean_config.xml");
        UserService  userService2=(UserService) context.getBean("userService");
        System.out.println(userService2.getUserById("2").getUserPassword());


    }
    @Test
    public  void  test03(){
        try {
            User userById = SqlSessionUtil.getSqlSession().getMapper(UserDao.class).getUserById("2");
            System.out.println(userById);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public  void  test04(){
       SqlSessionUtil.getSqlSession();

    }

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

        int add = 0;
        try {
            add = SqlSessionUtil.getSqlSession().getMapper(UserDao.class).add(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(add);

    }

}
