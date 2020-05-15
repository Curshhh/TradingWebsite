package com.TradingWebsite.Web;

import com.TradingWebsite.Model.Cart;
import com.TradingWebsite.Model.Commodity;
import com.TradingWebsite.Model.Manager;
import com.TradingWebsite.Model.User;
import com.TradingWebsite.Service.CartServiceImpl;
import com.TradingWebsite.Service.CommodityServiceImpl;
import com.TradingWebsite.Uitls.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
            long uid = ((User) request.getSession().getAttribute("user")).getId();//当前用户的id
            long cid = Long.parseLong(request.getParameter("id"));

            //4.使用uid和cid去查询购物车表进行判断是否有商品存在
            if (cartService.findCartInfoById(uid, cid) == null) {
                Cart cart = new Cart();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//获取当前日期
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
                if(commodity.getStatus()==0){
                    return jsonUtil.success("物品已售罄");
                }
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
                   if (waresnumber<=0) {
                       waresnumber=0;
                       return jsonUtil.fail("添加数量超过商品库存,您最多只能再添加" + waresnumber + "件");
                   }
                }

                if (cart1.getQuantity()+total<=warestotal) {
                    Commodity commodity = commodityService.findCommodityInfoById(cid);//查询商品信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//获取当前日期
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
    public List<Map> selectCartListByUser(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        List<Map> cartList=null;
        if(user!=null){
            try {
               return cartList= cartService.selectCartListByUser(user.getId());
            } catch (Exception e) {
                return null;
            }

        }
        return null;
    }

    /**
     * 购物车总价
     * @param request
     * @return
     */
    @PostMapping("/cartprices")
    public JSONObject SelectCartPrices(HttpServletRequest request){
        JSONUtil jsonUtil=new JSONUtil();
        double number=0;
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null) {
            try {
                number=cartService.selectCartPrices(user.getId());
                return jsonUtil.success(number);

            } catch (Exception e) {
                return jsonUtil.success(number);
            }
        }else return jsonUtil.success(number);
    }
    /**
     * 购物车商品数量
     * @param request
     * @return
     */
    @PostMapping("/cartnumber")
    public JSONObject findUserCartNumber(HttpServletRequest request){
        JSONUtil jsonUtil=new JSONUtil();
        long number=0;
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null) {
            try {
                number=cartService.findUserCartNumber(user.getId());
                return jsonUtil.success(number);

            } catch (Exception e) {
                return jsonUtil.success(number);
            }
        }else return jsonUtil.success(number);
    }

    /**
     * 购物车商品件数
     * @param request
     * @return
     */
    @PostMapping("/cartquantity")
    public JSONObject findUserCartQuantity(HttpServletRequest request){
        JSONUtil jsonUtil=new JSONUtil();
        long number=0;
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null) {
            try {
                number=cartService.findUserCartQuantity(user.getId());
                return jsonUtil.success(number);

            } catch (Exception e) {
                return jsonUtil.success(number);
            }
        }else return jsonUtil.success(number);
    }

    /**
     * 修改购物车商品的数量
     * @param request
     * @return
     */
    @PostMapping("/updatewaresnumber")
    public JSONObject updateWaresNumber(HttpServletRequest request){
        JSONUtil jsonUtil=new JSONUtil();
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            long sid= Long.parseLong(request.getParameter("sid"));//获取购物车sid
            Cart cart=cartService.findCartInfoBySid(sid);
            long quantity= Long.parseLong(request.getParameter("quantity"));//获取界面商品数量
            double price= Double.parseDouble(request.getParameter("price"));//获取到商品单价
            double total=quantity*price;//计算物品总价
            cart.setQuantity(quantity);
            cart.setTotal(total);
           cartService.updateCartInfoByCid(cart);
            System.out.println(cart);
           return jsonUtil.success("修改数量成功");
        }else  return jsonUtil.fail("失败");

    }

    /*----------------------管理员---------------------------------*/

    /**
     * 查看购物商品总量
     * @param request
     * @return
     */
    @GetMapping("/findCountOfCart")
    public JSONObject findCountOfCart(HttpServletRequest request){
        JSONUtil jsonUtil = new JSONUtil();
        Manager manager=(Manager) request.getSession().getAttribute("admin");
        if(manager!=null){
            Long Cnumber=cartService.findCountOfCart();
            return jsonUtil.success(Cnumber);
        } return null;
    }
}
