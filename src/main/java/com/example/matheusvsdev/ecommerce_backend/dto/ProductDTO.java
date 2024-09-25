package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.Category;
import com.example.matheusvsdev.ecommerce_backend.entities.Product;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductDTO {

    private Long id;

    @NotBlank(message = "Campo requerido")
    @Size(min = 3, message = "Mínimo 3 caracteres")
    private String name;

    @NotBlank(message = "Descrição não preenchida")
    @Size(min = 10, message = "Descrição precisa ter no mínimo 10 caracteres")
    private String description;

    @NotBlank(message = "Campo img não preenchido")
    private String img;

    @Positive(message = "A quantidade dever ser positiva")
    private Integer quantity;

    @Positive(message = "O preço dever ser positivo")
    private Double price;

    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, String img, Integer quantity, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.img = img;
        this.quantity = quantity;
        this.price = price;
    }

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        img = entity.getImg();
        quantity = entity.getQuantity();
        price = entity.getPrice();
    }

    public ProductDTO(Product entity, Set<Category> categories) {
        this(entity);
        categories.forEach(category -> this.categories.add(new CategoryDTO(category)));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImg() {
        return img;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }
}
