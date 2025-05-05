package edu.meialua.kidsgrace.model;

import org.springframework.web.multipart.MultipartFile;

public class ToyDTO {
    private String name;
    private String category;
    private String description;
    private String brand;
    private MultipartFile image;
    private float value;

    public ToyDTO() {
    }

    public ToyDTO( String name, String category, String description, String brand, MultipartFile  image, float value) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.brand = brand;
        this.image = image;
        this.value = value;
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

    public MultipartFile  getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
