package org.example.api.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 3193434283249432234L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "contact_id", nullable = false)
    private long contactId;

}
