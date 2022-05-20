package cn.bdqn.ssm.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.bdqn.ssm.pojo.User;

public interface UserService {

	//登录
	public User login(String userCore,String pwd);
	//根据条件获取总记录数
	public int getCount(String userName,int userRole);
	//分页条件查询用户列表
	public List<User> getUserList(String userName,int userRole,int currentPageNo, int pageSize);
	//添加一条新的用户信息
	public int  adduser(User u );
	//按照id 查询某个用户
	public User getUserbyid(int id);
	//通过userCode 查询用户
	public User getUserbyuserCode(String userCode);
	//根据id删除一个用户信息
	public int DelUserbyId(int id);
	//根据id修改一个用户信息
	public int updateuserbyid(User user );
	//修改登录用户的密码
	public int updatepassbyid(User user);
}
