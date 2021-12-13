package org.example.api.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    @Column(name = "soft_skills", length = 400, nullable = false)
    private String softSkills;

    @Column(name = "hard_skills", length = 400, nullable = false)
    private String hardSkills;

    @Column(name = "strengths", length = 200, nullable = false)
    private String strengths;

    @Column(name = "weaknesses", length = 200, nullable = false)
    private String weaknesses;

    @Column(name = "additional_information", length = 400)
    private String additionalInformation;

}
