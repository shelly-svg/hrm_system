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

@Data
@Entity
@Table(name = "opportunity")
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @Column(name = "candidate_id", nullable = false)
    private long candidateId;

    @Column(name = "status", length = 30, nullable = false)
    private String status;

    @Column(name = "cv_file_name", length = 100, nullable = false)
    private String cvFileName;

    @Column(name = "cover_letter_file_name", length = 100, nullable = false)
    private String coverLetterFileName;

}
