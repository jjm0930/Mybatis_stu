package com.jjm.dao;

import com.jjm.pojo.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author jjm
 * @version 1.0
 */
public interface BillMapper {
    //根据 productName providerId isPayment currentPageNo pageSize
    List<Bill> getBillList(HashMap map);

    //根据订单id 获取订单信息
    Bill getBillById(@Param("id") int billId);

    //根据 productName providerId isPayment
    int getBillCounts(HashMap map);

    //根据供应商Id查询订单数量
    int getBillCountByProviderId(@Param("providerId") int providerId);
    //添加订单
    int addBill(Bill bill);
    //添加订单 使用Map作为参数传入
    int addBillMap(HashMap<String,Object> map);
    //删除订单
    int deleteBill(@Param("id") int billId);
    //根据供应商Id删除订单信息
    int deleteBillByProviderId(@Param("providerId") int providerId);
    //修改订单信息
    int modifyBill(Bill bill);
    //修改订单信息 使用Map作为参数传入
    int modifyBillMap(HashMap<String,Object> params);

}
