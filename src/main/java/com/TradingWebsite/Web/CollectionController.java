package com.TradingWebsite.Web;

import com.TradingWebsite.Model.Collection;
import com.TradingWebsite.Model.Commodity;
import com.TradingWebsite.Model.User;
import com.TradingWebsite.Service.CollectionServiceImpl;
import com.TradingWebsite.Service.CommodityServiceImpl;
import com.TradingWebsite.Service.UserService;
import com.TradingWebsite.Service.UserServiceImpl;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/collect")
public class CollectionController {
    @Autowired
    CollectionServiceImpl collectionService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    CommodityServiceImpl commodityService;

    /**
     * 收藏功能
     * @param request
     * @return
     */
    @PostMapping("/addcollect")
    public JSONObject findCollectionById(HttpServletRequest request){
        JSONUtil jsonUtil=new JSONUtil();
        //1.添加收藏夹前验证用户身份
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null&&1==user.getStatus()) {
            long uid = user.getId();//用户id
            long cid= Long.parseLong(request.getParameter("id"));//商品id
            //2.判断用户收藏夹中是否有商品
            if(collectionService.findCollectionById(uid,cid)!=null){
                return jsonUtil.fail("已收藏");
            }else {
                Date date = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");//获取当前日期
                String time = df.format(date);
                Collection collection=new Collection();
                collection.setModify(time);
                collection.setUid(uid);
                collection.setCid(cid);
                //3.加入收藏栏
                collectionService.insertCollection(collection);
                return jsonUtil.success("收藏成功");
            }
        }else {
            return jsonUtil.fail("暂无权限,请登录或激活账户");
        }
    }
    /**
     * 1.点击查看收藏夹
     * 2.根据userid查询collection表
     * 3.返回cid集合
     * 4.使用cid集合查询商品
     * 5.返回商品给收藏夹
     */
    @PostMapping("/findcollectlist")
    public PageInfo<Commodity> findCollectionByUid(HttpServletRequest request){
        //1.验证用户身份
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null){
            long uid=user.getId();//获取用户id
           //int page1=Integer.parseInt(request.getParameter("page"));//获取页码
            //2.查询用户
            List<Long> collectionList=collectionService.findCidOfcollectionByUid(uid);
            //long cid= Long.parseLong(request.getParameter("id"));
            Long[] array=collectionList.toArray(new Long[collectionList.size()]);//Long转long类型
            //3.循环取出cid,并且将根据cid查询出的实体类结果存入PageInfo中
            List<Commodity> commodities=new ArrayList<>();
            PageInfo<Commodity> pageInfo = new PageInfo<>(commodities);
            for(int i=0;i<array.length;i++){
                Long cid=array[i];
                for(int j=0;j<=i;j++){
                   Commodity commodity=commodityService.findCommodityInfoById(cid);
                   commodities.add(commodity);
                }
            }
            return pageInfo;
        }else {
            return null;
        }
    }

    /**
     * 删除收藏夹物品
     * @param request
     * @return
     */
    @PostMapping("/deletecollect")
    public JSONObject deleteCollect(HttpServletRequest request){
        JSONUtil jsonUtil = new JSONUtil();
        User user=(User)request.getSession().getAttribute("user");
        if (user!=null){
            long tid= Long.parseLong(request.getParameter("tid"));
            boolean flag=collectionService.deleteCollect(tid);
            if(flag==true){
                return jsonUtil.success("删除成功");
            }else {
                return jsonUtil.fail("删除失败");
            }
        }else {
            return jsonUtil.fail("请登陆后查看收藏夹");
        }
    }

}
