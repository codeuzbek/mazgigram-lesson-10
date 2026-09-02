package dasturlash.uz.repository;

import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.ChatStatus;
import dasturlash.uz.enums.ProfileStatus;
import dasturlash.uz.mapper.ChatAndGroupMapperDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>, PagingAndSortingRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByEmailAndVisibleTrue(String username);

    Optional<ProfileEntity> findByIdAndVisibleTrue(String id);

    @Modifying
    @Transactional
    @Query("update ProfileEntity  set photoId =?2 where id = ?1 ")
    void updatePhoto(String id, String photoId);

    @Modifying
    @Transactional
    @Query("update ProfileEntity  set profileStatus =?2 where id = ?1 ")
    void changeStatus(String id, ProfileStatus status);

    @Query(value = """
            select p.id as chatId, concat(p.name,' ',p.surname) as chatName, p.photo_id as chatPhotoId,'USER' as chatType,
            	   lm.content as mContent, lm.created_date as mCreatedDate, lm.is_receiver_read as receiverRead, lm.sender_id as senderId, lm.receiver_id as receiverId,
            	   (select count(*) from chat_message where is_receiver_read = false and receiver_id = :profileId and sender_id = p.id) as unreadMessageCount
            from profile as p
             left join lateral (select * from chat_message as cm 
            			 where ((cm.sender_id = p.id and cm.receiver_id=:profileId) or (cm.sender_id=:profileId and cm.receiver_id = p.id )) and cm.visible = true
            			    order by cm.created_date desc limit 1 ) as lm on true
            WHERE p.id != :profileId
            and p.visible = true and p.profile_status = 'ACTIVE'
            ORDER BY mCreatedDate DESC NULLS LAST
            """, nativeQuery = true,
            countQuery = """
                    select count(*)
                    from profile as p
                    WHERE p.id != :profileId and p.visible = true and p.profile_status = 'ACTIVE'
                    """)
    Page<ChatAndGroupMapperDTO> getChatAndGroupList(@Param("profileId") String profileId, Pageable pageable);

}









