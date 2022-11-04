package mk.ukim.finki.webprogrammingaud.model;

import lombok.Data;

@Data
/**
* @Data annotation is part of
 * the Lombok dependency and
 * by using it we don't have
 * to write any getters or
 * setters for the class
* */
public class Category {

    private Long id;
    private String name;
    private String description;

    public Category(String name, String description) {
        this.id = (long) (Math.random() * 1000);
        this.name = name;
        this.description = description;
    }
}
