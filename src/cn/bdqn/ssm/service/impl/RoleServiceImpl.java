package cn.bdqn.ssm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.bdqn.ssm.mapper.RoleMapper;
import cn.bdqn.ssm.pojo.Role;
import cn.bdqn.ssm.service.RoleService;
@Service("RoleService")
public class RoleServiceImpl implements RoleService{
	@Resource
	private RoleMapper  rolemapper;
	public List<Role> getRolelist() {

		return rolemapper.getCount();
	}
	//添加角色信息
	public int addrole(Role role) {
		return rolemapper.addrole(role);
	}
	//按照角色id查找角色信息
	public Role getrolebyid(String id) {
		return rolemapper.getrolebyid(id);
	}
	//根据id修改角色信息
	public int updateole(Role role) {
		return rolemapper.updateole(role);
	}



}
