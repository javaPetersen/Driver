package pl.driver.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.driver.driver.entity.Question;
import pl.driver.driver.entity.Training;
import pl.driver.driver.service.TrainingService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/training")
public class TrainingRestController {

    private final TrainingService trainingService;

    @Autowired
    public TrainingRestController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping
    public ResponseEntity<List<Training>> allTrainings() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(trainingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Training> getTrainingById(@PathVariable Long id) {
        return ResponseEntity.of(trainingService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> addNewTraining(@Valid @RequestBody Training training) {
        trainingService.save(training);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editTraining(@PathVariable Long id, @Valid @RequestBody Training training) {
        return trainingService.update(training, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTraining(@PathVariable Long id) {
        return trainingService.removeById(id);
    }

    @GetMapping("/{id}/question")
    public ResponseEntity<Set<Question>> getAllQuestionsFromTraining(@PathVariable Long id) {
        return trainingService.getAllQuestionsFromTraining(id);
    }

    @GetMapping("/{trainingId}/question/{questionId}")
    public ResponseEntity<Question> getQuestionFromTraining(@PathVariable Long trainingId,
                                                            @PathVariable Long questionId) {
        return trainingService.getQuestionFromTraining(trainingId, questionId);
    }

    @PutMapping("/{trainingId}/question/{questionId}")
    public ResponseEntity<String> putQuestionToTraining(@PathVariable Long trainingId,
                                                        @PathVariable Long questionId) {
        return trainingService.putQuestionToTraining(trainingId, questionId);
    }

    @DeleteMapping("/{trainingId}/question/{questionId}")
    public ResponseEntity<Void> removeQuestionFromTraining(@PathVariable Long trainingId,
                                                           @PathVariable Long questionId) {
        return trainingService.removeQuestionFromTraining(trainingId, questionId);
    }

}
