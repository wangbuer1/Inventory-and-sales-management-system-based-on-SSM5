package cn.bdqn.ssm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.bdqn.ssm.pojo.Role;

public interface RoleMapper {
  //根据分页查询角色信息 
	public List<Role> getCount();
  //添加角色信息
	public int addrole(Role role);
	//按照角色id查找角色信息
	public Role getrolebyid(@Param("id")String id );
   //根据id修改角色信息
	public int updateole(Role role);
}