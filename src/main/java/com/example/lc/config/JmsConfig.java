package com.example.lc.config;

import com.example.lc.service.MyListener;
import com.solacesystems.jms.SolJmsUtility;
import com.solacesystems.jms.SupportedProperty;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.naming.Context;
import java.util.Hashtable;

@Configuration

public class JmsConfig {

    @Bean
    public DefaultMessageListenerContainer messageListenerContainer(ConnectionFactory cf, MyListener listener) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(cf);
        container.setDestinationName("myConsumerQueue"); // queue name in Solace
        container.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE); // client must ack
        container.setMessageListener(listener);
        container.setConcurrentConsumers(1);
        return container;
    }

    @Bean
    public ConnectionFactory connectionFactory(org.springframework.core.env.Environment env) throws Exception {
        Hashtable<Object, Object> props = new Hashtable<>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.solacesystems.jndi.SolJNDIInitialContextFactory");
        props.put(Context.PROVIDER_URL, env.getProperty("solace.smfUrl", "smf://localhost:55554"));
        props.put(Context.SECURITY_PRINCIPAL, env.getProperty("solace.username", "admin"));
        props.put(Context.SECURITY_CREDENTIALS, env.getProperty("solace.password", "admin"));
        props.put(SupportedProperty.SOLACE_JMS_VPN, env.getProperty("solace.vpn", "default"));
        return SolJmsUtility.createConnectionFactory(props);
//        SolConnectionFactoryImpl cf = new SolConnectionFactoryImpl();
//        cf.setHost(env.getProperty("solace.host", "tcp://localhost:55555"));
//        cf.setVPN(env.getProperty("solace.vpn", "default"));
//        cf.setUsername(env.getProperty("solace.username", "admin"));
//        cf.setPassword(env.getProperty("solace.password", "admin"));
//        return cf;
    }

}
