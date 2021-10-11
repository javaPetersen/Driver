package pl.driver.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.driver.driver.entity.Advice;
import pl.driver.driver.entity.Tag;
import pl.driver.driver.service.AdviceService;
import pl.driver.driver.service.TagService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class TagRestController {

    private final TagService tagService;
    private final AdviceService adviceService;

    @Autowired
    public TagRestController(TagService tagService, AdviceService adviceService) {
        this.tagService = tagService;
        this.adviceService = adviceService;
    }

    @GetMapping("/tag")
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.findAll());
    }

    @GetMapping("/tag/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        return ResponseEntity.of(tagService.getTagById(id));
    }

    @PostMapping("/advice/{id}/tag")
    @Transactional
    public ResponseEntity<Void> addTagToAdvice(@PathVariable Long id, @Valid @RequestBody Tag... tag) {
        Optional<Advice> optionalAdvice = adviceService.get(id);
        if (optionalAdvice.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Arrays.stream(tag).forEach(tagService::save);
        Arrays.stream(tag).forEach(t -> optionalAdvice.get().getTags().add(t));

        adviceService.save(optionalAdvice.get());


        return ResponseEntity.status(HttpStatus.CREATED).build();

    }


}
