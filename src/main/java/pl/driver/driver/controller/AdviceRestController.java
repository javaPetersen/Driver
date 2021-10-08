package pl.driver.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.driver.driver.entity.Advice;
import pl.driver.driver.service.AdviceService;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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


    @PostMapping(value = "/upload", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Void> addAdviceWithPhoto(@RequestPart(required = false) MultipartFile file, @Valid @RequestPart Advice advice) {
        if (file != null) {
            advice.setFilePath(adviceService.storeFile(file, advice));
        }
        adviceService.save(advice);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAdvice(@PathVariable Long id, @Valid @RequestBody Advice advice) {
        Advice oldAdvice = adviceService.get(id).orElse(null);
        if (oldAdvice == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        oldAdvice.setTitle(advice.getTitle());
        oldAdvice.setContent(advice.getContent());
        adviceService.save(oldAdvice);
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
