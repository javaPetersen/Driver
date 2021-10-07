package pl.driver.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.driver.driver.entity.Advice;
import pl.driver.driver.service.AdviceService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/advice")
public class AdviceRestController {


    private final AdviceService adviceService;

    @Autowired
    public AdviceRestController(AdviceService adviceService) {
        this.adviceService = adviceService;
    }

    @GetMapping
    public ResponseEntity<List<Advice>> allAdvices() {
        return new ResponseEntity<>(adviceService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Advice> getAdviceById(@PathVariable Long id) {
         return ResponseEntity.of(adviceService.get(id));
    }


    @PostMapping
    public ResponseEntity<Void> addAdvice(@Valid @RequestBody Advice advice) {
        adviceService.save(advice);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAdvice(@PathVariable Long id, @Valid @RequestBody Advice advice) {
        Optional<Advice> optionalAdvice = adviceService.get(id);
        if (optionalAdvice.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        advice.setId(optionalAdvice.get().getId());
        adviceService.save(advice);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvice(@PathVariable Long id) {
        if (!adviceService.remove(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
