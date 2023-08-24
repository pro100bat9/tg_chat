package com.example.scrapper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tg_chat")
@Getter
@Setter
@NoArgsConstructor
public class Chat {
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "subscription", joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "id")
    )
    private List<Link> subscription;

    public Chat(Long id) {
        this.id = id;
    }
}
