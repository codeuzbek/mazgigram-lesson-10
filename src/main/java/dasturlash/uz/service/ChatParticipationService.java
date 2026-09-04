package dasturlash.uz.service;

import dasturlash.uz.entity.ChatParticipantEntity;
import dasturlash.uz.repository.ChatParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ChatParticipationService {

    @Autowired
    private ChatParticipantRepository chatParticipantRepository;

    public ChatParticipantEntity create(String chatId, String profileId) {
        ChatParticipantEntity entity = new ChatParticipantEntity();
        entity.setChatId(chatId);
        entity.setProfileId(profileId);
        entity.setLastReadAt(Instant.now());
        chatParticipantRepository.save(entity);
        return entity;
    }

    public List<ChatParticipantEntity> getParticipantList(String chatId) {
        return chatParticipantRepository.findAllByChatId(chatId);
    }

    public boolean isParticipant(String chatId, String profileId) {
        return chatParticipantRepository.existsByChatIdAndProfileId(chatId, profileId);
    }
}
