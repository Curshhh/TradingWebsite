package com.TradingWebsite.Web;

import com.TradingWebsite.Model.Commodity;
import com.TradingWebsite.Model.Manager;
import com.TradingWebsite.Model.User;
import com.TradingWebsite.Service.MailServiceImpl;
import com.TradingWebsite.Service.UserServiceImpl;
import com.TradingWebsite.Uitls.JSONUtil;
import com.TradingWebsite.Uitls.UUIDUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.catalina.Session;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
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
     *
     * @param user
     * @return
     */
    @PostMapping("/regist")
    public JSONObject SaveUser(HttpServletRequest request,User user) {
        JSONUtil jsonUtil = new JSONUtil();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//获取当前日期
        //1.根据邮箱查询用户是否重复
        String name=request.getParameter("name");
        String password=request.getParameter("password");
        String email=request.getParameter("email");
        String sex=request.getParameter("sex");
        String address=request.getParameter("address");
        User u = userService.findByUserEmail(email);
        if (u != null) {
            return jsonUtil.fail("该邮箱已被注册，请更换有效邮箱");
        } else {
            //该用户不存在
            String time = df.format(date);
            String code = UUIDUtils.getUUID();
            user.setName(name);
            user.setSex(sex);
            user.setPassword(password);
            user.setCode(code);
            user.setModify(time);
            user.setAddress(address);
            user.setPower(0);
            System.out.println(code);
            //2.保存用户信息
            try {
                userService.saveUserInfo(user);

            } catch (Exception e) {
                return jsonUtil.fail("注册失败");
            }
            //3.发送激活邮件
            String content = "" +

                    "<a href='http://C:/Users/msi/Desktop/TradingWebSite_User/email_check.html?code="+code+"'>点击激活您的城市二手商品交易网账户</a>";
            try {
                mailService.sendHtmlMail(user.getEmail(), "城市二手商品交易网--激活邮件", content);
            } catch (Exception e) {
                return jsonUtil.fail("激活邮件发送失败，请联系管理员");
            }
            return jsonUtil.success("注册成功");
        }
    }

    /**
     * 用户激活码匹配
     *
     * @param code
     * @return
     */
    @PostMapping("/checkcode")
    @ResponseBody
    public JSONObject checkUserCode(HttpServletRequest request, String code) {
        JSONUtil jsonUtil = new JSONUtil();
        //1.获取激活码
        code = request.getParameter("code");
        System.out.println(code);
        //2.调用service完成激活
        User user = userService.checkCode(code);
        if (user != null) {
            //3.激活成功修改账号状态
            user.setStatus(1);
            user.setPower(1);
            userService.updateUserStatus(user);
            return jsonUtil.success("激活成功");

        } else {
            return jsonUtil.fail("激活失败");

        }
    }

    /**
     * 用户登陆
     *
     * @param request
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/login")
    public JSONObject login(HttpServletRequest request, @RequestParam("email") String email, @RequestParam("password") String password) {
        User user = userService.login(email, password);
        JSONUtil jsonUtil = new JSONUtil();
        if (user != null) {
            //使用session保存用户登陆信息
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(300 * 1000);
            session.setAttribute("user", user);
            System.out.println(session.getId());

            return jsonUtil.success("登录成功");
        } else {
            return jsonUtil.fail("用户信息错误");

        }
    }

    /**
     * 查询用户个人信息
     *
     * @param request
     * @return
     */
    @GetMapping("/userinfo")
    public User findUserInfoById(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * 修改用户信息
     *
     * @param request
     * @return
     */
    @PostMapping("/updateuser")
    public JSONObject UpdateUserInfo(HttpServletRequest request, User user) {
        JSONUtil jsonUtil = new JSONUtil();
        //1.获取用户登陆信息
        User user1 = (User) request.getSession().getAttribute("user");
        //2判断用户是否登陆
        if (user1 != null) {

//            try {
                userService.updateUserStatus(user);
//            } catch (Exception e) {
//                return jsonUtil.fail("登请检查输入的信息格式是否正确");
//            }

            System.out.println(user);
            return jsonUtil.success("修改成功");
        } else {
            return jsonUtil.fail("登陆信息失效，请重新登陆");
        }
    }

    //返回当前登陆的用户信息
    @RequestMapping(value = "/findusername", method = RequestMethod.GET)
    public User findUserName(HttpServletRequest request,HttpSession session, HttpServletResponse response) {

//        response.setHeader("Access-Control-Allow-Origin", "*");
//        session.getAttributeNames();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String date = sdf.format(session.getCreationTime());
//        session.getCreationTime();
        //这块代码有问题 可能获取session中的属性失败，肯能是session超时
        User user =(User)request.getSession().getAttribute("user");
        if (user!=null) {

          return userService.findUserInfoById(user.getId());
        }else
            return null;
//        System.out.println(session.getId());
//
//        User user = new User();
//        user.setName("danaohu");
//        user.setPhone("13247651080");


    }
//退出登陆
    @GetMapping("/signoutuser")
    public JSONObject signOutUser(HttpServletRequest request) {
        JSONUtil jsonUtil = new JSONUtil();
        try {
            request.getSession().removeAttribute("user");
            return jsonUtil.success("退出成功。");
        } catch (Exception e) {
            return jsonUtil.fail("退出失败。");
        }
    }
    //修改密码
    @PostMapping("/updatepwd")
    public JSONObject updateUserPassWord(HttpServletRequest request) {
        JSONUtil jsonUtil=new JSONUtil();
        //1.获取用户登陆信息
        User user1 = (User) request.getSession().getAttribute("user");
        //2判断用户是否登陆
        if (user1!= null) {
            long id=user1.getId();//获取用户id;
            String old_password=request.getParameter("old_password");//获取到旧密码
            if(old_password.equals(userService.userPassword(id))==false){

                return jsonUtil.fail("旧密码输入错误");
            }else {
                String new_password_1=request.getParameter("new_password_1");
                String new_password_2=request.getParameter("new_password_2");
                if (new_password_1.equals(new_password_2)==false){
                    return jsonUtil.fail("新密码校验错误");
                }else {
                    try {
                        userService.updateUserPassword(new_password_1,id);
                        return jsonUtil.success("修改成功.请重新登录");
                    } catch (Exception e) {
                        return jsonUtil.fail("修改失败，后台超时");
                    }

                }
            }

        }
        return jsonUtil.fail("登陆失效，请重新登录");

    }
    /*----------------------管理员---------------------------------*/

    @GetMapping("/findCountOfUser")
    public JSONObject findCountOfUser(HttpServletRequest request){
        JSONUtil jsonUtil = new JSONUtil();
        Manager manager=(Manager) request.getSession().getAttribute("admin");
        if(manager!=null){
            Long Unumber=userService.findCountOfUser();
            return jsonUtil.success(Unumber);
        } return null;

    }

    /**
     * 查询所有用户信息
     * @param request
     * @return
     */
    @PostMapping("/findListUser")
    public PageInfo<User> findListUser(HttpServletRequest request){

        Manager manager = (Manager) request.getSession().getAttribute("admin");
        if (manager!=null){
            int page1=Integer.parseInt(request.getParameter("page"));
            PageHelper.startPage(page1,10);//开启分页
            PageInfo<User> pageInfo=new PageInfo<>(userService.findListUser());
            return pageInfo;
        }return null;
    }

    /**
     * 搜索用户信息
     * @param request
     * @return
     */
    @PostMapping("/findUserInfo")
    public User findUserInfo(HttpServletRequest request){
        String email=request.getParameter("email");
        Manager manager = (Manager) request.getSession().getAttribute("admin");
        if (manager!=null){
           return userService.findUserInfo(email);
        }
        return null;
    }

    /**
     * 管理员修改用户信息
     * @param request
     * @return
     */
    @PostMapping("/updateUserInfo")
    public JSONObject updateUserInfo(HttpServletRequest request,User user){
        JSONUtil jsonUtil = new JSONUtil();
        Manager manager=(Manager) request.getSession().getAttribute("admin");
        if(manager!=null){
            try {
                userService.updateUserStatus(user);
            } catch (Exception e) {
                return jsonUtil.fail("登请检查输入的信息格式是否正确");
            }
            return jsonUtil.success("修改成功");
        }
        return jsonUtil.fail("登录失效请重新登录");
    }

}
