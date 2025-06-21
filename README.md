# Spring Ai 快速接入MCP 教程

## 环境准备
* JDK: 使用Jdk21,也可以选择jdk17
* Spring Boot 版本为3.5.3
* Spring AI 版本为1.0.0
## 拉取demo 项目
从github 仓库，拉取git clone git@github.com:fjh2021/spring-ai-mcp-fast.git  
项目代码简单讲解
* 集成MCP Client
```xml
<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-starter-mcp-client</artifactId>
 <version>${spring-ai.version}</version>
</dependency>
```
* 配置MCP Client
```yaml
spring.ai.mcp.client.request-timeout=30s
spring.ai.mcp.client.stdio.servers-configuration=classpath:/mcp-servers-config.json
```
例子：火车票MCP:mcp-servers-config.json
```json
{
    "mcpServers": {
        "12306-mcp": {
            "command": "npx",
            "args": [
                "-y",
                "12306-mcp"
            ]
        }
    }
}
```
* 创建客户端
```
@Service
public class ChatClientService {
    private final ChatClient chatClient;
    private final String prompt = """
            你是火车票售票员，查询12306的车票。
            """;
    public ChatClientService(OpenAiChatModel openAiChatModel,
                             List<McpSyncClient> mcpClients, JdbcChatMemoryRepository chatMemoryRepository) {
        var mcpToolProvider = new SyncMcpToolCallbackProvider(mcpClients);
        this.chatClient = ChatClient.builder(openAiChatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultToolCallbacks(mcpToolProvider).build();
    }
```
* 接口
```
public class OpenAiController {
    @Resource
    private ChatClientService chatClientService;
    
    @GetMapping(value = "/ai/generateAsString")
    public String generateAsString(@RequestParam("message") String message) {
        return chatClientService.generateAsString(message);
    }
}
```
启动测试
访问地址：
  
