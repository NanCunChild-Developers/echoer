package com.example.echoer.models;

public class ChatMessage {
    private final String dateTime;
    private final String message;
    private final String senderName;
    private final int isSent;    // 处理该信息展示在左边还是右边
    private final String imageUrl; // 添加用于存储图片URL的字段

    public ChatMessage(String dateTime, String message, String senderName, int isSent) {
        this(dateTime, message, senderName, isSent, null); // 调用新的构造器
    }

    // 新增一个构造器来处理包含图片URL的消息
    public ChatMessage(String dateTime, String message, String senderName, int isSent, String imageUrl) {
        this.dateTime = dateTime;
        this.message = message;
        this.senderName = senderName;
        this.isSent = isSent;
        this.imageUrl = imageUrl;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderName() {
        return senderName;
    }

    public int getIsSent() {
        return isSent;
    }

    // 新增获取imageUrl的方法
    public String getImageUrl() {
        return imageUrl;
    }
}
