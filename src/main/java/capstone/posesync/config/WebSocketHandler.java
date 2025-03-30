package capstone.posesync.config;

import capstone.posesync.dto.SquatRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<String, WebSocketSession>();

    @Override
    // 최초 연결 성립 후 해당 WebSocket Session 저장
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        CLIENTS.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // json 문자열
        String jsonData = message.getPayload();
        // 해당 User의 Session ID
        String id = session.getId();

        ObjectMapper objectMapper = new ObjectMapper();
        // json 문자열을 SquatRequestDTO 클래스로 매핑
        SquatRequestDTO squatRequestDTO = objectMapper.readValue(jsonData, SquatRequestDTO.class);

        // 스쿼트 DTO 데이터를 처리해서 가이드 라인 데이터, ResponseDTO 데이터 생성
        // 해당 메소드에서 처리하든, Service 계층에 위임해서 처리하든.

        // 처리된 데이터를 해당 User에게만 송신
        CLIENTS.entrySet().forEach(sessionId -> {
            if (sessionId.equals(id)) {
                // 데이터를 송신한 User에게만 가이드라인 데이터 전송
            }
        });
    }

    @Override
    // 연결 해제 후 WebSocket Session 삭제
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        CLIENTS.remove(session.getId());
    }
}
