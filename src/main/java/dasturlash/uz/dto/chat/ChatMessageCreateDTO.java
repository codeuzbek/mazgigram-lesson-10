package dasturlash.uz.dto.chat;

import dasturlash.uz.enums.ChatMessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageCreateDTO {
    private String content;
    private String repliedMessageId;

    private String chatId;

    private ChatMessageType chatMessageType;
    private String capture;
    private String attachId;


    private String receiverId; // ProfileId or GroupId
//    @NotNull(message = "chatType should be given")
//    private ChatType chatType;

}