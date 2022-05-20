package cn.bdqn.ssm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.bdqn.ssm.pojo.User;

public interface UserMapper {
    //登录
    User selectByPrimaryKey(@Param("userCore")String  userCore);
     //查询指定条件的记录数
    int getCount(@Param("userName") String username,@Param("userRole")int userRole);
    //  根据条件获取分页查询的用户信息
	public List<User> getUserList(@Param("userName")String userName,@Param("userRole")int userRole,@Param("currentPageNo")int currentPageNo,@Param("pageSize") int pageSize);
    //添加一条用户信息
	public int  adduser(User u );
	//按照id 查询某个用户
	public User getUserbyid(@Param("id")int id);
	//通过userCode 查询用户
	public User getUserbyuserCode(@Param("userCode")String userCode);
	//根据id删除一个用户信息
	public int DelUserbyId(@Param("Id")int id);
	//根据id修改一个用户信息
	public int updateuserbyid(User user );
	//修改登录用户的密码
	public int updatepassbyid(User user);
}