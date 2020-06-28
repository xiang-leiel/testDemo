package com.quantchi.tianji.service.search.service;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.StatusLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**企业情况记录service
 * @author whuang
 * @date 2019/7/11
 */
public interface StatusLogService {

    /**
     * 企业情况记录
     * @param statusLog
     * @return
     */
    ResultInfo save(StatusLog statusLog);


    /**
     * 根据用户id和企业id获取最新一条记录
     * @param staffId
     * @param companyId
     * @return
     */
    StatusLog getLastStatusLogByStaffIdAndCompanyId(String staffId,String companyId);


    /**
     * 根据用户id和企业id获取记录历史
     * @param staffId
     * @param companyId
     * @return
     */
    List<StatusLog> listStatusLogByStaffIdAndCompanyId(String staffId, String companyId);

    ResultInfo listMyLoggedCompany(String staffId,Integer page, Integer pageSize);

    ResultInfo get5LastStatusLogByCompanyId4EveryField(String companyId);

    List<Map<String, Object>> recordRank(String time);

    ResultInfo getLast(String staffId,String companyId);
}
