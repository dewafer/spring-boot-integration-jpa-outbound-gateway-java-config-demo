package com.dewafer.demo.dao.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
public class MessagePayloadEntity extends AbstractAuditableEntity<Long> {

    public MessagePayloadEntity(String message) {
        this.message = message;
    }

    @Lob
    @Column(name = "message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessagePayloadEntity{" +
            "id=" + (isNew() ? "<new>" : getId()) + ", " +
            "message='" + message + '\'' +
            '}';
    }
}
