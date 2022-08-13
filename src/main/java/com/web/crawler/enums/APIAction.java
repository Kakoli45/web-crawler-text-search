package com.web.crawler.enums;

public enum APIAction {

    SUCCESS("success"), FAILURE("failure");

    private String action;

    private APIAction(String action) {
        this.action = action;
    }

    public String action() {
        return action;
    }

}
