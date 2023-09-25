package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
    @Query("FROM Message WHERE posted_by = :postedByVar")
    public List<Message> findAllByPostedBy(@Param("postedByVar") int id);

    @Modifying
    @Query("DELETE FROM Message WHERE message_id = :messageIdVar")
    public int deleteMessageByMessageId(@Param("messageIdVar") int id);

    @Modifying
    @Query("UPDATE Message SET message_text = :messageTextVar WHERE message_id = :messageIdVar")
    public int updateMessageByMessageId(@Param("messageTextVar") String message, @Param("messageIdVar") int id);
}
