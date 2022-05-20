package cn.bdqn.ssm.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.bdqn.ssm.pojo.Bill;
import cn.bdqn.ssm.pojo.Provider;
import cn.bdqn.ssm.pojo.User;
import cn.bdqn.ssm.service.BillService;
import cn.bdqn.ssm.service.ProviderService;
import cn.bdqn.ssm.util.Constants;
import cn.bdqn.ssm.util.PageSupport;

@Controller
@RequestMapping("/bill")
public class BillController {
	//订单Controller
	@Resource
	private  BillService billservice;
	@Resource
	private ProviderService providerservice;
	//进入订单列表
	@RequestMapping("/billlist.html")
	public String billlist(@RequestParam(value = "queryProductName",required = false) String productName,
			@RequestParam(value = "queryProviderId",required = false) String providerid,
			@RequestParam(value = "queryIsPayment",required = false) String isPayment,
			@RequestParam(value = "pageIndex",required = false) String pageIndex,
			Model m ){
		int _providerid =0;  //供应商id
		int _isPayment = 0;    //是否付款
		if(productName ==null){
			productName ="";
		}
		if(providerid != null && !providerid.equals("")){
			_providerid = Integer.parseInt(providerid);
		}
		if (isPayment!=null && !isPayment.equals("")){
			_isPayment = Integer.parseInt(isPayment);
		}
		int currentpage = 1; //当前页   //默认从第一页开始
		int pageSize = Constants.pageSize; //页面容量
		if(pageIndex!=null){
			try {
				currentpage = Integer.valueOf(pageIndex);
			} catch (NumberFormatException e) {
				//否则就跳转到错误页面
				return  "redirect:/user/syserror.html";
			}
		}
		PageSupport pages=new PageSupport();
		pages.setCurrentPageNo(currentpage);//当前页
		pages.setPageSize(pageSize); //页面容量
		int totalcount=billservice.getcount(productName,_providerid, _isPayment);
		pages.setTotalCount(totalcount);//总记录数
		int totalPageCount = pages.getTotalPageCount(); //总页数
		//控制首页和尾页
		if(currentpage < 1){   //如果当前页码小于1 就等于1
			currentpage = 1;
		}else if(currentpage > totalPageCount){ //如果当前页码大于总页数  就等于总页数
			currentpage = totalPageCount;
		}
		List<Bill> billlist= billservice.billlist(productName, _providerid, _isPayment, ((currentpage-1)*pageSize), pageSize);
		m.addAttribute("bill",billlist);
		m.addAttribute("queryProductName",productName );//用于数据回显    用户名
		m.addAttribute("queryProviderId",_providerid); //用于数据回显    角色id
		m.addAttribute("queryIsPayment", _isPayment);// 总页数
		m.addAttribute("totalPageCount", totalPageCount); //总页数
		m.addAttribute("totalCount",totalcount); //查找到的总记录数
		m.addAttribute("currentPageNo",currentpage);//当前页码	
		return "billlist";
	}
	//进入添加订单列表
	@RequestMapping("/billadd.html")
	public String billadd(@ModelAttribute Bill bill){
		return "billadd";
	}
	//把要添加的订单保存到数据库‘
	@RequestMapping("/savebill.html")
	public String savebill(Bill bill,HttpSession session
			){
		//登陆人的id
		long   loginerid   = ((User)(session.getAttribute(Constants.SESSION))).getId();  
		bill.setCreatedBy(loginerid);
		//创建时间
		bill.setCreationDate( new  Date());
		if(billservice.savebill(bill)==1){
			return "redirect:/bill/billlist.html";
		}
		return "billadd";
	}
	//根据id查询订单信息
	@RequestMapping(value="view/{id}",method = RequestMethod.GET)
	public String getbillbyid(@PathVariable String id ,Model m){

		Bill bill=  billservice.getBillbyid(id);
		m.addAttribute("bill", bill);

		return "billview";
	}

	//进入修改页面
	@RequestMapping(value="updatebill/{id}",method = RequestMethod.GET)
	public String updatebill(@PathVariable String id,@ModelAttribute Bill bill,Model m){
		//根据id查找到订单信息
		Bill b= billservice.getBillbyid(id);
		m.addAttribute("bill", b);
		//供应商列表
		List<Provider> providerlist= providerservice.providerlist();
		m.addAttribute("providerList", providerlist);
		return "billmodify";
	}
	//点击保存修改的订单信息
	@RequestMapping(value="/saveupdatebill.html",method=RequestMethod.POST)
	public String saveupdatebill(Bill bill,HttpSession session){
		//登陆人的id
		long   loginerid   = ((User)(session.getAttribute(Constants.SESSION))).getId();  
		bill.setModifyBy(loginerid);
		//创建时间
		bill.setModifyDate( new  Date());
		if(billservice.updatebillbyid(bill)==1){
			return "redirect:/bill/billlist.html";
		}
		return "billadd";
	}
	//根据id删除订单信息
	@RequestMapping("/deletebillbyid/{id}")
	@ResponseBody
	public String deletebillbyid(@PathVariable String id){
		HashMap<String,Object> result = new HashMap<String,Object>();
		if(id==null || id ==""){
			result.put("delResult","notexist");
		}
		try {
			int  count= billservice.deletebillbyid(id);
			System.out.println(count+"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			if(count>0){  //如果删除成功
				result.put("delResult","true");
			}else{//否则
				result.put("delResult","false");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
			result.put("delResult","false");
		}	
		return JSONArray.toJSONString(result);
	}
}