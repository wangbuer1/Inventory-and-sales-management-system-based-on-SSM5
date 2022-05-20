package cn.bdqn.ssm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.bdqn.ssm.pojo.Bill;

public interface BillMapper {
	//按照条件分页查询订单
	public List<Bill> billlist(@Param ("productName") String productName,@Param("providerId")int providerId,@Param("isPayment")int isPayment,@Param("from") int from,@Param("pageSize") int pageSize);
	//按条件查询到的总记录数
	public int  getcount(@Param ("productName") String productName,@Param("providerId")int providerId,@Param("isPayment")int isPayment);
    //验证订单编码是否存在
	public Bill CheckbillCode(@Param("billCode")String billCode);
	//保存要添加的订单
	public int savebill(Bill bill);
	//根据id查询订单
	public Bill getBillbyid(@Param("id")String id);
	//根据id修改订单信息
	public int updatebillbyid(Bill bill );
	//根据id删除订单信息
	public int deletebillbyid(@Param("id")String id);
	//根据供应商id 查询  订单集合
	public List<Bill> getbillbypproviderid(@Param("pid")String pid);
}