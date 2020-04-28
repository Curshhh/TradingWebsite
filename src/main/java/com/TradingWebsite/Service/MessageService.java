package com.TradingWebsite.Service;

import com.TradingWebsite.Model.Message;

import java.util.List;

public interface MessageService {
    boolean insertMessageInfo(Message message);
    List<Message> findMessageListWithCommodity(long cid);
    long findMessageNumber(long cid);
}
