package com.urlshortener.shorturl.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "url")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Url {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "long_url", length = 500, unique = true, nullable = false)
    private String longUrl;

    @Column(name = "short_url", length = 10, unique = true, nullable = false)
    private String shortUrl;

    @Column(name = "count")
    private Integer count;
}
