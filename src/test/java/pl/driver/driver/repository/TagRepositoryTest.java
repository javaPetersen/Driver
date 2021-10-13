package pl.driver.driver.repository;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.driver.driver.entity.Tag;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TagRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TagRepository tagRepository;

    @Test
    void findByName_when_tagExists_thenCorrect() {
        //given
        Tag safety = new Tag();
        safety.setName("Safety");
        entityManager.persist(safety);
        //when
        Optional<Tag> result = tagRepository.findByName("Safety");
        //then
        assertTrue(result.isPresent());
        assertEquals(result.get().getName(), safety.getName());
    }

    @Test
    void findByName_givenSafety_findDanger_shouldNotBePresent() {
        //given
        Tag safety = new Tag();
        safety.setName("Safety");
        entityManager.persist(safety);
        //when
        Optional<Tag> result = tagRepository.findByName("Danger");
        //then
        assertFalse(result.isPresent());
    }

}