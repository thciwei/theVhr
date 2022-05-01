package com.thciwei.proj.mapper;

import com.thciwei.proj.bean.Hr;
import com.thciwei.proj.bean.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hr record);

    int insertSelective(Hr record);

    Hr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hr record);

    int updateByPrimaryKey(Hr record);

    Hr loadUserByUsername(String username);


    List<Role> getHrRolesById(Integer id);

    List<Hr> getAllHrs(@Param("hrid") Integer hrid, @Param("keywords") String keywords);

    List<Hr> getAllHrsExceptCurrentHr(Integer id);

    Integer updatePassword(@Param("hrid") Integer hrid,@Param("encodePass") String encodePass);

    Integer updateUserface(@Param("url") String url,@Param("hrid") Integer id);
}