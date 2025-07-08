package com.example.chatApp.dto;

public class SubscriptionDTO {
    private String endpoint;
    private SubscriptionKeys key;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public SubscriptionKeys getKey() {
        return key;
    }

    public void setKey(SubscriptionKeys key) {
        this.key = key;
    }
}
