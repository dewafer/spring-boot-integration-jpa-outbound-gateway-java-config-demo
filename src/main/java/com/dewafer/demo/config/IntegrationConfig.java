package com.dewafer.demo.config;

import org.aopalliance.aop.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.jpa.core.JpaExecutor;
import org.springframework.integration.jpa.outbound.JpaOutboundGateway;
import org.springframework.integration.jpa.support.PersistMode;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.persistence.EntityManager;
import java.util.Collections;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannel outboundChannel() {
        return MessageChannels.direct().get();
    }

    /**
     * see http://stackoverflow.com/a/39308078/5659614
     *
     * @param entityManager EntityManager
     * @param platformTransactionManager PlatformTransactionManager
     * @return JpaOutboundGateway
     */
    @Bean
    @ServiceActivator(inputChannel = "outboundChannel")
    public MessageHandler jpaOutboundHandler(@Autowired EntityManager entityManager, @Autowired PlatformTransactionManager platformTransactionManager) {

        JpaExecutor jpaExecutor = new JpaExecutor(entityManager);
        // 持久化操作
        jpaExecutor.setPersistMode(PersistMode.PERSIST);

        // see org.springframework.integration.jpa.config.xml.AbstractJpaOutboundGatewayParser
        DefaultTransactionAttribute defaultTransactionAttribute = new DefaultTransactionAttribute();
        defaultTransactionAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        MatchAlwaysTransactionAttributeSource matchAlwaysTransactionAttributeSource = new MatchAlwaysTransactionAttributeSource();
        matchAlwaysTransactionAttributeSource.setTransactionAttribute(defaultTransactionAttribute);

        // 拦截JpaOutboundGateway的方法并开启事务
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor(platformTransactionManager, matchAlwaysTransactionAttributeSource);

        JpaOutboundGateway jpaOutboundGateway = new JpaOutboundGateway(jpaExecutor);
        jpaOutboundGateway.setProducesReply(true); // 是否有返回
        jpaOutboundGateway.setAdviceChain(Collections.singletonList((Advice)transactionInterceptor));
        jpaOutboundGateway.setOutputChannel(inboundChannel());

        return jpaOutboundGateway;
    }

    @Bean
    public MessageChannel inboundChannel() {
        return MessageChannels.direct().get();
    }

}
