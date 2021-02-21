package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersMapper {

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    public Users getUser(String username);

    @Select("SELECT * FROM USERS WHERE userid = #{userId}")
    public Users getUserById(Integer userId);

    @Insert("INSERT INTO USERS (username, password, salt, firstname, lastname) " +
            "VALUES (#{username}, #{password}, #{salt}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    public int insertUser(Users user);
}
