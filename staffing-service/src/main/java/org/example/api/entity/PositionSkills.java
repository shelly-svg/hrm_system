package org.example.api.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "position_skills")
@IdClass(PositionSkill.class)
public class PositionSkills {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @Id
    private long skillId;

    @Column(name = "level", length = 30, nullable = false)
    private String level;

}
