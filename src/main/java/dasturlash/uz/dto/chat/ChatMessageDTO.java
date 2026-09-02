package dasturlash.uz.dto.chat;

import dasturlash.uz.dto.attach.AttachDTO;
import dasturlash.uz.dto.profile.ProfileDTO;
import dasturlash.uz.enums.ChatMessageType;
import dasturlash.uz.enums.ChatType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDTO {
    private String id;
    private String content;
    private String senderId;
    private String receiverId;
    private ChatType chatType; // CHAT,GROUP
    private ChatMessageType chatMessageType;
    private LocalDateTime createdDate;
    private Boolean isReceiverRead;
    //
    private String socketMessageType; // NewMessage,UpdateMessage,DeleteMessage
    private ChatMessageDTO repliedMessage;
    private AttachDTO attach;
    private String capture;

    private ProfileDTO sender;
}