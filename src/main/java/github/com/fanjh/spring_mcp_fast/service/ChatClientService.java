package github.com.fanjh.spring_mcp_fast.service;

import io.modelcontextprotocol.client.McpSyncClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@Service
public class ChatClientService {

    private final ChatClient chatClient;
    private final String prompt = """
            你是火车票售票员，查询12306的车票。
            """;

    public ChatClientService(ChatClient.Builder builder, SyncMcpToolCallbackProvider mcpToolProvider) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultSystem(prompt)
                .defaultToolCallbacks(mcpToolProvider).build();
    }

    public String generateAsString(String message) {
        String content = this.chatClient.prompt()
                .user(promptUserSpec -> promptUserSpec.text(message))
                .call().content();
        log.info("大模型回答：{}", content);
        return content;
    }


}
