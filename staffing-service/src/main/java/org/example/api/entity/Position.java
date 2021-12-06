package org.example.api.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "position")
public class Position implements Serializable {

    private static final long serialVersionUID = 72348742863248324L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "job_function", length = 50, nullable = false)
    private String jobFunction;

    @Column(name = "work_format", length = 50, nullable = false)
    private String workFormat;

    @Column(name = "english_level", length = 15, nullable = false)
    private String englishLevel;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

}
