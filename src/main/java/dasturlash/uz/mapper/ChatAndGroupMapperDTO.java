package dasturlash.uz.mapper;

import java.time.LocalDateTime;

public interface ChatAndGroupMapperDTO {
    String getChatId();

    String getChatName();

    String getChatPhotoId();

    String getChatStatus();

    String getMContent();

    LocalDateTime getMCreatedDate();

    Boolean getReceiverRead();

    String getSenderId();

    String getReceiverId();

    String getChatType();

    String getSenderName();

    Long getUnreadMessageCount();
}
