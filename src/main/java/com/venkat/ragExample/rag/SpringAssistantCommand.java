package com.venkat.ragExample.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ShellComponent
public class SpringAssistantCommand {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Value("classpath:/prompts/prompt.st")
    private Resource sbPromptTemplate;

    public SpringAssistantCommand(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    @ShellMethod(key = "q", value = "Ask a question to the assistant")
    public String question(@ShellOption(defaultValue = "Tell about Venkateswaran Thandapani") String message) {
        PromptTemplate promptTemplate = new PromptTemplate(sbPromptTemplate);
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("input", message);
        promptParameters.put("documents", String.join("\n", findSimilarDocuments(message)));
        Prompt prompt = promptTemplate.create(promptParameters);
        return chatClient.prompt(prompt).call().content();
    }

    private List<String> findSimilarDocuments(String message) {
        SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3).build();
        List<Document> similarDocuments = vectorStore.similaritySearch(searchRequest);
        assert similarDocuments != null;
        return similarDocuments.stream()
                .map(Document::getFormattedContent)
                .collect(Collectors.toList());
    }
}
