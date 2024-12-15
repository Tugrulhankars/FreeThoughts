package com.tugrulhan.freethoughts.configs;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    private String awsRegion="eu-north-1";
    private String accessKey="***************";
    private String secretKey="***************";

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService() {
        BasicAWSCredentials credentials=new BasicAWSCredentials(accessKey,secretKey);
        return AmazonSimpleEmailServiceClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsRegion)
                .build();

    }

}
