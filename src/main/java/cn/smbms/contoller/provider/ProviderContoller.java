package cn.smbms.contoller.provider;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.tools.Constants;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 供应商采用result风格哦
 */
@Controller
public class ProviderContoller {

        @Resource
        private  ProviderService providerService;
    /**
     * 显示供应商的列表
     */
    @RequestMapping("/provider.html")
    public String getproviderList(Model model, @RequestParam(value = "queryProName",required = false) String queryProName, @RequestParam(value = "queryProCode",required = false)String queryProCode ){
        if(StringUtils.isNullOrEmpty(queryProName)){
            queryProName = "";
        }
        if(StringUtils.isNullOrEmpty(queryProCode)){
            queryProCode = "";
        }
        List<Provider> providerList = new ArrayList<Provider>();
        providerList =  providerService.getProviderList(queryProName,queryProCode);
        model.addAttribute("providerList", providerList);
        model.addAttribute("queryProName", queryProName);
        model.addAttribute("queryProCode", queryProCode);
        return "providerlist";
    }
   /**
    *
    * 跳转到新增供应商的页面
    */
   @RequestMapping("/provideradd.html")
   public String provideraddHtml()
   {

       return "provideradd";
   }

    /**
     * 新增供应商的功能
     */

    @RequestMapping(value = "/provideradd.do",method = RequestMethod.POST)
    public String provideradd(Provider provider, HttpSession session)
    {
        //创建时间
        provider.setCreationDate(new Date( ));
        //创建者
        provider.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        //开始新增
       if ( providerService.add(provider)){
           return "redirect:provider.html";
       }
        return "provideradd";
    }

    /**
     * 跳转到查看界面 并且回显
     */

    @RequestMapping("viewProvider.html/{proid}")
    public String viewProviderShow(@PathVariable String proid,Model model)
    {
        System.out.println(proid+"-------");
        Provider providerById = providerService.getProviderById(proid);
        if (providerById!=null){
            model.addAttribute("provider",providerById);
            return "providerview";

        }
      return "provideradd.html";
    }

    /***
     * 跳转修改界面并回显
     */


     @RequestMapping("/modifyProvider.html/{proid}")
     public String modifyProviderShow(@PathVariable String proid,Model model)
     {

         Provider providerById = providerService.getProviderById(proid);
         if (providerById!=null){
             //System.out.println(providerById.getId());
             model.addAttribute("provider",providerById);
             return "providermodify";

         }
         return "redirect:../provideradd.html";
     }
    /**
     * 处理修改功能界面
     */
    @RequestMapping(value = "/modifyProvider.do",method = RequestMethod.POST)
    public String modifyProvider(Provider provider,HttpSession session) {
        System.out.println(provider.getId() + "=====");
        provider.setModifyDate(new Date());
        provider.setModifyBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
        boolean modify = providerService.modify(provider);
        System.out.println("答案是;" + modify);
        if (modify) {
            return "redirect:provider.html";

        }
        return "redirect:provideradd.html";

    }
        /**
         * 删除处理
         *
         */

        @RequestMapping("delprovider")
        @ResponseBody
        public Object delprovider(String proid)
        {
            System.out.println("进来了————————————————--");
            Map<String, String> map= new HashMap<String,String>();

            if (providerService.deleteProviderById(proid)>0){
                System.out.println("进来了————————————————--");
                map.put("delResult","true");

            }else {
                map.put("delResult","false");
            }

            return JSONObject.toJSONString(map);
        }
    }

