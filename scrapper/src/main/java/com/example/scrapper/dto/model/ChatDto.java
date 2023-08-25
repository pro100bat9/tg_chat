package com.example.scrapper.dto.model;


import com.example.scrapper.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatDto {
    private Long id;

    public ChatDto(Chat chat) {
        this.id = chat.getId();
    }
}
