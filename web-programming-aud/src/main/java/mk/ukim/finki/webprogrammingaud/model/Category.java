package mk.ukim.finki.webprogrammingaud.model;

import lombok.Data;

import javax.persistence.*;

@Data
/**
* @Data annotation is part of
 * the Lombok dependency and
 * by using it we don't have
 * to write any getters or
 * setters for the class
* */
@Entity
/**
 * Class that is persisted,
 * one that is connected to the database
* */
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 4000)
    private String description;

    public Category(String name, String description) {
        this.id = (long) (Math.random() * 1000);
        this.name = name;
        this.description = description;
    }

    public Category() {

    }
}
