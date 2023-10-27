package com.horangya.catchat.chat.dto;

import lombok.Data;

@Data
public class ChatDto {
    private Integer channelId;
    private Integer writerId;
    private String chat;
}