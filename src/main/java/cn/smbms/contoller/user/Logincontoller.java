package cn.smbms.contoller.user;

import cn.smbms.pojo.User;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.Request;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class Logincontoller {
    @Resource
    private UserService userService;
    @RequestMapping(value = "/login.html",method = {RequestMethod.POST})
    public String login(HttpServletRequest request, Model model, String userCode, String userPassword){

        User user = userService.login(userCode, userPassword);
        if(null != user){//登录成功
            //放入session
            request.getSession().setAttribute(Constants.USER_SESSION, user);
            //页面跳转（frame.jsp）
           return "frame";
        }
        //页面跳转（login.jsp）带出提示信息--转发
        model.addAttribute("error", "用户名或密码不正确");
        return "login";
    }

    @RequestMapping(value = "/login.html")
    public String loginindex(){

        return "login";
    }

    /**
     * 退出
     */
    @RequestMapping(value = "/exit.html")
    public String userExit(HttpSession session) {
        session.removeAttribute(Constants.USER_SESSION);
        return "login";
    }

    /**
     * 密码验证
     *
     */


     @RequestMapping("oldpwd.do")
     @ResponseBody
     public Object oldpwd( String oldpassword,HttpSession session)
     {
        Map <String,String> map = new   HashMap<String,String>();
        User  user = (User) session.getAttribute(Constants.USER_SESSION);
        if (user==null){
            map.put("result","sessionerror");
            return JSONObject.toJSONString(map);
        }else if (oldpassword==""){
            map.put("result","error");
            return JSONObject.toJSONString(map);
        }
            //能过这2个验证说明 可以修改密码了
         if (user.getUserPassword().equals(oldpassword)){
             map.put("result","true");
             return JSONObject.toJSONString(map);
         }else {
             map.put("result","false");
             return JSONObject.toJSONString(map);
         }
     }
    /**
     * 密码修改界面
     */
    @RequestMapping("/pwdmodify.html")
    public String userAdminupdate() {
        return "pwdmodify";
    }

    /**
     * 处理修改密码
     *
     * @param model
     * @param session
     * @param newpassword
     * @return
     */
    @RequestMapping(value = "/pwdmodify.do",method = {RequestMethod.POST})
    public String userAdminupdateDo(Model model, HttpSession session, String newpassword) {
        User o = (User) session.getAttribute(Constants.USER_SESSION);
        boolean flag = false;
        if (o != null && !StringUtils.isNullOrEmpty(newpassword)) {

            flag = userService.updatePwd((o).getId(), newpassword);
            if (flag) {
                model.addAttribute(Constants.SYS_MESSAGE, "修改密码成功,请退出并使用新密码重新登录！");
                session.removeAttribute(Constants.USER_SESSION);//session注销
                return "redirect:login.html";
            } else {
                model.addAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
            }
        } else {
            model.addAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
        }
        return "pwdmodify";
    }



}
