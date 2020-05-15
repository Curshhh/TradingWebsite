package com.TradingWebsite.Web;

import com.TradingWebsite.Model.*;
import com.TradingWebsite.Service.CartServiceImpl;
import com.TradingWebsite.Service.CommodityServiceImpl;
import com.TradingWebsite.Service.OrdersServiceImpl;
import com.TradingWebsite.Uitls.JSONUtil;
import com.alibaba.fastjson.JSONObject;
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
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersServiceImpl ordersService;
    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private CommodityServiceImpl commodityService;

    /**
     * 生成订单
     * @param request
     * @param orders
     * @return
     */

    @PostMapping("/insertorders")
    public JSONObject insetOrdersInfo(HttpServletRequest request, Orders orders){
        //1.判断用户当前状态
        JSONUtil jsonUtil = new JSONUtil();
        //取出登陆用户的session信息
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null&&1==user.getStatus()) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//获取当前日期
            Date date = new Date();
            String time = df.format(date);
            long uid=user.getId();
            //2.购物车的商品id存入list中
            List<Long> cidlist=cartService.findCidByUidFromCart(user.getId());
            Long[] array=cidlist.toArray(new Long[cidlist.size()]);//Long转long类型
            //3.遍历cid,循环执行插入订单表
            for(int i=0;i<array.length;i++){
                Long cid=array[i];

                    Cart cart=cartService.findCartInfoByCid(cid);
                    long quantity=cart.getQuantity();//购物车中某个商品的数量
                    double price=cart.getTotal();//购物车中某个商品的总价


                    orders.setCid(cid);
                    orders.setStatus(0);//交易中，0为未支付
                     orders.setModify(time);
                    orders.setQuantity(quantity);
                    orders.setUid(uid);
                    orders.setPrice(price);
                    ordersService.insertOrdersInfo(orders);
                    //4.根据插入cid商品的数量信息，减去对应commodity中的商品的库存量。并且增加销量
                   Commodity commodity=commodityService.findCommodityInfoById(cid);//根据cid查询到物品信息存入commoditity实体类中
                   long wares_number=commodity.getCount()-quantity;//物品总数量减去订单生产量
                   long wares_sales=quantity;
                   commodity.setSales(wares_sales);
                   commodity.setCount(wares_number);//修改实体类中的数量
                    if (commodity.getCount()<=0){
                        commodity.setStatus(0);//改变物品状态 未支付
                    }
                    commodityService.updateCommodityInfo(commodity);//执行修改

            }
            cartService.deleteAllCartByUid(uid);//清空购物车
            return jsonUtil.success("创建订单成功");
        }else return jsonUtil.fail("登陆失效,请重新登录账户");
    }

    /**
     * 订单总价
     * @param request
     * @return
     */
    @PostMapping("/countprice")
    public JSONObject countPriceOfOrders(HttpServletRequest request){
        JSONUtil jsonUtil = new JSONUtil();
        double price=0;
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null) {
            price=ordersService.countPriceOfOrders(user.getId());
            return jsonUtil.success(price);
        }else return jsonUtil.fail("请登陆查看订单");
    }

    /**
     * 查看订单数量
     * @param request
     * @return
     */
    @PostMapping("/ordersnumber")
    public JSONObject findOrdersNumber(HttpServletRequest request){
        JSONUtil jsonUtil = new JSONUtil();
        long number=0;
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null) {
        number=ordersService.selectNmuberOreders(user.getId());
        return jsonUtil.success(number);
        }else return jsonUtil.fail("请登录查看订单");
    }

    /**
     * 查看用户订单表
     * @param request
     * @return
     */
    @PostMapping("/orderslist")
    public List<Map> findOrderListById(HttpServletRequest request){
        List<Map> orders=null;
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            orders=ordersService.findOrdersInfoByUid(user.getId());
            return orders;
        }else return null;
    }

    /**
     * 订单详情页
     * @param request
     * @return
     */
    @PostMapping("/order_details")
    public Map findOrderAndCommdityByOid(HttpServletRequest request){
        Map orders=null;
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            long oid= Long.parseLong(request.getParameter("oid"));
            orders=ordersService.findOrdersAndCommodityInfoByOid(oid);
            return orders;
        }return null;
    }

    @PostMapping("/updateorders_status_1")
    public JSONObject updateOrdersStatus_1ByCidAndUid(HttpServletRequest request){
        JSONUtil jsonUtil=new JSONUtil();
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            long cid= Long.parseLong(request.getParameter("cid"));
            long uid= Long.parseLong(request.getParameter("uid"));
//            try {
                ordersService.updateOrdersStatus_1ByCidAndUid(cid,uid);
                return jsonUtil.success("支付成功");
//            } catch (Exception e) {
//                return  jsonUtil.fail("系统出错");
//            }
        }else  return jsonUtil.fail("登陆失效，请重新登陆。");
    }
    @PostMapping("/updateorders_status_2")
    public JSONObject updateOrdersStatus_2ByCidAndUid(HttpServletRequest request){
        JSONUtil jsonUtil=new JSONUtil();
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            long cid= Long.parseLong(request.getParameter("cid"));
            long uid= Long.parseLong(request.getParameter("uid"));
            try {
                ordersService.updateOrdersStatus_2ByCidAndUid(cid,uid);
                return jsonUtil.success("提交成功");
            } catch (Exception e) {
                return  jsonUtil.fail("系统出错");
            }
        }else  return jsonUtil.fail("登陆失效，请重新登陆。");
    }
    @PostMapping("/updateorders_status_3")
    public JSONObject updateOrdersStatus_3ByCidAndUid(HttpServletRequest request){
        JSONUtil jsonUtil=new JSONUtil();
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            long cid= Long.parseLong(request.getParameter("cid"));
            long uid= Long.parseLong(request.getParameter("uid"));
            try {
                ordersService.updateOrdersStatus_3ByCidAndUid(cid,uid);
                return jsonUtil.success("收货成功");
            } catch (Exception e) {
                return  jsonUtil.fail("系统出错");
            }
        }else  return jsonUtil.fail("登陆失效，请重新登陆。");
    }
    /*----------------------管理员---------------------------------*/

    /**
     *查看订单完成量
     * @param request
     * @return
     */
    @GetMapping("/findCountEndOfOrders")
    public JSONObject findCountEndOfOrders(HttpServletRequest request){
        JSONUtil jsonUtil = new JSONUtil();
        Manager manager=(Manager) request.getSession().getAttribute("admin");
        if(manager!=null){
            Long Onumber=ordersService.findCountEndOfOrders();
            return jsonUtil.success(Onumber);
        } return null;

    }

    /**
     * 查看完成交易金额
     * @param request
     * @return
     */
    @GetMapping("findSumPriceOfOrders")
    public JSONObject findSumPriceOfOrders(HttpServletRequest request){
        JSONUtil jsonUtil = new JSONUtil();
        Manager manager=(Manager) request.getSession().getAttribute("admin");
        if(manager!=null){
            String price=ordersService.findSumPriceOfOrders();
            String price1=price;
            return jsonUtil.success(price);
        } return null;
    }

}
