package cn.bdqn.ssm.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.bdqn.ssm.pojo.Provider;
import cn.bdqn.ssm.pojo.Role;
import cn.bdqn.ssm.pojo.User;
import cn.bdqn.ssm.service.RoleService;
import cn.bdqn.ssm.util.Constants;

@Service
@RequestMapping("/role")
public class RoleController {
	@Resource
	private RoleService roleservice;
	//角色信息
	@RequestMapping(value = "/rolelist",method = RequestMethod.GET)
	@ResponseBody
	public String rolelist(){
		List<Role> roleList= roleservice.getRolelist();
		return JSONArray.toJSONString(roleList);
	}
	//先进入角色页面
	@RequestMapping("/rolelist.html")
	public String getrolelist(Model m ){
		List<Role> roleList= roleservice.getRolelist();
		m.addAttribute("roleList", roleList);
		return "rolelist";
	}
	//进入添加角色页面
	@RequestMapping(value = "/addrole.html",method = RequestMethod.GET)
	public String addrole(@ModelAttribute Role role){
		return "roleadd";
	}
	//保存添加角色
	@RequestMapping(value = "/saveaddrole.html")
	public String saveaddrole(Role role ,HttpSession session){
		//登陆人的id
		long   loginerid   = ((User)(session.getAttribute(Constants.SESSION))).getId();  
		role.setCreatedBy(loginerid);
		//创建时间
		role.setCreationDate( new  Date());
		if(roleservice.addrole(role)>=1){
			return "redirect:/role/rolelist.html";
		}
		return "roleadd";
	}
	//修改角色信息
	@RequestMapping(value = "/updaterole.html/{id}",method = RequestMethod.GET)
	public String updaterole(@PathVariable String id,@ModelAttribute Role role,Model m){
		Role r =roleservice.getrolebyid(id);
		m.addAttribute("role",r);
		return "rolemodify";
	}
	//保存修改的角色信息
	@RequestMapping(value = "/saveupdaterole.html",method = RequestMethod.POST)
	public String saveupdaterole(Role role ,HttpSession session){
		//登陆人的id
		long   loginerid   = ((User)(session.getAttribute(Constants.SESSION))).getId();  
		role.setModifyBy(loginerid);
		//创建时间
		role.setModifyDate(new  Date());
		if(roleservice.updateole(role)>=1){
			return "redirect:/role/rolelist.html";
		}
		return "roleadd";
	}
}
