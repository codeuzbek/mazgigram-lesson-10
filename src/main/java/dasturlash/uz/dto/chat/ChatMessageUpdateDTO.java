package dasturlash.uz.dto.chat;

import dasturlash.uz.enums.ChatType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageUpdateDTO {
    @NotBlank
    private String content;
    private ChatType chatType;
}