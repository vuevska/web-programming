package mk.finki.ukim.mk.lab.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "balloons")
public class Balloon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(length = 2000)
    private String description;
    @ManyToOne
    private Manufacturer manufacturer;

    public Balloon(String name, String description, Manufacturer manufacturer) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
    }
    public Balloon() {
    }
}
