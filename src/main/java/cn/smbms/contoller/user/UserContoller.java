package cn.smbms.contoller.user;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.role.RoleServiceImpl;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.JSONObjectDeserializer;
import com.mysql.jdbc.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class UserContoller {
    private Logger logger = Logger.getLogger(UserContoller.class);
    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    /**
     * 跳转用户界面
     */
    @RequestMapping(value = "/userlist.html")
    public String userlist(Model model, @RequestParam(value = "queryname", required = false) String queryname, @RequestParam(value = "queryUserRole", required = false) String queryUserRole, @RequestParam(value = "pageIndex", required = false) String pageIndex) {
        //查询用户列表
        String queryUserName = queryname;
        String temp = queryUserRole;
        String pageIndex1 = pageIndex;
        int queryUserRole1 = 0;
        List<User> userList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;
        /**
         * http://localhost:8090/SMBMS/userlist.do
         * ----queryUserName --NULL
         * http://localhost:8090/SMBMS/userlist.do?queryname=
         * --queryUserName ---""
         */
        System.out.println("queryUserName servlet--------" + queryUserName);
        System.out.println("queryUserRole servlet--------" + queryUserRole);
        System.out.println("query pageIndex--------- > " + pageIndex);
        if (queryUserName == null) {
            queryUserName = "";
        }
        if (temp != null && !temp.equals("")) {
            queryUserRole1 = Integer.parseInt(temp);
        }

        if (pageIndex != null) {
            try {
                currentPageNo = Integer.valueOf(pageIndex);
            } catch (NumberFormatException e) {
                return "error";
            }
        }
        //总数量（表）
        int totalCount = userService.getUserCount(queryUserName, queryUserRole1);
        //总页数
        PageSupport pages = new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);

        int totalPageCount = pages.getTotalPageCount();

        //控制首页和尾页
        if (currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            currentPageNo = totalPageCount;
        }


        userList = userService.getUserList(queryUserName, queryUserRole1, currentPageNo, pageSize);
        model.addAttribute("userList", userList);
        List<Role> roleList = null;
        roleList = roleService.getRoleList();
        model.addAttribute("roleList", roleList);
        model.addAttribute("queryUserName", queryUserName);
        model.addAttribute("queryUserRole", queryUserRole);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        return "userlist";
    }

    /**
     * 查询
     *
     * @param model
     * @param queryname
     * @param queryUserRole
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = "/userlist.html", method = {RequestMethod.POST})
    public String userlistpost(Model model, @RequestParam(value = "queryname", required = false) String queryname, @RequestParam(value = "queryUserRole", required = false) String queryUserRole, @RequestParam(value = "pageIndex", required = false) String pageIndex) {
        System.out.println("进来 了");
        //查询用户列表
        String queryUserName = queryname;
        String temp = queryUserRole;
        String pageIndex1 = pageIndex;
        int queryUserRole1 = 0;
        List<User> userList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;
        /**
         * http://localhost:8090/SMBMS/userlist.do
         * ----queryUserName --NULL
         * http://localhost:8090/SMBMS/userlist.do?queryname=
         * --queryUserName ---""
         */
        System.out.println("queryUserName servlet--------" + queryUserName);
        System.out.println("queryUserRole servlet--------" + queryUserRole);
        System.out.println("query pageIndex--------- > " + pageIndex);
        if (queryUserName == null) {
            queryUserName = "";
        }
        if (temp != null && !temp.equals("")) {
            queryUserRole1 = Integer.parseInt(temp);
        }

        if (pageIndex != null) {
            try {
                currentPageNo = Integer.valueOf(pageIndex);
            } catch (NumberFormatException e) {
                return "error";
            }
        }
        //总数量（表）
        int totalCount = userService.getUserCount(queryUserName, queryUserRole1);
        //总页数
        PageSupport pages = new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);

        int totalPageCount = pages.getTotalPageCount();

        //控制首页和尾页
        if (currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            currentPageNo = totalPageCount;
        }


        userList = userService.getUserList(queryUserName, queryUserRole1, currentPageNo, pageSize);
        model.addAttribute("userList", userList);
        List<Role> roleList = null;
        RoleService roleService = new RoleServiceImpl();
        roleList = roleService.getRoleList();
        model.addAttribute("roleList", roleList);
        model.addAttribute("queryUserName", queryUserName);
        model.addAttribute("queryUserRole", queryUserRole);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        return "userlist";
    }


    /**
     * 新增用户增加界面
     *
     * @return
     */
    @RequestMapping("/useradd.html")
    public String userAddView() {

        return "useradd";
    }

    /**
     * 同名验证
     */

    @RequestMapping(value = "/username")
    @ResponseBody
    public Object userNameCheck(String userCode) {
        System.out.println(userCode);
        if (StringUtils.isNullOrEmpty(userCode)) {
            //serCode == null || userCode.equals("")
            return "exist";
        } else {
            User user = userService.selectUserCodeExist(userCode);
            if (null != user) {
                System.out.println("===============================>");
                Object exist = JSONObject.toJSON("exist");
                System.out.println(exist);
                return "exist";
            } else {
                return "notexist";
            }
        }

    }

    /**
     * 查询用户类型
     */
    @RequestMapping(value = "/userRole", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String userRole() {
        List<Role> roleList = null;
        roleList = roleService.getRoleList();
        return JSONArray.toJSONString(roleList);
    }

//    /**
//     * 新增用户 不带上传文件
//     */
//    @RequestMapping(value = "/useradd.do")
//    public ModelAndView useraddnot( User user,HttpSession session) {
//
//        User adduser = new User();
//        user.setUserCode(user.getUserCode());
//        user.setUserName(user.getUserName());
//        user.setUserPassword(user.getUserPassword());
//        user.setAddress(user.getAddress());
//        try {
//            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(user.getBirthday().toString()));
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        user.setGender(Integer.valueOf(user.getGender()));
//        user.setPhone(user.getPhone());
//        user.setUserRole(Integer.valueOf(user.getUserRole()));
//        user.setCreationDate(new Date());
//
//        user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
//        if( userService.add(user)){
//
//           return new ModelAndView("redirect:userlist.html");
//        }else{
//           return new ModelAndView("redirect:useradd.html");
//        }
//    }

    /**
     * 带文件上传的 新增用户
     * @param user
     * @param session
     * @return
     */
    @RequestMapping(value = "/useradd.do",method = RequestMethod.POST)
    public String useradd(User user, HttpSession session,HttpServletRequest request,@RequestParam(value = "attn",required = false) MultipartFile attn) {
        String path = null;
        //判断是否有文件上传
        if (!attn.isEmpty()) {
            //设置上传路径
            path = request.getServletContext().getRealPath("/statics" + File.separator + "FileUpload\\");
            //判断文件按大小
            if (attn.getSize() > 5000000) {
                request.setAttribute("error", "文件太大了!");
                return "useradd";
            }
            //如果能过这里 说明可以判断文件类型了
            List<String> pics = Arrays.asList(new String[]{".jpg", ".png", ".jpeg", ".gif"});
            //获取文件名称
            String filename = attn.getOriginalFilename();

            //获取文件的后缀
            String suffex = filename.substring(filename.lastIndexOf("."), filename.length());
            System.out.println(suffex+"文件类型");
            if (!pics.contains(suffex)) {
                request.setAttribute("error", "文件类型上传错误!");
                return "useradd";
            }
            //能过这步 就开始创建文件名字 防止重命名
            String newFilename = System.currentTimeMillis() + "" + new Random().nextInt(1000000) + suffex;
            //开始创建文件
            File file = new File(path, newFilename);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                attn.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
          user.setPicPath(newFilename);
        }
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(user.getBirthday().toString()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        user.setCreationDate(new Date());
        user.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
        if (userService.add(user)) {
            return "redirect:userlist.html";
        } else {
            return "redirect:useradd.html";
        }

    }
    /**
     * 删除用户
     */

   @RequestMapping(value = "/deluser.do",produces = {"application/json;charset=UTF-8"})
   @ResponseBody
   public String deluser(String uid) {
       Integer delId = 0;
       try{
           delId = Integer.parseInt(uid);
       }catch (Exception e) {
           // TODO: handle exception
           delId = 0;
       }
       if(delId <= 0){
           return "notexist";
       }else{
           System.out.println("id=="+delId);
           if( userService.deleteUserById(delId)){
            return "true";
           }else{
              return "false";
           }
       }

   }

    /**
     * 修改界面
     */

    @RequestMapping("/modifyUser.html")
    public String modifyUser(String uid,Model model) {
        User userById = userService.getUserById(uid);
        if (userById!=null){
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String format = sdf.format(userById.getBirthday());
            model.addAttribute("user",userById);
            System.out.print(userById.toString());
            return "usermodify";
        }else {

            return "usermodify";
        }


    }

    /**
     * 处理修改
     */

    @RequestMapping(value = "modifyUser",method = RequestMethod.POST)
    public ModelAndView modifyUser(User user ,Model model) {


        Boolean userById = userService.modify(user);

        if (userById ) {


        }
        return  new ModelAndView("redirect:userlist.html");
    }

    /**
     * 跳转查看界面
     */
    @RequestMapping("/viewuser.do")
    public String view(String uid,Model model) {

        if(!StringUtils.isNullOrEmpty(uid)){
            //调用后台方法得到user对象
            User user = userService.getUserById(uid);
            model.addAttribute("user", user);
          return "userview";
        }


        return "redirect:/userlist.html";
    }
}

