package org.example.api.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class ProjectSkill implements Serializable {

    private static final long serialVersionUID = -92348823748723842L;

    private Project project;
    private long skillId;

}
