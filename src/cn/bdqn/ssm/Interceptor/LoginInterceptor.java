package cn.bdqn.ssm.Interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.bdqn.ssm.pojo.User;
import cn.bdqn.ssm.util.Constants;

public class LoginInterceptor extends HandlerInterceptorAdapter {
   //登录的拦截器
	public boolean preHandle(HttpServletRequest request ,HttpServletResponse response,Object handler){
		HttpSession session = request.getSession();
		User user =(User)session.getAttribute(Constants.SESSION);
		if(user==null){
			try {
				response.sendRedirect(request.getContextPath()+"/401.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return  false;
		}else{
			return true;
		}
		
	}
}
