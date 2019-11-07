package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 检查用户名是否存在
     * @param username
     * @return 存在返回1，不存在返回0
     */
    int checkUsername(String username);

    /**
     * 检查邮箱是否已存在
     * @param email
     * @return
     */
    int checkEmail(String email);

    /**
     * 验证用户名密码是否正确
     * @param username
     * @param password
     * @return User类中的信息
     */
    User selectLogin(@Param("username") String username, @Param("password") String password);
    //mybatis声明多个参数需要用到@param

    /**
     * 根据username查出密码问题
     * @param username
     * @return
     */
    String selectQuestionByUsername(String username);

    /**
     * 校验密码问题答案是否正确
     * @param username
     * @param question
     * @param answer
     * @return
     */
    int checkAnswer(@Param("username")String username, @Param("question")String question, @Param("answer")String answer);

    /**
     * 根据用户名重置密码
     * @param username
     * @param passwordNew
     * @return
     */
    int updatePasswordByUsername(@Param("username")String username, @Param("passwordNew")String passwordNew);

    /**
     * 校验旧密码是否为该用户的
     * @param password
     * @param userId
     * @return
     */
    int checkPassword(@Param("password")String password, @Param("userId")Integer userId);

    /**
     * 查询其他用户是否已经占用了email
     * @param email
     * @param userId
     * @return
     */
    int checkEmailByUserId(@Param("email")String email, @Param("userId")Integer userId);
}