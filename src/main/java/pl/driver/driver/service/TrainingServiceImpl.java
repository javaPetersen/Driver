package pl.driver.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.driver.driver.entity.Training;
import pl.driver.driver.repository.TrainingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService{

    private final TrainingRepository trainingRepository;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public List<Training> findAll() {
        return trainingRepository.findAll();
    }

    @Override
    public Optional<Training> findById(Long id) {
        return trainingRepository.findById(id);
    }

    @Override
    public void save(Training training) {
        trainingRepository.save(training);
    }

    @Override
    public Boolean removeById(Long id) {
        Optional<Training> optionalTraining = trainingRepository.findById(id);
        if (optionalTraining.isPresent()) {
            trainingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
