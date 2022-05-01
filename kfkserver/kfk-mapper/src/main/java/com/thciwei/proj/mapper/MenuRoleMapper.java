package com.thciwei.proj.mapper;

import com.thciwei.proj.bean.MenuRole;
import org.apache.ibatis.annotations.Param;

public interface MenuRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MenuRole record);

    int insertSelective(MenuRole record);

    MenuRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MenuRole record);

    int updateByPrimaryKey(MenuRole record);

    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);


    boolean deleteByRid(Integer rid);
}