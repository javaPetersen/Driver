package pl.driver.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.driver.driver.entity.Answer;
import pl.driver.driver.service.AnswerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping
    public ResponseEntity<List<Answer>> getAllAnswers() {
        return ResponseEntity.ok(answerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable Long id) {
        return ResponseEntity.of(answerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> addNewAnswer(@Valid @RequestBody Answer answer) {
        answerService.save(answer);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAnswer(@PathVariable Long id,
                                             @Valid @RequestBody Answer answer) {
        return answerService.update(id, answer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        return answerService.removeById(id);
    }

}
