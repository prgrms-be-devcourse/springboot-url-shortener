package com.example.springbootboardjpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "short_urls")
@Getter
@Setter
@NoArgsConstructor
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "short_id", nullable = false, unique = true)
    private String shortId;

    @Column(name= "url", nullable = false, unique = true)
    private String url;

    @Column(name= "count", nullable = false)
    private long count = 0;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    public void setCount() {
        ++count;
    }
}
