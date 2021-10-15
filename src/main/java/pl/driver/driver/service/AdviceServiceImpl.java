package pl.driver.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.driver.driver.entity.Advice;
import pl.driver.driver.entity.Tag;
import pl.driver.driver.repository.AdviceRepository;
import pl.driver.driver.repository.TagRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdviceServiceImpl implements AdviceService{

    private final AdviceRepository adviceRepository;
    private final TagRepository tagRepository;

    @Autowired
    public AdviceServiceImpl(AdviceRepository adviceRepository,
                             TagRepository tagRepository) {
        this.adviceRepository = adviceRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public void save(Advice advice) {
        advice.setId(null);
        adviceRepository.save(advice);
    }

    @Override
    public Optional<Advice> getAdvice(Long id) {
        return adviceRepository.findById(id);
    }

    @Override
    public List<Advice> findAll() {
        return adviceRepository.findAll();
    }

    @Override
    public void remove(Long id) {
        Advice advice = checkIfAdviceExists(id);
        adviceRepository.deleteById(advice.getId());
    }

    @Override
    public void saveAll(List<Advice> advices) {
        adviceRepository.saveAll(advices);
    }

    @Override
    public void update(Advice editedAdvice, Long oldAdviceId) {
        Advice oldAdvice = checkIfAdviceExists(oldAdviceId);
        oldAdvice.setTitle(editedAdvice.getTitle());
        oldAdvice.setContent(editedAdvice.getContent());
        oldAdvice.setTags(editedAdvice.getTags());
        oldAdvice.setTraining(editedAdvice.getTraining());
        adviceRepository.save(oldAdvice);
    }

    @Override
    public List<Tag> getAllTagsFromAdvice(Long adviceId) {
        Advice advice = checkIfAdviceExists(adviceId);
        return advice.getTags();
    }

    @Override
    public Optional<Tag> getTagByIdFromAdvice(Long adviceId, Long tagId) {
        Advice advice = checkIfAdviceExists(adviceId);
        return advice.getTags().stream()
                .filter(t -> t.getId().equals(tagId))
                .findAny();
    }

    @Override
    public ResponseEntity<String> addTagsToAdvice(Set<Long> tagIds, Long adviceId) {
        Advice advice = checkIfAdviceExists(adviceId);
        if (!checkIfTagsExists(tagIds)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One of entered tags does not exists.");
        if (!checkTagDuplicates(tagIds, advice)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Advice already contains one of entered tags");
        advice.getTags().addAll(mapListOfTags(tagIds));
        adviceRepository.save(advice);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<String> updateTagsInAdvice(Set<Long> tagIds, Long adviceId) {
        Advice advice = checkIfAdviceExists(adviceId);
        if (!checkIfTagsExists(tagIds)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One of entered tags does not exists.");
        advice.setTags(mapListOfTags(tagIds));
        adviceRepository.save(advice);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<String> deleteTagFromAdvice(Long adviceId, Long tagId) {
        Advice advice = checkIfAdviceExists(adviceId);
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        if (optionalTag.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tag does not exists");
        if (!advice.getTags().contains(optionalTag.get())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Advice does not contain that tag");
        advice.getTags().remove(optionalTag.get());
        adviceRepository.save(advice);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private List<Tag> mapListOfTags(Set<Long> tagIds) {
        return tagIds.stream()
                .map(tagRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Boolean checkIfTagsExists(Set<Long> tagIds) {
        return tagIds.stream()
                .map(tagRepository::findById)
                .noneMatch(Optional::isEmpty);
    }

    private Boolean checkTagDuplicates(Set<Long> tagIds, Advice advice) {
        return tagIds.stream()
                .map(tagRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .noneMatch(t -> advice.getTags().contains(t));
    }

    private Advice checkIfAdviceExists(Long adviceId) {
        Optional<Advice> optionalAdvice = adviceRepository.findById(adviceId);
        if (optionalAdvice.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Advice not found");
        }
        return optionalAdvice.get();
    }
}
