package com.vuongkma.media.repositories;

import com.vuongkma.media.entities.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<MediaEntity,Long> {
}
