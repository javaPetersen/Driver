package pl.driver.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.driver.driver.entity.Training;
import pl.driver.driver.service.TrainingService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/training")
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
    public ResponseEntity<Training> addNewTraining(@Valid @RequestBody Training training) {
        trainingService.save(training);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Training> editTraining(@PathVariable Long id, @Valid @RequestBody Training training) {
        Optional<Training> optionalTraining = trainingService.findById(id);
        if (optionalTraining.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        training.setId(optionalTraining.get().getId());
        trainingService.save(training);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        if (!trainingService.removeById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
