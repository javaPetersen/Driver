package pl.driver.driver.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "trainings")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "training")
    private Advice advice;

    @ManyToMany
    private List<Question> questions;

    @NotNull
    private Boolean isPassed = false;

    @NotNull
    @Min(1)
    private Integer points;


    

}
