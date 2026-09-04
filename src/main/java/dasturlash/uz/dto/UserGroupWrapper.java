package dasturlash.uz.dto;

import dasturlash.uz.enums.ChatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserGroupWrapper {
    private String id;
    private ChatType chatType;
}