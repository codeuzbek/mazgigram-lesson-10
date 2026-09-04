package dasturlash.uz.service;

import dasturlash.uz.config.CustomUserDetails;
import dasturlash.uz.dto.ChatDTO;
import dasturlash.uz.dto.chat.ChatMessageDTO;
import dasturlash.uz.dto.profile.ProfileDTO;
import dasturlash.uz.entity.ChatEntity;
import dasturlash.uz.enums.ChatType;
import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.mapper.ChatAndGroupMapperDTO;
import dasturlash.uz.repository.ChatRepository;
import dasturlash.uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ChatParticipationService chatParticipationService;
    @Autowired
    private ChatRepository chatRepository;

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

    public ChatEntity createUserChat(String profileId, String receiverId) {
        Optional<ChatEntity> chatOptional = chatRepository.findDirectBetween(profileId, receiverId);

        return chatOptional.orElseGet(() -> { // create if not exits
            ChatEntity chat = new ChatEntity();
            chat.setType(ChatType.USER);
            chatRepository.save(chat);
            // both sides are participants
            chatParticipationService.create(chat.getId(), profileId);
            chatParticipationService.create(chat.getId(), receiverId);

            return chat;
        });
    }

    /**
     * Returns the chat between sender and receiver. Receiver can be a Group or a Profile.
     * Group chat is created together with the group, direct chat is created on the first message.
     */
    public ChatEntity checkAndCreateChat(String senderId, String receiverId, ChatType chatType) {
        if (receiverId == null) {
            throw new AppBadException("Receiver should be given");
        }

        if (senderId.equals(receiverId)) {   // receiver is a Profile
            throw new AppBadException("You can not chat with yourself");
        }

        // receiver is a Group...
        // User
        return createUserChat(senderId, receiverId); // returns existing one if it is already created
    }
}
