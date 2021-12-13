package org.example.api.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserSkill implements Serializable {

    private static final long serialVersionUID = -234742849823924323L;

    private User user;
    private Skill skill;

}
