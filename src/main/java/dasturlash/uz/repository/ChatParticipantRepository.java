package dasturlash.uz.repository;

import dasturlash.uz.entity.ChatParticipantEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatParticipantRepository extends CrudRepository<ChatParticipantEntity, String> {

    List<ChatParticipantEntity> findAllByChatId(String chatId);

    boolean existsByChatIdAndProfileId(String chatId, String profileId);
}
