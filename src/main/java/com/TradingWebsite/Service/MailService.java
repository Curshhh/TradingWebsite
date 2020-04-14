package com.TradingWebsite.Service;

import javax.mail.MessagingException;

public interface MailService {

    /**
     *
     * @param to
     * @param subject
     * @param content
     * @return
     */
    public boolean sendHtmlMail(String to, String subject, String content);
}
