package com.vuongkma.media.services;

import com.vuongkma.media.entities.MediaEntity;
import com.vuongkma.media.exceptions.NotFoundException;
import com.vuongkma.media.repositories.MediaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {
    @Autowired
    private MediaRepository mediaRepository;
    public MediaEntity insert(MediaEntity data) {
        return this.mediaRepository.save(data);
    }

    @Transactional
    public MediaEntity update(Long id, MediaEntity data) {
        var entity = this.mediaRepository.findById(id).orElseThrow(() -> new NotFoundException("This data does not exists"));

        if (data.getName() != null && !data.getName().isBlank()) {
            entity.setName(data.getName());
        }

        if (data.getUrl() != null) {
            entity.setUrl(data.getUrl());
        }

        if (data.getType() != null) {
            entity.setType(data.getType());
        }

        this.mediaRepository.save(entity);
        return entity;
    }

    @Transactional
    public boolean delete(Long id) {
        this.mediaRepository.deleteById(id);

        return true;
    }

}
