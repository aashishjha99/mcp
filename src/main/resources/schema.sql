CREATE TABLE SPRING_AI_CHAT_MEMORY (
                                       message_id BIGINT NOT NULL AUTO_INCREMENT,
                                       conversation_id VARCHAR(255) NOT NULL,
                                       content TEXT,
                                       type VARCHAR(50),
                                       `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       PRIMARY KEY (message_id),
                                       INDEX SPRING_AI_CHAT_MEMORY_CONV_IDX (conversation_id),
                                       INDEX SPRING_AI_CHAT_MEMORY_CONV_TS_IDX (conversation_id, `timestamp`)
);