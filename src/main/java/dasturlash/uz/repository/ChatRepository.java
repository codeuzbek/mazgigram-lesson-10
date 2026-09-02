package dasturlash.uz.repository;

import dasturlash.uz.entity.ChatEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRepository extends CrudRepository<ChatEntity, String> {

}
