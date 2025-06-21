package github.com.fanjh.spring_mcp_fast.controller;

import github.com.fanjh.spring_mcp_fast.service.ChatClientService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Slf4j
public class OpenAiController {
    @Resource
    private ChatClientService chatClientService;

    record ChatRequest(String message) {
    }

    @CrossOrigin
    @PostMapping(value = "/api/ai-test/chat")
    public String generateAsString(@RequestBody ChatRequest chatRequest) {
        return chatClientService.generateAsString(chatRequest.message);
    }

}
