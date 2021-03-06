package com.TradingWebsite.Web;

import com.TradingWebsite.Model.Commodity;
import com.TradingWebsite.Model.Message;
import com.TradingWebsite.Model.User;
import com.TradingWebsite.Service.MessageServiceImpl;
import com.TradingWebsite.Uitls.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageServiceImpl messageService;

    /**
     * 商品留言
     * @param request
     * @param message
     * @return
     */
    @PostMapping("/insertmessage")
    public JSONObject insertMessageInfo(HttpServletRequest request, Message message){
        User user = (User) request.getSession().getAttribute("user");
        JSONUtil jsonUtil = new JSONUtil();
        if (user!=null){
            long cid= Long.parseLong(request.getParameter("id"));
            String content=request.getParameter("content");
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//获取当前日期
            String time = df.format(date);
            message.setModify(time);
            message.setUid(user.getId());
            message.setCid(cid);
            message.setContent(content);
            if (messageService.insertMessageInfo(message)==true){
                return jsonUtil.success("留言成功");
            }else {
                return jsonUtil.fail("留言失败");
            }

        }
        return jsonUtil.fail("登录失效请重新登录");
    }

    @PostMapping("/showmessage")
    public List<Message> findMessageListWithCommodity(HttpServletRequest request){
        long cid= Long.parseLong(request.getParameter("id"));
        return messageService.findMessageListWithCommodity(cid);

    }
    @PostMapping("/messagenumber")
    public JSONObject findMessageNumber(HttpServletRequest request){
        JSONUtil jsonUtil=new JSONUtil();
        long number=0;
        long cid= Long.parseLong(request.getParameter("id"));
            try {
                number=messageService.findMessageNumber(cid);
                return jsonUtil.success(number);
            } catch (Exception e) {
                return jsonUtil.success(number);
            }
    }
}
