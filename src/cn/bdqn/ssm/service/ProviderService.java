package cn.bdqn.ssm.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.bdqn.ssm.pojo.Provider;

public interface ProviderService {
	//查询所有的供应商信息
	public List<Provider> providerlist();
	//根据条件分页查询用户信息
	public List<Provider> GetProviderList(String proCode,String proName,int from,int pageSize);
	//查找符合条件的总记录数
	public int GetCount(String proCode,String proName);
	//保存要添加的供应商信息
	public int saveprovider(Provider provider);
	//根据供应商id查找供应商id
	public Provider getProviderbyid(@Param("id")String id);
	//修改供应商
	public int updateProviderbyid(Provider provider);
	//删除供应商
	public int deleteproviderbyid(@Param("id")String id);
}
