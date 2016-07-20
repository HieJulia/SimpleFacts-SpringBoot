package com.boot.controller.socket;

import com.boot.model.ChatMessage;
import com.boot.model.ChatName;
import com.boot.util.EnvelopeUtil;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

@Controller
public class ChatController {

    @Autowired 
    private SimpMessagingTemplate simpMessagingTemplate;
    
    final static String queueName = "spring-boot";

    @Autowired
    RabbitTemplate rabbitTemplate;
    
    @MessageMapping("/system.name")
    @SendTo("/topic/system.name")
    public ChatName registerUser(ChatName chatName, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        System.out.println("WE GOT HERE");
        System.out.println(chatName.toString());
        EnvelopeUtil envelope = new EnvelopeUtil("ChatName", chatName);
        
        rabbitTemplate.convertAndSend(queueName, envelope.toJSON());
        
        return chatName;
    }
    
    @MessageMapping("/chat.message.{channel}")
    @SendTo("/topic/chat.message.{channel}")
    public ChatMessage addChatMessage(ChatMessage chatMessage, @DestinationVariable("channel") String channelname, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        System.out.println(headerAccessor.getUser());
        System.out.println(channelname);
        System.out.println(chatMessage);
        
        EnvelopeUtil envelope = new EnvelopeUtil("ChatMessage", chatMessage);
        
        rabbitTemplate.convertAndSend(queueName, envelope.toJSON());
        
        return chatMessage;
    }

}
