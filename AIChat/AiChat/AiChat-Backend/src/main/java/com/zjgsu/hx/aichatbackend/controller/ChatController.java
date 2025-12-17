package com.zjgsu.hx.aichatbackend.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import com.zjgsu.hx.aichatbackend.service.ChatService;
import com.zjgsu.hx.aichatbackend.model.ChatRoom;

import java.util.List;

@RestController
public class ChatController {
    @Resource
    private ChatService chatService;

    /**
     * 用户与AI聊天 - 简化版本
     */
    @PostMapping("/{roomId}/chat")
    public String doChat(@PathVariable long roomId, @RequestParam String userPrompt) {
        return chatService.doChat(roomId, userPrompt);
    }

    /**
     * 获取聊天室列表 - 简化版本
     */
    @GetMapping("/rooms")
    public List<ChatRoom> getChatRoomList() {
        return chatService.getChatRooms();
    }
}
