package pl.driver.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.driver.driver.entity.Advice;
import pl.driver.driver.service.AdviceService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("api/advice")
public class AdviceRestController {


    private final AdviceService adviceService;

    @Autowired
    public AdviceRestController(AdviceService adviceService) {
        this.adviceService = adviceService;
    }

    @GetMapping
    public ResponseEntity<List<Advice>> getAllAdvices() {
        return ResponseEntity.status(HttpStatus.OK).body(adviceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Advice> getAdviceById(@PathVariable Long id) {
        return ResponseEntity.of(adviceService.getAdvice(id));
    }

    @PostMapping
    public ResponseEntity<Void> addAdvice(@Valid @RequestBody Advice advice) {
        adviceService.save(advice);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAdvice(@PathVariable Long id, @Valid @RequestBody Advice advice) {
        adviceService.update(advice, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvice(@PathVariable Long id) {
        adviceService.remove(id);
        return ResponseEntity.ok().build();
    }




}
