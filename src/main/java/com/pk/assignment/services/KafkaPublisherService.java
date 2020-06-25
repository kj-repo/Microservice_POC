package com.pk.assignment.services;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class KafkaPublisherService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaPublisherService.class);
    private static final String TOPIC = "users";

   
}

