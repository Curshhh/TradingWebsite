package com.TradingWebsite.Web;

import com.TradingWebsite.Model.User;
import com.TradingWebsite.Service.MailServiceImpl;
import com.TradingWebsite.Service.UserServiceImpl;
import com.TradingWebsite.Uitls.JSONUtil;
import com.TradingWebsite.Uitls.UUIDUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private MailServiceImpl mailService;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/regist")
    public JSONObject SaveUser(User user){
        JSONUtil jsonUtil=new JSONUtil();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");//获取当前日期
        //1.根据邮箱查询用户是否重复
       User u = userService.findByUserEmail(user.getEmail());
       if (u!=null){
           return jsonUtil.fail("注册失败,请检查填入的信息");
       }else {
           String time = df.format(date);
           String code=UUIDUtils.getUUID();
           user.setCode(code);
           user.setModify(time);
           System.out.println(code);
           //2.保存用户信息
           userService.saveUserInfo(user);
           //3.发送激活邮件
           String content="" +
                   "<a href='http://localhost:8080/user/checkcode?code="+code+"'>点击激活您的城市二手商品交易网账户</a>";
           mailService.sendHtmlMail(user.getEmail(),"城市二手商品交易网--激活邮件",content);
           return jsonUtil.success("注册成功");
       }

    }

    /**
     * 用户激活码匹配
     * @param code
     * @return
     */
    @RequestMapping("/checkcode")
    @ResponseBody
    public JSONObject checkUserCode(HttpServletRequest request,String code){
        JSONUtil jsonUtil=new JSONUtil();
        //1.获取激活码
        code=request.getParameter("code");
        System.out.println(code);
        //2.调用service完成激活
        User user=userService.checkCode(code);
        if (user!=null){
            //3.激活成功修改账号状态
            user.setStatus(1);
            user.setPower(1);
            userService.updateUserStatus(user);                    /* 有bug*/
            return jsonUtil.success("激活成功");

        }else {
            return jsonUtil.fail("激活失败");

        }
    }

    /**
     * 用户登陆
     * @param request
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/login")
    public JSONObject login(HttpServletRequest request, @RequestParam("email") String email, @RequestParam("password")String password){
        User user=userService.login(email,password);
        JSONUtil jsonUtil = new JSONUtil();
        if (user!=null) {
            //使用session保存用户登陆信息
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return jsonUtil.success("登录成功");
        }else {
            return jsonUtil.fail("用户信息错误");

        }
    }

    /**
     * 查询用户个人信息
     * @param request
     * @return
     */
    @GetMapping("/userinfo")
    public User findUserInfoById(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
           return user;
        }else {
            return null;
        }
    }

    /**
     * 修改用户信息
     * @param request
     * @return
     */
    @PostMapping("/updateuser")
    public JSONObject UpdateUserInfo(HttpServletRequest request,User user){
        JSONUtil jsonUtil = new JSONUtil();
        //1.获取用户登陆信息
       User user1 =(User)request.getSession().getAttribute("user");
        //2判断用户是否登陆
        if (user1!=null) {
         /*   String address=request.getParameter("address");
            String birthday=request.getParameter("birthday");
            String email=request.getParameter("email");
            String sex=request.getParameter("sex");
            String phone=request.getParameter("phone");
            String name=request.getParameter("name");
            user.setModify(user1.getModify());
            user.setCode(user1.getCode());
            user.setAddress(address);
            user.setBirthday(birthday);
            user.setEmail(email);
            user.setName(name);
            user.setSex(sex);
            user.setPhone(phone);*/

            user.setPower(user1.getPower());
            user.setId(user1.getId());
            user.setStatus(user1.getStatus());
            userService.updateUserStatus(user);

            System.out.println(user);
            return jsonUtil.success("修改成功");
        } else {
            return jsonUtil.fail("登陆信息失效，请重新登陆");
        }
    }


    @GetMapping("/findusername")
    public JSONObject findUserName(HttpServletRequest request){
        JSONUtil jsonUtil = new JSONUtil();
        User user =(User)request.getSession().getAttribute("user");
        if (user!=null) {
            return jsonUtil.success(userService.findUserInfoById(user.getId()));
        }else return null;

    }

    @GetMapping("/signoutuser")
    public JSONObject signOutUser(HttpServletRequest request){
        JSONUtil jsonUtil = new JSONUtil();
        try {
            request.getSession().invalidate();
            return jsonUtil.success("退出成功。");
        } catch (Exception e) {
            return jsonUtil.fail("退出失败。");
        }
    }

}
