package com.example.scrapper.entity.pk;


import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class SubscriptionPk implements Serializable {
    private Long linkId;
    private Long chatId;
}
