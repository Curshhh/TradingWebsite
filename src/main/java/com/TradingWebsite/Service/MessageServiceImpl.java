package com.TradingWebsite.Service;

import com.TradingWebsite.Dao.MessageDao;
import com.TradingWebsite.Model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Override
    public boolean insertMessageInfo(Message message) {

            return  messageDao.insertMessageInfo(message);


    }

    @Override
    public List<Message> findMessageListWithCommodity(long cid) {
        try {
            return messageDao.findMessageListWithCommodity(cid);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public long findMessageNumber(long cid) {
        return messageDao.findMessageNumber(cid);
    }
}
