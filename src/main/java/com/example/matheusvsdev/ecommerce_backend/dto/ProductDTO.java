package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.Category;
import com.example.matheusvsdev.ecommerce_backend.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Positive(message = "O preço dever ser positivo")
    private Double price;

    private Set<CategoryDTO> categories = new HashSet<>();

    private boolean available = true;

    private InventoryDTO inventory;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, String img, Double price, boolean available, InventoryDTO inventory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.img = img;
        this.price = price;
        this.available = available;
        this.inventory = inventory;
    }

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        img = entity.getImg();
        price = entity.getPrice();
        available = entity.isAvailable();
        inventory = new InventoryDTO(entity.getInventory());
        this.categories = new HashSet<>(entity.getCategories())
                .stream().map(CategoryDTO::new)
                .collect(Collectors.toSet());
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

    public Double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public InventoryDTO getInventory() {
        return inventory;
    }

    public void setInventory(InventoryDTO inventory) {
        this.inventory = inventory;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }
}
