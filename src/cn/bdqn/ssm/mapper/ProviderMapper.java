package cn.bdqn.ssm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.bdqn.ssm.pojo.Provider;

public interface ProviderMapper {
	//查询所有的供应商信息
	public List<Provider> providerlist();
	//按条件分页查询供应商信息
	public List<Provider>  GetProviderList(@Param("proCode") String proCode,@Param("proName") String proName,@Param("from") int from,@Param("pageSize")int pageSize);
	//查找符合条件的总记录数
	public int GetCount(@Param("proCode") String proCode,@Param("proName") String proName);
	//保存要添加的供应商信息
	public int saveprovider(Provider provider);
	//根据供应商id查找供应商id
	public Provider getProviderbyid(@Param("id")String id);
	//修改供应商
	public int updateProviderbyid(Provider provider);
	//删除供应商
	public int deleteproviderbyid(@Param("pid")String pid);
}