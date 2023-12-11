package com.programmers.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "URI")
public class UriEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uri_entity_seq_generator")
    @SequenceGenerator(name = "uri_entity_seq_generator", sequenceName = "uri_entity_seq", initialValue = 1000000)
    private Long id;

    private String originalUri;
    private String shortUri;
    private int count = 0;

    public UriEntity() {
    }
    public UriEntity(String uri) {
        this.originalUri= uri;
        this.shortUri = null;
    }

    public void initShortUri(String shortUri) {
        this.shortUri = shortUri;
    }

    public void increaseCount() {
        this.count++;
    }
}
