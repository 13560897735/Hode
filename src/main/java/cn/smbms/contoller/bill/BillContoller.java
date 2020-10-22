package cn.smbms.contoller.bill;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.bill.BillService;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.tools.Constants;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class BillContoller {
    @Resource
    private ProviderService providerService;
    @Resource
    private  BillService billService;
    /**
     * 订单管理界面
     */

    @RequestMapping("/bill.html")
    public String billSelect(Model model,String queryProductName,String queryProviderId,String queryIsPayment) {
        List<Provider> providerList = new ArrayList<Provider>();
        providerList = providerService.getProviderList("", "");
        model.addAttribute("providerList", providerList);

        if (StringUtils.isNullOrEmpty(queryProductName)) {
            queryProductName = "";
        }

        List<Bill> billList = new ArrayList<Bill>();
        Bill bill = new Bill();
        if (StringUtils.isNullOrEmpty(queryIsPayment)) {
            bill.setIsPayment(0);
        } else {
            bill.setIsPayment(Integer.parseInt(queryIsPayment));
        }

        if (StringUtils.isNullOrEmpty(queryProviderId)) {
            bill.setProviderId(0);
        } else {
            bill.setProviderId(Integer.parseInt(queryProviderId));
        }
        bill.setProductName(queryProductName);
        billList = billService.getBillList(bill);
        model.addAttribute("billList", billList);
        model.addAttribute("queryProductName", queryProductName);
        model.addAttribute("queryProviderId", queryProviderId);
        model.addAttribute("queryIsPayment", queryIsPayment);
        return "billlist";
    }


    /**
     * 跳转到新增订单页面
     */
    @RequestMapping("billadd.html")
    public String billaddHtml()
    {

        return "billadd";
    }
    /**
     * 处理新增功能
     */
    @RequestMapping(value = "billadd.do",method = RequestMethod.POST)
    public String billadd(Bill bill, HttpSession session)
    {
        //设置创建者 和创建时间
        bill.setCreationDate(new Date());
        bill.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        //判断有没有新增成功
        if (billService.add(bill) ) {
            return  "redirect:bill.html";
        }

        return "forward:billadd.html";
    }

    /**
     * 跳转查看页面并回显
     */
    @RequestMapping("viewBill/{billid}")
    public String viewBill(@PathVariable String billid,Model model)
    {
        Bill billById = billService.getBillById(billid);
        model.addAttribute("bill",billById);

        return "billview";
    }

    /**
     * 跳转修改页面并回显
     */

    @RequestMapping("/modifyBill/{billid}")
    public String update(@PathVariable String billid,Model model)
    {

        Bill billById = billService.getBillById(billid);
        model.addAttribute("bill",billById);

        return "billmodify";
    }
    /**
     * 处理修改
     */
    @RequestMapping(value = "modifyBill.do",method = RequestMethod.POST)
    public String updatedo(Bill bill,HttpSession session)
    {
        bill.setModifyDate(new Date());
        //设置修改时间
        bill.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        if (billService.modify(bill)) {
            return "redirect:bill.html";
        }
        return "billmodify";
    }

    /**
     * 删除功能
     */
    @RequestMapping("delbill.do")
    @ResponseBody
    public Map delete(String billid)
    {
        Map<String,String> map = new HashMap<>();
          if (billService.deleteBillById(billid)){
              map.put("delResult","true");
          }else {
              map.put("delResult","false");
          }
        return map;
    }

    /**
     * 异步查询
     */

    @RequestMapping("getprovider.do")
    @ResponseBody
    public List<Provider> providerlist()
    {
        return providerService.getProviderList("","");

    }
}
