package pl.driver.driver.service;

import pl.driver.driver.entity.Training;

import java.util.List;
import java.util.Optional;

public interface TrainingService {

    List<Training> findAll();
    Optional<Training> findById(Long id);
    void save(Training training);
    Boolean removeById(Long id);

}
