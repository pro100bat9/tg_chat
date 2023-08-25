package com.example.scrapper.entity.pk;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPk implements Serializable {
    private Long linkId;
    private Long chatId;
}
