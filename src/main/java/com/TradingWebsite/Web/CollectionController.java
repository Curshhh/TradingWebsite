package com.TradingWebsite.Web;

import com.TradingWebsite.Model.Collection;
import com.TradingWebsite.Model.Commodity;
import com.TradingWebsite.Model.Manager;
import com.TradingWebsite.Model.User;
import com.TradingWebsite.Service.CollectionServiceImpl;
import com.TradingWebsite.Service.CommodityServiceImpl;
import com.TradingWebsite.Service.UserService;
import com.TradingWebsite.Service.UserServiceImpl;
import com.TradingWebsite.Uitls.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
                return jsonUtil.fail("已收藏过");
            }else {
                Date date = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//获取当前日期
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
     * 用户查看收藏栏
     * @param request
     * @return
     */
    @PostMapping("/findcollectlist")
    public List<Map> findCollectionInfoByUid(HttpServletRequest request){
        List<Map> collection=null;
        //1.验证用户身份
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null) {
            long uid = user.getId();//获取用户id
            collection=collectionService.findCollectionInfoByUid(uid);
            return collection;

        } else return null;
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
