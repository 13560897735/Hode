package cn.smbms.tools;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionUtil {

    private static SqlSessionFactory factory;

    static {
        try {
            InputStream is = Resources.getResourceAsStream("mybatis_config.xml");
            factory=new SqlSessionFactoryBuilder().build(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 打开sqlsession
     * @return
     */
    public static SqlSession getSqlSession(){
        return  factory.openSession();
    }
    /**
     * 关闭sqlsession
     * @return
     */
    public static void closeSqlSession(SqlSession session){
     if(session!=null) session.close();
    }
}
