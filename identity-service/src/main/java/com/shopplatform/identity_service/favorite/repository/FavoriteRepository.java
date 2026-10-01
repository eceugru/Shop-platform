package com.shopplatform.identity_service.favorite.repository;

import com.shopplatform.identity_service.favorite.entity.Favorite;
import com.shopplatform.identity_service.favorite.entity.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
}
