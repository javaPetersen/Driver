package pl.driver.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.driver.driver.entity.Advice;
import pl.driver.driver.entity.Tag;
import pl.driver.driver.repository.TagRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final AdviceService adviceService;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository,
                          AdviceService adviceService) {
        this.tagRepository = tagRepository;
        this.adviceService = adviceService;
    }

    @Override
    public void save(Tag tag) {
        if (duplicateCheck(tag)) {
            tagRepository.save(tag);
        }
    }

    @Override
    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public void removeById(Long id) {
        if (tagRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        separateFromAdvices(tagRepository.findById(id).get());
        tagRepository.deleteById(id);
    }

    private void separateFromAdvices(Tag tag) {
        List<Advice> collect = adviceService.findAll().stream()
                .filter(a -> a.getTags().contains(tag))
                .peek(a -> a.getTags().remove(tag))
                .collect(Collectors.toList());
        adviceService.saveAll(collect);
    }

    @Override
    public void update(Tag newTag, Long oldTagId) {
        Tag oldTag = tagRepository.findById(oldTagId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (duplicateCheck(newTag)) {
            oldTag.setName(newTag.getName());
            tagRepository.save(oldTag);
        }
    }

    private Boolean duplicateCheck(Tag tag) {
        if (tagRepository.findByName(tag.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag name already taken.");
        }
        return true;
    }


}
