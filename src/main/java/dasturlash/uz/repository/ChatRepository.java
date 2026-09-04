package dasturlash.uz.repository;

import dasturlash.uz.entity.ChatEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRepository extends CrudRepository<ChatEntity, String> {

    @Query("""
            SELECT c FROM ChatEntity c
            WHERE c.type = 'DIRECT'
              AND EXISTS (SELECT 1 FROM ChatParticipantEntity p1 WHERE p1.chat = c AND p1.profile.id = :id1)
              AND EXISTS (SELECT 1 FROM ChatParticipantEntity p2 WHERE p2.chat = c AND p2.profile.id = :id2)
            """)
    Optional<ChatEntity> findDirectBetween(@Param("id1") String id1, @Param("id2") String id2);
}
