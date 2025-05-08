package edu.meialua.kidsgrace.adapters.in;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "toys")
public class Toy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Permite o auto increment do ID no banco
    @Column (name="id")
    private Long id;

    @Column(name = "name", length = 250, nullable = false, unique = true)
    private String name;
    @Column(name = "category", length = 250, nullable = false)
    private String category;
    @Column(name = "description", length = 250, nullable = false)
    private String description;
    @Column(name = "brand", length = 100)
    private String brand;
    @Column(name = "image",columnDefinition = "LONGBLOB", nullable = true)
    private byte[] image;
    @Column(name = "value", nullable = false)
    private float value;
    @Column(name = "visible_in_catalog", nullable = false)
    private boolean visibleInCatalog = false;

    @Override
    public String toString() {
        return "Toy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", image=" + Arrays.toString(image) +
                ", value=" + value +
                '}';
    }

    public Toy(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
    public boolean isVisibleInCatalog() {return this.visibleInCatalog;}

    public void setVisibleInCatalog(boolean visibleInCatalog) {this.visibleInCatalog = visibleInCatalog;}

}
