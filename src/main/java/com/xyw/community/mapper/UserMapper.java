package com.xyw.community.mapper;

import com.xyw.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_creat,gmt_modified ) values(#{name},#{accountId},#{token},#{gmtCreat},#{gmtModified})")
    void insert(User user);

}
