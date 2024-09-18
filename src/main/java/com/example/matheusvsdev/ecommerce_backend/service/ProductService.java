package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.CategoryDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.ProductDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.Category;
import com.example.matheusvsdev.ecommerce_backend.entities.Product;
import com.example.matheusvsdev.ecommerce_backend.repository.CategoryRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public ProductDTO insertProduct(ProductDTO productDTO) {
        Product product = new Product();
        assigningDtoToEntities(product, productDTO);
        product = productRepository.save(product);

        return new ProductDTO(product, product.getCategories());

    }

    public void assigningDtoToEntities(Product entity, ProductDTO dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setImg(dto.getImg());
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());

        entity.getCategories().clear();
        for (CategoryDTO catDTO : dto.getCategories()) {
            Category category = categoryRepository.getReferenceById(catDTO.getId());
            entity.getCategories().add(category);
        }
    }
}
