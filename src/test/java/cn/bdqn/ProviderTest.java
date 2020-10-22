package cn.bdqn;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.service.user.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class ProviderTest {
    ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationBean_config.xml");
    @Test
    public  void  add(){
        Provider provider = new Provider();
        provider.setProName("新测试");
        try {
            boolean providerServiceImpl = context.getBean("providerServiceImpl", ProviderService.class).add(provider);
            if (providerServiceImpl) System.out.println("成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public  void  del(){
        try {
            int providerServiceImpl = context.getBean("providerServiceImpl", ProviderService.class).deleteProviderById("18");
            if (providerServiceImpl>0) System.out.println("成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public  void      getProviderList(){
        try {
            List<Provider> providerServiceImpl = context.getBean("providerServiceImpl", ProviderService.class).getProviderList(null, null);
            System.out.println(providerServiceImpl.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public  void      login(){
        try {
            User login = context.getBean("userServiceImpl", UserService.class).login("admin", "1234567");
            System.out.println(login);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
