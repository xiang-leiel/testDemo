package com.example.test.dao;

import com.example.test.entity.UserInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author leiel
 * @since 2020-03-04
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    List<UserInfo> selectAll();

    @Select("select * from user_info where id = #{id}")
    UserInfo selectOne(int id);
}
