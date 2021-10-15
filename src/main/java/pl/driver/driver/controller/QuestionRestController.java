package pl.driver.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.driver.driver.entity.Answer;
import pl.driver.driver.entity.Question;
import pl.driver.driver.service.QuestionService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/question")
public class QuestionRestController {

    private final QuestionService service;

    @Autowired
    public QuestionRestController(QuestionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.of(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> addQuestion(@Valid @RequestBody Question question) {
        service.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateQuestion(@PathVariable Long id,
                                               @Valid @RequestBody Question question) {
        return service.update(id, question);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        return service.removeById(id);
    }

    @GetMapping("/{id}/answer")
    public ResponseEntity<Set<Answer>> getAllAnswersFromQuestion(@PathVariable Long id) {
        return service.getAllAnswersFromQuestion(id);
    }

    @GetMapping("/{questionId}/answer/{answerId}")
    public ResponseEntity<Answer> getAnswerFromQuestionById(@PathVariable Long questionId,
                                                            @PathVariable Long answerId) {
        return service.getAnswerFromQuestionById(questionId, answerId);
    }

    @PutMapping("/{questionId}/answer/{answerId}")
    public ResponseEntity<Void> putAnswerToQuestion(@PathVariable Long questionId,
                                                    @PathVariable Long answerId) {
        return service.putAnswerToQuestion(questionId, answerId);
    }

    @DeleteMapping("/{questionId}/answer/{answerId}")
    public ResponseEntity<Void> removeAnswerFromQuestion(@PathVariable Long questionId,
                                                         @PathVariable Long answerId) {
        return service.removeAnswerFromQuestion(questionId, answerId);
    }


}
