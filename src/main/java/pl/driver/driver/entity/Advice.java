package pl.driver.driver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private String filePath;

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    @OneToOne
    private Training training;


    @PrePersist
    public void setCreatedOn(){
        this.createdOn = LocalDateTime.now().withNano(0);
    }

    @PreUpdate
    public void setUpdatedOn(){
        this.updatedOn = LocalDateTime.now().withNano(0);
    }


}
