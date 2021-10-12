package pl.driver.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.driver.driver.entity.Tag;
import pl.driver.driver.service.TagService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/tag")
public class TagRestController {

    private final TagService tagService;

    @Autowired
    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        return ResponseEntity.of(tagService.getTagById(id));
    }


    @PostMapping
    public ResponseEntity<String> createNewTag(@Valid @RequestBody Tag tag) {
        tagService.save(tag);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editExistingTag(@PathVariable Long id,
                                                  @Valid @RequestBody Tag tag) {
        tagService.update(tag, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTagById(@PathVariable Long id) {
        tagService.removeById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
