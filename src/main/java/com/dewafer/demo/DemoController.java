package com.dewafer.demo;

import com.dewafer.demo.dao.entity.MessagePayloadEntity;
import com.dewafer.demo.gateway.JpaPersistGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private JpaPersistGateway jpaPersistGateway;

    @PostMapping
    public String persistMessage(@RequestBody String message) {
        return jpaPersistGateway.persist(new MessagePayloadEntity(message)).toString();
    }
}
