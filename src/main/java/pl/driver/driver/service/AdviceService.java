package pl.driver.driver.service;

import org.springframework.http.ResponseEntity;
import pl.driver.driver.entity.Advice;
import pl.driver.driver.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AdviceService {

    void save(Advice advice);
    Optional<Advice> getAdvice(Long id);
    List<Advice> findAll();
    void remove(Long id);
    void saveAll(List<Advice> advices);
    void update(Advice editedAdvice, Long oldAdviceId);
    List<Tag> getAllTagsFromAdvice(Long adviceId);
    Optional<Tag> getTagByIdFromAdvice(Long adviceId, Long tagId);
    ResponseEntity<String> addTagsToAdvice(Set<Long> tagIds, Long adviceId);
    ResponseEntity<String> updateTagsInAdvice(Set<Long> tagIds, Long adviceId);

    ResponseEntity<String> deleteTagFromAdvice(Long adviceId, Long tagId);
}
