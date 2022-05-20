package cn.bdqn.ssm.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

import cn.bdqn.ssm.pojo.Role;
import cn.bdqn.ssm.pojo.User;
import cn.bdqn.ssm.service.RoleService;
import cn.bdqn.ssm.service.UserService;
import cn.bdqn.ssm.util.Constants;
import cn.bdqn.ssm.util.PageSupport;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userservice;
	@Resource
	private RoleService roleservice;
	//按条件分页查询用户列表
	@RequestMapping(value = "/userlist.html")
	public String  showuserlist(Model model
			,@RequestParam(value = "queryname",required= false) String username
			,@RequestParam(value="queryUserRole",required= false)String userRole
			,@RequestParam( value = "pageIndex",required= false) String pageIndex){
		int _userRole =0; //角色id
		List<User> userlist = null;//查询用户的集合
		int pageSize = Constants.pageSize ;//页面容量
		int currentpage=1;  //从第一页开始

		//如果用户名为空  就设置成空字符串
		if(username == null){
			username = "";
		}
		//如果用户角色不为空 或者不为空字符串
		if(userRole != null && !userRole.equals("")){ 
			//就把角色id赋值给当前角色id
			_userRole = Integer.parseInt(userRole);
		}
		if(pageIndex != null){
			try{ 
				//当前页码等于传过来的页码     
				currentpage = Integer.valueOf(pageIndex);
			}catch(NumberFormatException e){
				//否则就跳转到错误页面
				return  "redirect:/user/syserror.html";
			}
		}
		//获取查询到的所有记录数
		int totalCount 	= userservice.getCount(username,_userRole);
		PageSupport pages=new PageSupport();
		pages.setCurrentPageNo(currentpage);//当前页
		pages.setPageSize(pageSize); //页面容量
		pages.setTotalCount(totalCount);//总记录数
		int totalPageCount = pages.getTotalPageCount(); //总页数
		//控制首页和尾页
		if(currentpage < 1){   //如果当前页码小于1 就等于1
			currentpage = 1;
		}else if(currentpage > totalPageCount){ //如果当前页码大于总页数  就等于总页数
			currentpage = totalPageCount;
		}
		userlist = userservice.getUserList(username,_userRole,((currentpage-1)*pageSize), pageSize);
		model.addAttribute("userList", userlist);
		List<Role> roleList = null;
		roleList = roleservice.getRolelist();
		model.addAttribute("roleList", roleList);  //角色列表
		model.addAttribute("user",userlist); 
		model.addAttribute("queryUserName", username);//用于数据回显    用户名
		model.addAttribute("queryUserRole", _userRole); //用于数据回显    角色id
		model.addAttribute("totalPageCount", totalPageCount);// 总页数
		model.addAttribute("totalCount", totalCount); //查找到的总记录数
		model.addAttribute("currentPageNo", currentpage);//当前页码
		return "userlist"; // 转发到userlist.jsp
	}




	//添加用户
	//先进入到添加用户界面
	@RequestMapping(value ="/adduser.html",method = RequestMethod.GET)
	public String adduser(@ModelAttribute("user")User user
			,HttpServletRequest request){
		return "useradd";
	}

	//单击保存按钮  把要添加 的用户保存到数据库中	
	@RequestMapping(value = "/saveuser.html",method = RequestMethod.POST)
	public String saveuser(User user
			,HttpSession session
			,HttpServletRequest request
			,@RequestParam(value = "attachs",required = false)MultipartFile[] attachs){
		String  idPicPath = ""; //工作照片的路径
		String  workPicPath = "";//工作证的照片路径
		//设置上传路径
		String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
		//设置上传文件的最大字节数；
		int filesize = 500000;
		//错误信息出自
		String errorinfo = "";
		//设置一个标识符 判断 上船的文件是否出错
		boolean flag =  true;
		//循环遍历上传表单标签 的数组
		for (int i = 0; i<attachs.length ;i++) {
			MultipartFile attach = attachs[i];
			//如果上传的工作照片不为空时
			if(!attach.isEmpty()){
				if(i==0){
					errorinfo = "uploadFileError";
				}else if(i==1){
					errorinfo = "uploadWpError";
				}
				//获取上传文件的原文件名
				String oldPathName = attach.getOriginalFilename();
				//获取上传文件的后缀名
				String prefix = FilenameUtils.getExtension(oldPathName);
				//先判断文件大小
				if(attach.getSize()>filesize){ //如果上传文件的大小大于设置的最大字节
					request.setAttribute(errorinfo,"上传文件过大！");
					flag=false;
				}else if(prefix.equalsIgnoreCase("jpg")
						||prefix.equalsIgnoreCase("png")
						||prefix.equalsIgnoreCase("jpeg")
						||prefix.equalsIgnoreCase("pneg")){
					//如果上传文件满足各条件
					//设置新的文件名
					String fileName = System.currentTimeMillis()+RandomUtils.nextInt(10000000)+"_Personal.jpg";
					//指定文件夹存放上传的图片
					File targetFile = new File(path,fileName);
					//如果该文件夹不存在，就重新创建一个
					if(!targetFile.exists()){
						targetFile.mkdirs();
					}
					//把上传文件保存到新建的文件夹中
					try {
						attach.transferTo(targetFile);
					} catch (Exception e) {
						request.setAttribute(errorinfo,"上传失败！");
						flag = false;
					}
					//如果保存成功  就把该文件的绝对路径获取出来
					if(i==0){
						idPicPath = path+File.separator+fileName;
					}else if(i==1){
						workPicPath =  path+File.separator+fileName;
					}

				}else{ //如果上传文件的后缀不等于指定的这几个
					request.setAttribute(errorinfo,"上传文件类型不正确！");
					flag= false;
				}
			}

		}
		if(flag){
			//登陆人的id
			long   loginerid   = ((User)(session.getAttribute(Constants.SESSION))).getId();  
			user.setCreatedBy(loginerid);
			//创建时间
			user.setCreationDate( new  Date());
			user.setIdPicPath(idPicPath);
			user.setWorkPicPath(workPicPath);
			if(userservice.adduser(user)==1){
				return "redirect:/user/userlist.html";
			}
		}
		return "useradd";
	}

	//ajax异步验证账号是否存在
	@RequestMapping("/ucexist")
	@ResponseBody
	public Object userCodeIsExit(@RequestParam String userCode,HttpServletRequest request){
		// 由于与JSON格式相似的集合为map ，所以声明一个map集合保存json数据  
		HashMap<String, String> map = new HashMap<String,String>();
		User user = new User();
		if(StringUtils.isNullOrEmpty(userCode)){
			map.put("userCode","exist");
		}else{

			user = userservice.getUserbyuserCode(userCode);
			if(user!=null)
				map.put("userCode","exist");
			else
				map.put("userCode","noexist");
		}
		return JSON.toJSON(map);
	}

	@RequestMapping(value = "/view",method = RequestMethod.GET)
	@ResponseBody
	public Object view(@RequestParam String uid ){
		User user = null;
		if(uid==null || uid==""){
			return "nodata";
		}else{

			try {
				user = userservice.getUserbyid(Integer.parseInt(uid));

			} catch (Exception e) {
				e.getStackTrace();
				return "failed";
			}
		}
		return user;
	}


	//单击修改按钮进入修改页面并根据id先查询指定的用户信息
	@RequestMapping("/modify.html")
	public String modifyuser(@RequestParam String uid,Model m 
			,HttpServletRequest request){
		User user = new User();
		try {
			user = userservice.getUserbyid(Integer.parseInt(uid));
			if(user.getIdPicPath() != null && !"".equals(user.getIdPicPath())){
				String[] paths = user.getIdPicPath().split("\\"+File.separator);
				user.setIdPicPath(request.getContextPath()+"/statics/uploadfiles/"+paths[paths.length-1]);
			}
			if(user.getWorkPicPath() != null && !"".equals(user.getWorkPicPath())){
				String[] paths = user.getWorkPicPath().split("\\"+File.separator);
				user.setWorkPicPath(request.getContextPath()+"/statics/uploadfiles/"+paths[paths.length-1]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.addAttribute(user);
		return "usermodify";
	}
	@RequestMapping(value = "/modifysave.html")
	public String modifysaveuser(User user,HttpSession session
			){

		//登陆人的id
		long   loginerid   = ((User)(session.getAttribute(Constants.SESSION))).getId();  
		//修改人
		user.setModifyBy(loginerid);
		//修改日期
		user.setModifyDate(new  Date());
		if(userservice.updateuserbyid(user)==1){
			return "redirect:/user/userlist.html";
		}

		return "usermodify";
	}
	//点击查看进入查看页面
	//	@RequestMapping("/view.html/{uid}")
	//	public String view (@PathVariable String uid,Model m){
	//		User user= userservice.getUserbyid(Integer.parseInt(uid));
	//		m.addAttribute(user);
	//		return "userview";
	//	}


	//删除图片
	public void deleteFile(String path) {
		File file = new File(path);
		if (!file.isDirectory()) {  //如果path是一个具体的文件绝对路径
			file.delete();
		} else if (file.isDirectory()) { //如果path是一个文件夹
			String[] filelist = file.list(); //查找该文件夹下的文件或文件夹数组
			for (int i = 0; i < filelist.length; i++) { //循环遍历该数组
				File filessFile = new File(path + "\\" + filelist[i]);
				if (!filessFile.isDirectory()) { 
					filessFile.delete();
				} else if (filessFile.isDirectory()) {
					deleteFile(path + "//" + filelist[i]);
				}
			}
			file.delete();
		}
	}
	//根据id 删除该用户
	@RequestMapping(value ="/deluser",method= RequestMethod.GET)
	@ResponseBody
	public Object deluser(@RequestParam String userid,HttpServletRequest request){
		HashMap<String,Object> resultmap = new HashMap<String,Object>();

		if(userid=="" ||userid ==null){
			resultmap.put("result", "notexist");
		}
		User user= userservice.getUserbyid(Integer.parseInt(userid));
		try {
			if(userservice.DelUserbyId(Integer.parseInt(userid))>0){
				// 判断个人照和工作照是否为空,不为空先删除证件照再删除用户信息,否则直接删除用户信息
				if (user.getIdPicPath() != null || user.getWorkPicPath() != null) {
					try {
						deleteFile(user.getIdPicPath());
						deleteFile(user.getWorkPicPath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.getStackTrace();
			resultmap.put("result", "false");
		}
		resultmap.put("result", "true");
		return JSONArray.toJSONString(resultmap);
	}



	//修改密码
	//先进入修改页面 
	@RequestMapping(value = "/updatepass.html",method=RequestMethod.GET)
	public String updatepass(){
		return "pwdmodify";
	}
	//检查旧密码是否相同
	@RequestMapping(value = "/checkoldpass/{oldpwd}",method=RequestMethod.GET)
	@ResponseBody
	public String checkoldpass(@PathVariable String oldpwd,HttpSession session){
		HashMap<String,Object>  result = new HashMap<String,Object>();
		//登陆人的id
		User user= ((User)(session.getAttribute(Constants.SESSION)));  
		if(user==null){ //如果没有登录
			result.put("result","sessionerror");
		}else if(oldpwd.equals("")){ //如果旧密码为空
			result.put("result","error");
		} else if(!user.getUserPassword().equals(oldpwd)){//如果输入的旧密码与旧密码不同
			result.put("result","false");
		}else{
			result.put("result","true");
		}
		return  JSONArray.toJSONString(result);
	}

	//保存修改的密码
	@RequestMapping("/savepass.html")
	public String savepass(@Param("rnewpassword") String newpassword,HttpSession session){
		User user= ((User)(session.getAttribute(Constants.SESSION)));
		User updater  =  new User ();
		updater.setModifyBy(user.getId());
		updater.setModifyDate(new Date());
		updater.setUserPassword(newpassword);
		updater.setId(user.getId());
		if( userservice.updatepassbyid(updater)==1){
			return "redirect:/login.html";
		}
		return "pwdmodify";
	}
}
