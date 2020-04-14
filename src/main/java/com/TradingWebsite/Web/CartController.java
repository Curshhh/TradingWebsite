package com.TradingWebsite.Web;

import com.TradingWebsite.Model.Cart;
import com.TradingWebsite.Model.Commodity;
import com.TradingWebsite.Model.User;
import com.TradingWebsite.Service.CartServiceImpl;
import com.TradingWebsite.Service.CommodityServiceImpl;
import com.TradingWebsite.Uitls.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private CommodityServiceImpl commodityService;

    /**
     * 添加购物车
     * @param request
     * @return
     */
    @PostMapping("/addcart")
    public JSONObject insertCommodityIntoCart(HttpServletRequest request) {
        JSONUtil jsonUtil = new JSONUtil();
        User user = (User) request.getSession().getAttribute("user");
        long total = Long.parseLong(request.getParameter("total"));//商品数量

        //1.验证用户身份
        if (user != null) {
            //2.获取用户id,获取商品id
            long uid = ((User) request.getSession().getAttribute("user")).getId();
            long cid = Long.parseLong(request.getParameter("id"));

            //4.使用uid和cid去查询购物车表进行判断
            if (cartService.findCartInfoById(uid, cid) == null) {
                Cart cart = new Cart();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");//获取当前日期
                Date date = new Date();
                String time = df.format(date);
                Commodity commodity = commodityService.findCommodityInfoById(cid);//查询商品信息
                double price = (total * commodity.getPrice());//单个商品数量总价
                cart.setModify(time);
                cart.setCid(cid);
                cart.setUid(uid);
                cart.setStatus(commodity.getStatus());
                cart.setTotal(price);
                cart.setQuantity(total);
                boolean flag = cartService.insertCommodityIntoCart(cart);//添加商品进入购物车
                if (flag = true) {
                    return jsonUtil.success("添加成功");
                } else {
                    return jsonUtil.fail("添加失败");
                }
            }
            if (cartService.findCartInfoById(uid,cid)!=null){
                long warestotal = commodityService.findCountById(cid);//商品库存量 20
                Cart cart1=cartService.findCartInfoById(uid,cid);

                if (cart1.getQuantity()+total>=warestotal) {
                   long waresnumber=warestotal-cart1.getQuantity();
                    return jsonUtil.fail("添加数量超过商品库存,您最多只能再添加"+waresnumber+"件");
                }

                if (cart1.getQuantity()+total<=warestotal) {
                    Commodity commodity = commodityService.findCommodityInfoById(cid);//查询商品信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");//获取当前日期
                    Date date = new Date();
                    String time = df.format(date);
                    cart1.setQuantity(cart1.getQuantity()+total);
                    cart1.setModify(time);
                    cart1.setTotal(cart1.getQuantity()*commodity.getPrice());
                    cart1.setStatus(commodity.getStatus());
                    cartService.updateCartInfoByCid(cart1);
                    return jsonUtil.success("添加成功");
                }
            }
        }
            return jsonUtil.fail("登录失效请重新登录");
    }

    /**
     * 清空购物车
     * @param request
     * @return
     */
    @PostMapping("/deletecart")
    public JSONObject deleteCartByUid(HttpServletRequest request){
        JSONUtil jsonUtil = new JSONUtil();
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            long uid=user.getId();
            if(cartService.deleteAllCartByUid(uid)==true){
                return jsonUtil.success("清空完成");
            }else {
                return jsonUtil.fail("清空失败");
            }

        }
        return jsonUtil.fail("登录失效请重新登录");
    }

    /**
     * 删除单个购物车商品
     * @param request
     * @return
     */
    @PostMapping("/deletecartinfo")
    public JSONObject deleteCartBySid(HttpServletRequest request){
        JSONUtil jsonUtil = new JSONUtil();
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            long sid= Long.parseLong(request.getParameter("sid"));
            if (cartService.deleteCartInfoBySid(sid,user.getId())){
                return jsonUtil.success("清除成功");
            }else {
                return jsonUtil.fail("清除失败");
            }
        }
        return jsonUtil.fail("登录失效请重新登录");
    }

    /**
     * 查看用户购物车内容
     * @param request
     * @return
     */
    @PostMapping("/cartlist")
    public PageInfo<Map> selectCartListByUser(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null){
            int page=Integer.parseInt(request.getParameter("page"));
            PageHelper.startPage(page,10);//开启分页
            PageInfo<Map> pageInfo = new PageInfo<>(cartService.selectCartListByUser(user.getId()));
            return pageInfo;
        }
        return null;
    }

    /**
     * 购物车总价
     * @param request
     * @return
     */
    @PostMapping("/cartprices")
    public double SelectCartPrices(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null) {
          return cartService.selectCartPrices(user.getId());
        }else return 0;
    }
}
