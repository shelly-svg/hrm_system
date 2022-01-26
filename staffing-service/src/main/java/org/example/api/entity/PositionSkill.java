package org.example.api.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class PositionSkill implements Serializable {

    private static final long serialVersionUID = -348274827248324L;

    private Position position;
    private long skillId;

}
