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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


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
        return ResponseEntity.status(HttpStatus.OK).body(adviceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Advice> getAdviceById(@PathVariable Long id) {
        return ResponseEntity.of(adviceService.get(id));
    }


    @PostMapping
    public ResponseEntity<Void> addAdvice(@Valid @RequestBody Advice advice) {
        adviceService.save(advice);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping(value = "/upload", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Void> addAdviceWithPhoto(@RequestPart(required = false) MultipartFile file, @Valid @RequestPart Advice advice) {
        if (file != null) {
            advice.setFilePath(adviceService.storeFile(file, advice));
        }
        adviceService.save(advice);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAdvice(@PathVariable Long id, @Valid @RequestBody Advice advice) {
        Advice oldAdvice = adviceService.get(id).orElse(null);
        if (oldAdvice == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        oldAdvice.setTitle(advice.getTitle());
        oldAdvice.setContent(advice.getContent());
        adviceService.save(oldAdvice);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvice(@PathVariable Long id) {
        if (!adviceService.remove(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }




}
