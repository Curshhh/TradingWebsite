package com.TradingWebsite.Web;

import com.TradingWebsite.Model.Commodity;
import com.TradingWebsite.Model.Manager;
import com.TradingWebsite.Model.Publish;
import com.TradingWebsite.Model.User;
import com.TradingWebsite.Service.CommodityServiceImpl;
import com.TradingWebsite.Service.PublishServiceImpl;
import com.TradingWebsite.Uitls.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/wares")
public class CommodityController {
    @Autowired
    private CommodityServiceImpl commodityService;
    @Autowired
    private PublishServiceImpl publishService;



    /**
     * 模糊查询物品
     * @param request
     * @param name
     * @return PageInfo<Commodity>
     */
    @PostMapping("/findwaresbyname")
    public PageInfo<Commodity> findWaresByName(HttpServletRequest request, @RequestParam("name")String name, @RequestParam("page")String page){
        String wares=request.getParameter("name");
        int page1=Integer.parseInt(request.getParameter("page"));
        PageHelper.startPage(page1,10);//开启分页
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityService.findByNameOfCommodity(wares));
        return pageInfo;
    }

    /**
     * 分种类查询商品
     * @param sort
     * @param request
     * @return PageInfo<Commodity>
     */
    @PostMapping("/findwaresbytype")
    public PageInfo<Commodity> findWaresByType(String sort,HttpServletRequest request){
        int page1=Integer.parseInt(request.getParameter("page"));
        String type=request.getParameter("sort");
        PageHelper.startPage(page1,8);//开启分页
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityService.findByTypeOfCommodity(type));
        return pageInfo;
    }

    /**
     * 查看商品详情
     * @param request
     * @param id
     * @return Commodity
     */
    @PostMapping("/findwaresbyid")
    public Commodity findWaresInfoById(HttpServletRequest request,String id){
        long cid=Long.parseLong(request.getParameter("id"));
        if (id!=null){
        return commodityService.findCommodityInfoById(cid);
        }else {
            return  null;
        }
    }

    /**
     * 用户添加商品
     * @param request
     * @param commodity
     * @return
     */
    @PostMapping("/addwaresinfo")
    public JSONObject addWaresInfo(HttpServletRequest request,Commodity commodity){
        JSONUtil jsonUtil = new JSONUtil();
        //1。取出登陆用户的session信息
        User user = (User) request.getSession().getAttribute("user");
        System.out.println(user);
        //2.验证用户状态
        if(user!=null&&1==user.getStatus()){
            //3.执行添加商品操作
            Publish publish=new Publish();
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//获取当前日期
            String time = df.format(date);
            commodity.setUid(user.getId());
            commodity.setStatus(1);//商品状态
            commodity.setModify(time);//商品修改或发布时间
            commodity.setSales(0);
            commodity.setName(request.getParameter("name"));

                if (commodityService.addCommodityInfo(commodity) == true) {
                    //添加进商品成功后查询商品信息
                    Commodity commodity1=commodityService.findCommodityByName(request.getParameter("name"));
                    long cid=commodity1.getId();
                    //添加进我的发布
                    publish.setName(commodity.getName());
                    publish.setUid(user.getId());
                    publish.setCid(cid);
                    publish.setModify(time);
                    publish.setPrice(commodity.getPrice());
                    publish.setQuantity(commodity.getCount());
                    if (publishService.insertMyPublish(publish) == true) {
                        return jsonUtil.success("添加成功");
                    } else {
                        return jsonUtil.fail("添加到我的发布失败");
                }
                } else return jsonUtil.fail("添加到商品列表失败");
//            }

        }else {
                return jsonUtil.fail("暂无权限,请登录或激活账户");
        }
    }

    /**
     * 用户修改商品信息
     * @param request
     * @return
     */
    @PostMapping("/updatewares")
    public JSONObject updateWaresInfo(HttpServletRequest request,Commodity commodity){
        JSONUtil jsonUtil = new JSONUtil();
        //1。取出登陆用户的session信息
        User user = (User) request.getSession().getAttribute("user");
        //2.验证用户状态
        if (user!=null&&1==user.getStatus()){

            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//获取当前日期
            String time = df.format(date);
            commodity.setModify(time);
            try {
                commodityService.updateCommodityInfo(commodity);
                return jsonUtil.success("修改成功");
            } catch (Exception e) {
                System.out.println(commodity);
                return jsonUtil.fail("修改失败");
            }
        }else
            return jsonUtil.fail("暂无权限,请登录或激活账户");
    }

    /**
     * 删除商品
     * @param request
     * @return
     */
    @PostMapping("/deletewares")
    public JSONObject deletewares(HttpServletRequest request){
        JSONUtil jsonUtil=new JSONUtil();
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null&&1==user.getStatus()){
            long cid= Long.parseLong(request.getParameter("id"));
            boolean flag=commodityService.deleteCommodityById(cid);
            if(flag==true){
                return jsonUtil.success("删除成功");
            }else {
                return jsonUtil.fail("删除失败");
            }
        }else {
            return jsonUtil.fail("暂无权限,请登录或激活账户");
        }
    }

    /**
     * 查询最新的5个发布商品
     * @return
     */
    @PostMapping("/findnewwares")
    public List<Commodity> findNewCommodityByModify(){

        return commodityService.findNewCommodityByModify();
    }
    /**
     * 查询价格最低的5个商品
     * @return
     */
    @PostMapping("/findlowprice")
    public List<Commodity> findPriceLowCommodityByPrice(){
        return commodityService.findPriceLowCommodityByPrice();
    }

    /**
     * 根据id寻找商品
     * @param request
     * @return
     */
    @PostMapping("/findwaresbyId")
    public Commodity findWaresById(HttpServletRequest request){
        long id= Long.parseLong(request.getParameter("id"));
        return commodityService.findCommodityInfoById(id);

    }

    /**
     * 推荐可能喜欢的商品
     * @param request
     * @return
     */
    @GetMapping("/findnewsort")
    public List<Commodity> findSortNewTimeWares(HttpServletRequest request){
            String sort = request.getParameter("sort");
        return commodityService.findSortNewTimeWares(sort);
    }

    /**
     * 查询用户发布过的商品
     * @param request
     * @return
     */
    @PostMapping("/findwaresbyuser")
    public List<Commodity> findWaresByUserUid(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null){
            return commodityService.findCommodityByUserUid(user.getId());
        }else return null;
    }


    /*----------------------管理员---------------------------------*/
    /**
     * 统计商品总数
     * @param request
     * @return
     */
    @GetMapping("/findCountNumberWares")
    public JSONObject findCountOfCommodity(HttpServletRequest request){
        JSONUtil jsonUtil = new JSONUtil();
        Manager manager=(Manager) request.getSession().getAttribute("admin");
        if(manager!=null){
            Long Cnumber=commodityService.findCountOfCommodity();
            return jsonUtil.success(Cnumber);
        } return null;
    }
    /**
     * 管理员查询所有商品
     * @return
     */
    @PostMapping("/adminfindwares")
    public PageInfo<Commodity> findAllCommodity(HttpServletRequest request){

        //1。取出登陆管理员的session信息
        Manager manager = (Manager) request.getSession().getAttribute("admin");
        if (manager!=null){
            int page1=Integer.parseInt(request.getParameter("page"));
            PageHelper.startPage(page1,10);//开启分页
            PageInfo<Commodity> pageInfo = new PageInfo<>(commodityService.findAllCommodity());
            return pageInfo;
        }else return null;

    }
    /**
     * 统计商品销售量
     * @param request
     * @return
     */
    @GetMapping("/findCountSalesOfWares")
    public JSONObject findCountSalesOfWares(HttpServletRequest request){
        JSONUtil jsonUtil = new JSONUtil();
        Manager manager=(Manager) request.getSession().getAttribute("admin");
        if(manager!=null){
            Long Cnumber=commodityService.findCountSalesOfCommodity();
            return jsonUtil.success(Cnumber);
        } return null;
    }





}


