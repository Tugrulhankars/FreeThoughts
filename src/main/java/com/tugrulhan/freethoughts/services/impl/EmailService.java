package com.tugrulhan.freethoughts.services.impl;

import org.springframework.stereotype.Service;

public interface EmailService {

    void sendEmail(String to,String subject,String body);
    void sendHtmlEmail(String to,String subject,String body);

}
