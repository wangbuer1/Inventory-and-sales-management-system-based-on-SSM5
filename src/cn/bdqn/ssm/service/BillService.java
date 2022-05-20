package cn.bdqn.ssm.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.bdqn.ssm.pojo.Bill;

public interface BillService {
	//按照条件分页查询订单
	public List<Bill> billlist(String billCode,int providerId,int isPayment, int from,int pageSize);
	//按条件查询到的总记录数
	public int  getcount(String productName,int providerId,int isPayment);
	//验证订单编码是否存在
	public boolean CheckbillCode(@Param("billCode")String billCode);
	//保存要添加的订单
	public int savebill(Bill bill);//根据id查询订单
	//根据id查询订单信息
	public Bill getBillbyid(@Param("id")String id);
	//根据id修改订单信息
	public int updatebillbyid(Bill bill );
	//根据id删除订单信息
	public int deletebillbyid(@Param("id")String id);
	//根据供应商id 查询  订单集合
	public List<Bill> getbillbypproviderid(@Param("pid")String pid);
}
