package dasturlash.uz.mapper;

import dasturlash.uz.enums.ChatMessageType;
import dasturlash.uz.enums.ChatType;

import java.time.LocalDateTime;

public interface ChatMessageMapper {
    String getId();

    String getContent();

    String getSenderId();

    String getReceiverId();

    ChatType getChatType(); // CHAT,GROUP

    String getRepliedMessageId();

    String getCapture();

    String getAttachId();

    ChatMessageType getChatMessageType();

    String getRepliedMessageContent();

    LocalDateTime getCreatedDate();

    Boolean getIsReceiverRead();

    String getSenderName();

    String getSenderSurname();

    String getSenderPhotoId();
}
