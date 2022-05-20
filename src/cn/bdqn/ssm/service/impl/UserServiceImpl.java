package cn.bdqn.ssm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.bdqn.ssm.mapper.UserMapper;
import cn.bdqn.ssm.pojo.User;
import cn.bdqn.ssm.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	//登录
	public User login(String userCore, String pwd) {
		User  user =userMapper.selectByPrimaryKey(userCore);
		if(user!=null){
			//判断密码
			if(!user.getUserPassword().equals(pwd)) user=null;
		}
		return user;
	}
	
	
	//根据条件获取总记录数
	public int getCount(String userName, int userRole) {
		return  userMapper.getCount(userName, userRole);
	}
	//根据条件分页查询用户信息
	public List<User> getUserList(String userName, int userRole,
			int currentPageNo, int pageSize) {	   
		return  userMapper.getUserList(userName, userRole, currentPageNo, pageSize);
	}
	//添加一条新的用户信息
	public int adduser(User u) {
		return userMapper.adduser(u);
	}
	//按照id 查询某个用户
	public User getUserbyid(int id){
		return userMapper.getUserbyid(id);
	}
	//通过userCode 查询用户
	public User getUserbyuserCode(@Param("userCode")String userCode){
		return userMapper.getUserbyuserCode(userCode);
	}
	//根据用户id删除用户信息
	public int DelUserbyId(int id) {
		return userMapper.DelUserbyId(id);
	}
	//根据id修改一个用户信息
	public int updateuserbyid(User user ){
		 return userMapper.updateuserbyid(user);
	}
	//根据登录用户名修改密码
	public int updatepassbyid(User user) {
		return userMapper.updatepassbyid(user);
	}
}
