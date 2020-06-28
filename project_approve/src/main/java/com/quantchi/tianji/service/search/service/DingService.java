package com.quantchi.tianji.service.search.service;

import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.taobao.api.ApiException;

import java.util.List;

/**
 * 用于获取钉钉数据
 */
public interface DingService {
    /**
     * 获取token
     * @return token
     */
    String getToken() throws ApiException;

    /**
     * 获取指定父部门下的部门列表
     * @param parentDepartId 父部门id
     * @return 部门列表
     * @throws ApiException 钉钉定义的异常
     */
    List<OapiDepartmentListResponse.Department> listDepartments(String parentDepartId, String token) throws ApiException;

    /**
     * 同步钉钉数据到mysql
     * @throws ApiException
     */
    void sychronizeDataToMysql() throws ApiException;

    /**
     * 获取dingding用户id
     * @param accessToken
     * @param code
     * @return
     */
    String getUserid(String accessToken, String code) throws ApiException;

    OapiUserGetResponse getUserDetail(String accessToken, String staffId) throws ApiException;
}
