package com.tugrulhan.freethoughts.services;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.tugrulhan.freethoughts.services.impl.EmailService;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final Tracer tracer;

    public EmailServiceImpl(AmazonSimpleEmailService amazonSimpleEmailService, Tracer tracer) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
        this.tracer = tracer;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        Span span=tracer.spanBuilder("sendEmail").startSpan();
        SendEmailRequest request=new SendEmailRequest()
                .withSource("**********")
                .withDestination(new Destination().withToAddresses(to))
                .withMessage(new Message()
                        .withSubject(new Content(subject))
                        .withBody(new Body().withText(new Content(body))));

        amazonSimpleEmailService.sendEmail(request);
        span.addEvent("Email sent");
        span.end();
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String body) {
        SendEmailRequest request = new SendEmailRequest()
                .withSource("*************")
                .withDestination(new Destination().withToAddresses(to))
                .withMessage(new Message()
                        .withSubject(new Content(subject))
                        .withBody(new Body().withHtml(new Content(body)))
                );
        amazonSimpleEmailService.sendEmail(request);
    }
}
