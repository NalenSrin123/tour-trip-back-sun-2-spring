package com.etec.tourtripapi.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TelegramService {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${otp.expiry.minutes}")
    private int otpExpiryMinutes;

    @Value("${telegram.test.channel.id}")
    private String testChannelId;

    @Value("${telegram.test.topic.id:0}")
    private Integer testTopicId;

    @Value("${telegram.use.test.channel:true}")
    private boolean useTestChannel;

    private final OkHttpClient httpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendOtp(String userChatId, String name, String email, String otpCode) {
        String targetChatId = useTestChannel ? testChannelId : userChatId;

        // Match format from Image 1 exactly
        String text = String.format(
            "Your verification code is: %s. Expires in %d minutes.\n" +
            "Email: %s\n" +
            "Name: %s\n" +
            "Please use this code to verify your account.",
            otpCode, otpExpiryMinutes, email, name
        );

        try {
            Map<String, Object> body = new HashMap<>();
            body.put("chat_id", targetChatId);
            body.put("text", text);

            // Add topic/thread ID if using a channel with topics
            if (useTestChannel && testTopicId != null && testTopicId > 0) {
                body.put("message_thread_id", testTopicId);
            }

            String json = objectMapper.writeValueAsString(body);
            String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";

            Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(json,
                    MediaType.parse("application/json")))
                .build();

            try (Response response = httpClient.newCall(request).execute()) {
                String responseBody = response.body().string();
                if (!response.isSuccessful()) {
                    log.error("Telegram API error: {}", responseBody);
                    throw new RuntimeException("Failed to send Telegram OTP: " + responseBody);
                }
                log.info("OTP sent via Telegram to: {}",
                    useTestChannel ? "test channel" : "chat_id " + userChatId);
            }
        } catch (IOException e) {
            log.error("Failed to send Telegram OTP: {}", e.getMessage());
            throw new RuntimeException("Failed to send Telegram OTP");
        }
    }
}