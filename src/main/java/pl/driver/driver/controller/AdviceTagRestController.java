package pl.driver.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.driver.driver.entity.Tag;
import pl.driver.driver.service.AdviceService;
import pl.driver.driver.service.TagService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/advice/{adviceId}/tag")
public class AdviceTagRestController {

    private final AdviceService adviceService;
    private final TagService tagService;

    @Autowired
    public AdviceTagRestController(AdviceService adviceService, TagService tagService) {
        this.adviceService = adviceService;
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTagsFromAdvice(@PathVariable Long adviceId) {
        return ResponseEntity.status(HttpStatus.OK).body(adviceService.getAllTagsFromAdvice(adviceId));
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<Tag> getTagByIdFromAdvice(@PathVariable Long adviceId,
                                                    @PathVariable Long tagId) {
        return ResponseEntity.of(adviceService.getTagByIdFromAdvice(adviceId, tagId));
    }

    @PostMapping
    public ResponseEntity<String> addTagsToAdvice(@PathVariable Long adviceId,
                                                  @RequestBody Set<Long> tagIds) {
        if (tagIds.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Entered value cannot be null or empty");
        return adviceService.addTagsToAdvice(tagIds, adviceId);
    }

    @PutMapping
    public ResponseEntity<String> updateTagsInAdvice(@PathVariable Long adviceId,
                                                 @RequestBody Set<Long> tagIds) {
        return adviceService.updateTagsInAdvice(tagIds, adviceId);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<String> deleteTagFromAdvice(@PathVariable Long adviceId,
                                                      @PathVariable Long tagId) {
        return adviceService.deleteTagFromAdvice(adviceId, tagId);
    }






}

