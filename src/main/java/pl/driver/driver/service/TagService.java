package pl.driver.driver.service;

import pl.driver.driver.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    void save(Tag tag);
    Optional<Tag> getTagById(Long id);
    List<Tag> findAll();
    void removeById(Long id);
    void update(Tag newTag, Long oldTagId);
}
