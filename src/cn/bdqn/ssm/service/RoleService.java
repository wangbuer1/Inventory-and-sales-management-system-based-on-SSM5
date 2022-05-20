package cn.bdqn.ssm.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.bdqn.ssm.pojo.Role;

public  interface RoleService {
	//获取所有的角色列表
	public List<Role> getRolelist();
	//添加角色信息
	public int addrole(Role role);
	//按照角色id查找角色信息
	public Role getrolebyid(@Param("id")String id );
	//根据id修改角色信息
	public int updateole(Role role);
}
