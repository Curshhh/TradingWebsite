package com.TradingWebsite.Web;

import com.TradingWebsite.Model.Manager;
import com.TradingWebsite.Model.User;
import com.TradingWebsite.Service.PublishServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/publish")
public class PublishController {
    @Autowired
    PublishServiceImpl publishService;

    /**
     * 查看用户订单
     * @param request
     * @return List
     */
    @PostMapping("/findselleroders")
    public List<Map> findSellerOrders(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null){
            List<Map> seller_orders=publishService.findSellerOrders(user.getId());
            return seller_orders;
        }return null;
    }

}