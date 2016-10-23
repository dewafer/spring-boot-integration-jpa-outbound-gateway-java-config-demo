package com.dewafer.demo.gateway;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "outboundChannel", defaultReplyChannel = "inboundChannel")
public interface JpaPersistGateway {

    Object persist(Object entity);
}
