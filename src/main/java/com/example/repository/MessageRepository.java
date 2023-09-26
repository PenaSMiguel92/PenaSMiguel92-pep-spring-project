package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;

@Transactional
public interface MessageRepository extends JpaRepository<Message, Integer>{
    //Note: namedqueries don't work with underscore containing fields
    @Query("FROM Message WHERE posted_by = :postedByVar")
    public List<Message> findAllByPostedBy(@Param("postedByVar") int id);

    @Modifying
    @Query("DELETE Message m WHERE m.message_id = :messageIdVar")
    public int deleteMessageByMessageId(@Param("messageIdVar") int id);

    @Modifying
    @Query("UPDATE Message m SET m.message_text = :messageTextVar WHERE m.message_id = :messageIdVar")
    public int updateMessageByMessageId(@Param("messageTextVar") String message, @Param("messageIdVar") int id);
}
