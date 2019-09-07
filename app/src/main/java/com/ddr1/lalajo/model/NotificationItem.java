package com.ddr1.lalajo.model;

public class NotificationItem {

    private int notificationId;
    private String title;
    private String message;

    public NotificationItem(int id, String title, String message) {
        this.notificationId = id;
        this.title = title;
        this.message = message;
    }

    public int getId() {
        return notificationId;
    }

    public void setId(int id) {
        this.notificationId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }
}
