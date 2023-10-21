package com.example.chat_board;

public class ChatMessage {

    private String dateTime;
    private String message;
    private String senderName;
    private Boolean senderId;    // 处理该信息展示在左边还是右边

    public String getDateTime() {
        return dateTime;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderName() {
        return senderName;
    }

    public Boolean getSenderId() {
        return senderId;
    }
}
