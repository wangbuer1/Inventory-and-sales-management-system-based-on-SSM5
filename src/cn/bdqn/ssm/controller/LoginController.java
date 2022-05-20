package cn.bdqn.ssm.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.bdqn.ssm.pojo.User;
import cn.bdqn.ssm.service.UserService;
import cn.bdqn.ssm.util.Constants;

@Controller
public class LoginController {

	@Resource
	private UserService userService;
	
	//主页面
	@RequestMapping("/sys/user/main.html")
	public String main(){
		return "frame";
	}
	
	//登录
	@RequestMapping("/login.html")
	public String login(){
		return "login";
	}
	
	@RequestMapping("/doLogin.html")
	public String doLogin(@RequestParam("userCode")String userCode,
			@RequestParam("userPassword")String userPassword,
			HttpSession  session, HttpServletRequest request){
		
		User user = userService.login(userCode, userPassword);
		if(user!=null){
			session.setAttribute(Constants.SESSION, user);
			return "redirect:/sys/user/main.html";
		}else{
			request.setAttribute("error","账号或密码错误");
			return "login";
		}
		
		
	}
	
	
	/* 主页页面  head.jsp */
	
	//退出系统——清空session回话
	@RequestMapping("/sys/user/outLogin.html")
	public String outLogin(HttpSession  session){
		session.removeAttribute(Constants.SESSION);
		return "login";
	}
	
	//左侧栏
	@RequestMapping("/sys/user/userlist.html")
	public String userMange(){
		return "userlist";
	}
}
