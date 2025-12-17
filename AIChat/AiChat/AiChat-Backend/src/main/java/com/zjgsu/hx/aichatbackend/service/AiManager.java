package com.zjgsu.hx.aichatbackend.service;

import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionRequest;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionResult;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AiManager {

    @Resource
    private ArkService arkService;

    public String doChat(String systemPrompt,String userPrompt) {
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content(systemPrompt).build();
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(userPrompt).build();
        messages.add(systemMessage);
        messages.add(userMessage);

        return doChat(messages);
    }

    public String doChat(List<ChatMessage> messages){

        if(messages == null || messages.isEmpty()) {
            return "List is empty";
        }

        BotChatCompletionRequest chatCompletionRequest = BotChatCompletionRequest.builder()
                .botId("bot-20251217100952-cj4g7")
                .messages(messages)
                .build();

        BotChatCompletionResult chatCompletionResult =  arkService.createBotChatCompletion(chatCompletionRequest);
//        chatCompletionResult.getChoices().forEach(choice -> System.out.println(choice.getMessage().getContent()));

        arkService.shutdownExecutor();
        // the references example
        if(chatCompletionResult.getChoices() != null && !chatCompletionResult.getChoices().isEmpty()) {
            return (String) chatCompletionResult.getChoices().get(0).getMessage().getContent();
            //            chatCompletionResult.getReferences().forEach(ref -> System.out.println(ref.getUrl()));
        }

        return "No answer from AI";
    }


}
