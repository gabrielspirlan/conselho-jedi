package com.padwan.test.domain.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "jedis")
public class Jedi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @NotBlank
    @Column (nullable = false)
    private java.lang.String name;

    @NotBlank
    @Column (nullable = false)
    @Pattern(regexp = "Padawan|Jedi|Mestre Jedi|")
    private String status;

    @Column (nullable = false)
    @Min(value = 0)
    private Long midichlorians;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Jedi mentor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMidichlorians() {
        return midichlorians;
    }

    public void setMidichlorians(Long midichlorians) {
        this.midichlorians = midichlorians;
    }

    public Jedi getMentor() {
        return mentor;
    }

    public void setMentor(Jedi mentor) {
        this.mentor = mentor;
    }

    public Jedi () {

    }


    public Jedi(Long id, String name, String status, Long midichlorians, Jedi mentor) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.midichlorians = midichlorians;
        this.mentor = mentor;
    }


    public Jedi(String name, String status, Long midichlorians) {
        this.name = name;
        this.status = status;
        this.midichlorians = midichlorians;
    }

    public Jedi(String name, String status, Long midichlorians, Jedi mentor) {
        this.name = name;
        this.status = status;
        this.midichlorians = midichlorians;
        this.mentor = mentor;
    }
}
