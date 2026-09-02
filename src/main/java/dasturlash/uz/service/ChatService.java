package dasturlash.uz.service;

import dasturlash.uz.config.CustomUserDetails;
import dasturlash.uz.dto.ChatDTO;
import dasturlash.uz.dto.chat.ChatMessageDTO;
import dasturlash.uz.dto.profile.ProfileDTO;
import dasturlash.uz.enums.ChatStatus;
import dasturlash.uz.enums.ChatType;
import dasturlash.uz.mapper.ChatAndGroupMapperDTO;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AttachService attachService;

    public PageImpl<ChatDTO> getChatList(Principal principal, int page, int size) {
//        CustomUserDetails currentProfile =  SpringSecurityUtil.getCurrentProfile();
        CustomUserDetails currentProfile = (CustomUserDetails) ((Authentication) principal).getPrincipal();
        Pageable pageable = PageRequest.of(page, size);
        Page<ChatAndGroupMapperDTO> pageResult = profileRepository.getChatAndGroupList(currentProfile.getId(), pageable);
        List<ChatDTO> dtoList = pageResult.stream().map(item -> toDTO(item)).toList();
        return new PageImpl<>(dtoList, pageable, pageResult.getTotalElements());
    }

    public ChatDTO toDTO(ChatAndGroupMapperDTO mapper) {
        ChatDTO dto = new ChatDTO();
        dto.setId(mapper.getChatId());
        dto.setName(mapper.getChatName());
        dto.setUnreadMessageCount(mapper.getUnreadMessageCount());
        if (mapper.getChatPhotoId() != null) {
            dto.setPhoto(attachService.attachDTO(mapper.getChatPhotoId()));
        }
//        if (mapper.getChatStatus() != null) {
//            dto.setChatStatus(ChatStatus.valueOf(mapper.getChatStatus()));
//        }
        if (mapper.getChatType() != null) {
            dto.setChatType(ChatType.valueOf(mapper.getChatType()));
        }

        ChatMessageDTO chatMessage = new ChatMessageDTO();
        chatMessage.setContent(mapper.getMContent());
        chatMessage.setCreatedDate(mapper.getMCreatedDate());
        chatMessage.setIsReceiverRead(mapper.getReceiverRead());
        chatMessage.setSenderId(mapper.getSenderId()); // >
        chatMessage.setReceiverId(mapper.getReceiverId());

        ProfileDTO profile = new ProfileDTO();
        profile.setName(mapper.getSenderName());

        chatMessage.setSender(profile);
        dto.setMessage(chatMessage);

        return dto;
    }

}
