package com.shopplatform.identity_service.favorite.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "favorite")
public class Favorite {

    @EmbeddedId
    private FavoriteId id;

    @Column(nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    public Favorite(FavoriteId id) {
        this.id = id;
    }
}
