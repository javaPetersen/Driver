package pl.driver.driver.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "advices")
public class Advice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String title;

    @NotEmpty
    @Size(min = 3, max = 1000)
    private String content;

    @ManyToMany
    private List<Tag> tags = new LinkedList<>();

    @OneToOne
    private Training training;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @PrePersist
    public void setCreatedOn(){
        this.createdOn = LocalDateTime.now().withNano(0);
    }

    @PreUpdate
    public void setUpdatedOn(){
        this.updatedOn = LocalDateTime.now().withNano(0);
    }


}
