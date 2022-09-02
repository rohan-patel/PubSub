package com.rohan.jms.security;

import javax.jms.JMSContext;
import javax.jms.JMSConsumer;
import javax.jms.Topic;
import javax.jms.Message;
import javax.jms.JMSException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.rohan.jms.hr.Employee;

public class SecurityApp {

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();
        Topic topic = (Topic) context.lookup("topic/empTopic");

        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
                JMSContext jmsContext = cf.createContext()) {

            jmsContext.setClientID("securityApp");
            JMSConsumer consumer = jmsContext.createDurableConsumer(topic, "subscription1");
            consumer.close();

            Thread.sleep(10000);

            jmsContext.createDurableConsumer(topic, "subscription1");
            // JMSConsumer consumer = jmsContext.createConsumer(topic);
            Message message = consumer.receive();
            Employee employee = message.getBody(Employee.class);

            System.out.println(employee.getFirstName());

            consumer.close();
            jmsContext.unsubscribe("subscription1");


        };

    }

}
