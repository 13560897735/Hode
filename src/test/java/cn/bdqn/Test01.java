package cn.bdqn;

import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test01 {

    @Test
    public  void  test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationBean_config.xml");
        RoleService roleServiceImpl = context.getBean("roleServiceImpl", RoleService.class);
        System.out.println(roleServiceImpl.getRoleList().size());
    }
}
