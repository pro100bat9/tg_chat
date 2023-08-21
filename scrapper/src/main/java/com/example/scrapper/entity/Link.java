package com.example.scrapper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "link")
@Getter
@Setter
@NoArgsConstructor
public class Link {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "linkIdGen"
    )
    @SequenceGenerator(
            name = "linkIdGen",
            sequenceName = "link_id_generator",
            initialValue = 1, allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "url", nullable = false, unique = true)
    private String url;
    @Column(name = "last_check_time", nullable = false)
    private OffsetDateTime lastCheckTime;

    @Column(name = "update_at", nullable = false)
    private OffsetDateTime updateAtTime;

    @ManyToMany
    @JoinTable(
            name = "subscription", joinColumns = @JoinColumn(name = "link_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id")
    )
    private List<Chat> subscription;

    public Link(String url) {
        this.url = url;
    }
}
