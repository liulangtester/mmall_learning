package com.mmall.controller.portal;


import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")//写在类上面，所有的方法都可以重用这个/user/的url
public class UserController {
    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value="login.do", method= RequestMethod.POST)//指定为POST请求
    @ResponseBody   //在dispatcher-servlet SpringMVC配置文件中设置了返    回的是JSON格式的数据
    public ServerResponse<User> login (String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSucess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 用户登出
     * @param session
     * @return
     */
    @RequestMapping(value="logout.do", method= RequestMethod.POST)//指定为GET请求
    @ResponseBody   //在dispatcher-servlet SpringMVC配置文件中设置了返回的是JSON格式的数据
    public ServerResponse<String> logout (HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value="register.do", method= RequestMethod.POST)//指定为GET请求
    @ResponseBody   //在dispatcher-servlet SpringMVC配置文件中设置了返回的是JSON格式的数据
    public ServerResponse<String> register(User user) {
       /* ServerResponse<String> response = iUserService.register(user);
        return response;*/

        return iUserService.register(user);
    }

    /**
     * 检查用户名或邮箱是否已存在
     * @param str
     * @param type
     * @return
     */
    //前台已经校验过了用户名和邮箱是否存在，后台再次校验是为了防止用户通过接口调用注册接口 ？？？
    @RequestMapping(value="check_valid.do", method= RequestMethod.POST)//指定为GET请求
    @ResponseBody   //在dispatcher-servlet SpringMVC配置文件中设置了返回的是JSON格式的数据
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    /**
     * 获取用户信息
     * @param session
     * @return
     */
    @RequestMapping(value="get_user_info.do", method= RequestMethod.POST)//指定为GET请求
    @ResponseBody   //在dispatcher-servlet SpringMVC配置文件中设置了返回的是JSON格式的数据
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户信息");
    }

    /**
     * 忘记密码，根据username获取密码问题
     * @param username
     * @return
     */
    @RequestMapping(value="forget_get_question.do", method= RequestMethod.POST)//指定为GET请求
    @ResponseBody   //在dispatcher-servlet SpringMVC配置文件中设置了返回的是JSON格式的数据
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username);
    }

    /**
     * 校验密码问题答案是否正确
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value="forget_check_answer.do", method= RequestMethod.POST)//指定为GET请求
    @ResponseBody   //在dispatcher-servlet SpringMVC配置文件中设置了返回的是JSON格式的数据
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    /**
     * 忘记密码的重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @RequestMapping(value="forget_reset_password.do", method= RequestMethod.POST)//指定为GET请求
    @ResponseBody   //在dispatcher-servlet SpringMVC配置文件中设置了返回的是JSON格式的数据
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    /**
     * 登录状态下重置密码
     * @param session
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping(value="reset_password.do", method= RequestMethod.POST)//指定为GET请求
    @ResponseBody   //在dispatcher-servlet SpringMVC配置文件中设置了返回的是JSON格式的数据
    public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        return iUserService.resetPassword(passwordOld, passwordNew, user);
    }

    /**
     * 更新个人信息
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value="update_information.do", method= RequestMethod.POST)//指定为GET请求
    @ResponseBody   //在dispatcher-servlet SpringMVC配置文件中设置了返回的是JSON格式的数据
    public ServerResponse<User> updateInfomation(HttpSession session, User user) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        //从前端拿过来的user中，没有userID和username，先设置一下,否则后面返回来的user不会有username和userId
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInfomation(user);
        if (response.isSucess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }


    /**
     * 获取用户详细信息
     * @param session
     * @return
     */
    @RequestMapping(value="get_information.do", method= RequestMethod.POST)//指定为GET请求
    @ResponseBody   //在dispatcher-servlet SpringMVC配置文件中设置了返回的是JSON格式的数据
    public ServerResponse<User> getInfomation(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要强制登录status=10");
        }
        return iUserService.getInfomation(currentUser.getId());
    }













}