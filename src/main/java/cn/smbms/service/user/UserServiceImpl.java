package cn.smbms.service.user;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.smbms.dao.BaseDao;
import cn.smbms.dao.user.UserDao;
import cn.smbms.dao.user.UserDaoImpl;
import cn.smbms.pojo.User;
import cn.smbms.tools.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * service层捕获异常，进行事务处理
 * 事务处理：调用不同dao的多个方法，必须使用同一个connection（connection作为参数传递）
 * 事务完成之后，需要在service层进行connection的关闭，在dao层关闭（PreparedStatement和ResultSet对象）
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService{
	public Logger logger=Logger.getLogger(UserService.class);
		@Autowired
	private UserDao userDao;

	public UserServiceImpl() {

	}


	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}



	@Override
	public boolean add(User user) {
            int count=-1;
		try {
			 count = userDao.add(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count>0;
	}
	@Override
	public User login(String userCode, String userPassword) {
		User user=null;
		try {
			 user= userDao.getLoginUser(userCode);
			//匹配密码
			if(null != user){
				System.out.println("密码是:"+userPassword);
				if(!user.getUserPassword().equals(userPassword))
					user = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	@Override
	public List<User> getUserList(String queryUserName,int queryUserRole,int currentPageNo, int pageSize) {

		try {
			return userDao.getUserList(queryUserName,queryUserRole,currentPageNo,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public User selectUserCodeExist(String userCode) {

		User user = null;
		try {
			user = userDao.getLoginUser(userCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	@Override
	public boolean deleteUserById(Integer delId) {
		int i=0;
		try {
			 i = userDao.deleteUserById(delId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i>0;
	}
	@Override
	public User getUserById(String id)  {

		try {
			return userDao.getUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public boolean modify(User user) {
		boolean flag = false;
		try {
			int modify = userDao.modify(user);
			if(modify>0) flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			return flag;
		}
	}
	@Override
	public boolean updatePwd(int id, String pwd) {
	    boolean flag=false;
		try {
			 if (userDao.updatePwd(id, pwd)>0)
			 	return flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	public int getUserCount(String queryUserName, int queryUserRole) {
		if(queryUserName==null){
			System.out.println("空");
		}else {
			System.out.println("有"+queryUserName);
		}
		try {
			return userDao.getUserCount(queryUserName,queryUserRole);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
}
