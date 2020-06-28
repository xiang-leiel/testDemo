package com.quantchi.tianji.service.search.service;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.User;
import com.quantchi.tianji.service.search.model.UserDetail;
import com.quantchi.tianji.service.search.model.vo.JobInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface UserService {

    void saveUserLocation(String staffId, Double latitude, Double longitude);

    ResultInfo followCompany(String userId, String companyId, String from, String origin);

    ResultInfo unFollowCompany(String userId, String companyId);

    ResultInfo isCompanysFollowed(String staffId, String companyIds);

    ResultInfo updateTime(String staffId, String type);


    User getUserInfo(String staffId);

    void insertUserBasicDetail(Map<String, Object> condition);

    UserDetail getUserDetail(String userId);

    void insertUserDetail(Map<String,Object> condition);

    void deleteUserDetail(int id, int flag);

    void insertUserImg(Map<String,Object> condition);

    JobInfo queryLeaderInfo(String staffId);

    ResultInfo setLeaderInfo(String staffId, String leaderId, String location, Double longitude, Double latitude);

    ResultInfo getOperateManual();

    ResultInfo searchAuthority(String staffId);

    ResultInfo getProjectMember(String visitId);
}
