package dasturlash.uz.dto;

import dasturlash.uz.dto.attach.AttachDTO;
import dasturlash.uz.dto.chat.ChatMessageDTO;
import dasturlash.uz.enums.ChatStatus;
import dasturlash.uz.enums.ChatType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDTO {
    private String id;
    private String name;
    private AttachDTO photo;
    private ChatStatus chatStatus;
    private ChatMessageDTO message;
    private ChatType chatType; // USER,GROUP
    private Long unreadMessageCount;
}
