package com.isaguler.image_to_text_ai.service;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class ImageDescribeService {

    private final ChatModel chatModel;

    public ImageDescribeService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String imageToText(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            Resource resource = new InputStreamResource(inputStream);

            var userMessage = new UserMessage("Explain what do you see on this picture?",
                    List.of(new Media(MimeTypeUtils.IMAGE_PNG, resource)));

            ChatResponse response = chatModel.call(
                    new Prompt(List.of(userMessage), OllamaOptions.create().withModel("llava"))
            );

            return response.getResult().getOutput().getContent();

        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
