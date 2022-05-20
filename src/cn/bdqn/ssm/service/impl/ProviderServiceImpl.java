package cn.bdqn.ssm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.bdqn.ssm.mapper.ProviderMapper;
import cn.bdqn.ssm.pojo.Provider;
import cn.bdqn.ssm.service.ProviderService;
@Service("providerService")
public class ProviderServiceImpl implements ProviderService {

	@Resource
	private ProviderMapper providermapper;
	//查找所有的供应商信息
	public List<Provider> providerlist() {
		return  providermapper.providerlist();
	}
	//按照条件分页查询供应商
	public List<Provider> GetProviderList(String proCode, String proName,
			int from, int pageSize) {
		return  providermapper.GetProviderList(proCode, proName, from, pageSize);
	}
	public int GetCount(String proCode, String proName) {
		return providermapper.GetCount(proCode, proName);
	}
	//保存要添加的供应商信息
	public int saveprovider(Provider provider) {
		return providermapper.saveprovider(provider);
	}
	//根据供应商id查找供应商id
	public Provider getProviderbyid(String id) {
		return providermapper.getProviderbyid(id);
	}
	public int updateProviderbyid(Provider provider) {
	
		return providermapper.updateProviderbyid(provider);
	}
	//删除供应商
	public int deleteproviderbyid(String id) {
	
		return providermapper.deleteproviderbyid(id);
	}
}
