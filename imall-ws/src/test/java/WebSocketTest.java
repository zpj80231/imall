import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * socket 连接数测试
 *
 * @author zhangpengjun
 * @date 2023/08/04
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@ClientEndpoint
public class WebSocketTest {

    private String deviceId;
    private static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    private boolean connect() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://localhost/gw/ws/sdk?agentId=" + deviceId + "&extension=" + deviceId + "&projectId=70000001001&token=9du6";
        try {
            Session session = container.connectToServer(WebSocketTest.class, URI.create(uri));
            sessionMap.put(deviceId, session);
            log.info("sessionCount: {}, deviceId: {}, Connecting to: {}", sessionMap.size(), deviceId, uri);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        int threadNum = 20;
        int threadExecCount = 500;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 1; i <= threadNum; i++) {
            String threadName = "t" + i;
            new Thread(() -> {
                for (int j = 1; j <= threadExecCount; j++) {
                    WebSocketTest wSocketTest = new WebSocketTest(threadName + "-" + j);
                    if (!wSocketTest.connect()) {
                        log.error("{}, 连接异常测试结束!", threadName);
                        countDownLatch.countDown();
                        break;
                    }
                    if (j == threadExecCount) {
                        log.debug("{}, 连接正常测试结束!", threadName);
                        countDownLatch.countDown();
                    }
                }
            }, threadName).start();
        }
        countDownLatch.await();
        log.warn("全部测试结束，{}", sessionMap.size());
    }

}