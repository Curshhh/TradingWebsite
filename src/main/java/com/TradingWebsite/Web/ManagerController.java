package com.TradingWebsite.Web;

import com.TradingWebsite.Model.Manager;
import com.TradingWebsite.Service.ManagerServiceImpl;
import com.TradingWebsite.Uitls.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class ManagerController {
    @Autowired
    private ManagerServiceImpl managerService;

    /**
     * 用户登陆校验
     * @param request
     * @param email
     * @param password
     * @return
     */
    @PostMapping("adminlogin")
    public JSONObject AdminLogin(HttpServletRequest request, @RequestParam("email") String email, @RequestParam("password") String password){
        JSONUtil jsonUtil = new JSONUtil();
        Manager manager=managerService.adminLogin(email,password);
        if (manager!=null){
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(300 * 1000);
            session.setAttribute("admin", manager);
            System.out.println(session.getId());
            return jsonUtil.success("登录成功");
        }return jsonUtil.fail("用户信息错误");

    }

    /**
     * 返回当前管理员登陆的信息
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/adminName", method = RequestMethod.GET)
    public Manager adminName(HttpServletRequest request, HttpSession session) {

        Manager manager =(Manager)request.getSession().getAttribute("admin");
        if (manager!=null) {
            return manager;
        }else
            return null;
    }
    //退出登陆
    @GetMapping("/signoutadmin")
    public JSONObject signOutUser(HttpServletRequest request) {
        JSONUtil jsonUtil = new JSONUtil();
        try {
            request.getSession().removeAttribute("admin");
            return jsonUtil.success("退出成功。");
        } catch (Exception e) {
            return jsonUtil.fail("退出失败。");
        }
    }
}
