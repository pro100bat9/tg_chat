package com.example.scrapper.entity;

import com.example.scrapper.entity.pk.SubscriptionPk;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscription")
@IdClass(SubscriptionPk.class)
@Getter
@Setter
@NoArgsConstructor
public class Subscription {
    @Id
    @Column(name = "chat_id")
    private Long chatId;
    @Id
    @Column(name = "link_id")
    private Long linkId;

    public Subscription(Long chatId, Long linkId) {
        this.chatId = chatId;
        this.linkId = linkId;
    }
}
