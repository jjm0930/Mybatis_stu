package com.jjm.dao;

import com.jjm.pojo.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author jjm
 * @version 1.0
 * 9
 */
public interface ProvideMapper {
    //通过条件查询providerList(proName proCode currentPaeNo pageSize )
    List<Provider> getProviderList(HashMap map);
    //获取供应商列表
    List<Provider> getProList();
    //增加供应商信息
    int addProvider(Provider provider);
    //增加供应商信息Map
    int addProviderMap(HashMap<String, Object> map);

    //通过条件查询供应商记录数
    int getProviderCounts(@Param("proName") String proName,@Param("proCode") String proCode);

    //通过供应商Id删除供应商信息
    int deleteProviderById(@Param("id") int delId);

    //根据供应商Id获取供应商信息
    Provider getProviderById(@Param("id") int id);

    //修改供应商
    int modifyProvider(Provider provider);

    //修改供应商
    int modifyProviderMap(HashMap<String, Object> map);;
}
