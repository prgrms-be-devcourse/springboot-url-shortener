package com.prgrms.shortener.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String originalUrl;

    @NotBlank
    private String uniqueKey;

    protected Url() {

    }

    private Url(Builder builder) {

        this.originalUrl = builder.originalUrl;
        this.uniqueKey = builder.uniqueKey;
    }

    static class Builder {

        private String originalUrl;
        private String uniqueKey;

        public Builder originalUrl(String originalUrl) {

            this.originalUrl = originalUrl;
            return this;
        }

        public Builder uniqueKey(String uniqueKey) {

            this.uniqueKey = uniqueKey;
            return this;
        }

        public Url build() {

            return new Url(this);
        }
    }

    public static Builder builder() {

        return new Builder();
    }
}
