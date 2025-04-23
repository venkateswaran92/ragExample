package com.venkat.ragExample;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RagExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RagExampleApplication.class, args);
	}

	@Bean
	public ChatClient ollamaChatClient(OllamaChatModel chatModel) {
		return ChatClient.create(chatModel);
	}

}
