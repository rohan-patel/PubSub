package com.rohan.jms.hr;

import javax.jms.JMSContext;
import javax.jms.JMSConsumer;
import javax.jms.JMSProducer;
import javax.jms.Topic;
import javax.jms.Message;
import javax.jms.JMSException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.rohan.jms.hr.Employee;

public class HRApp {

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();
        Topic topic = (Topic) context.lookup("topic/empTopic");

        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
                JMSContext jmsContext = cf.createContext()) {

            Employee employee = new Employee();
            employee.setId(123);
            employee.setFirstName("Rohan");
            employee.setLastName("Patel");
            employee.setDesignation("Software Architect");
            employee.setEmail("rohanpatel1531@gmail.com");
            employee.setPhone("7228815397");

            for(int i=0; i<=10; i++) {
                jmsContext.createProducer().send(topic, employee);
            }

            System.out.println("Message Sent");

        };

    }

}
