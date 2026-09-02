package dasturlash.uz.controller;

import dasturlash.uz.dto.ChatDTO;
import dasturlash.uz.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping("")
    public ResponseEntity<PageImpl<ChatDTO>> getChatList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                                         Principal principal) {

        PageImpl<ChatDTO> result = chatService.getChatList(principal, page - 1, size);
        return ResponseEntity.ok(result);
    }

}
