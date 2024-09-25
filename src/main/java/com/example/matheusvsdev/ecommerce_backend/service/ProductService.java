package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.CategoryDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.ProductDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.Category;
import com.example.matheusvsdev.ecommerce_backend.entities.Product;
import com.example.matheusvsdev.ecommerce_backend.repository.CategoryRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.ProductRepository;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ArgumentAlreadyExistsException;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.DatabaseException;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public ProductDTO insertProduct(ProductDTO productDTO) {
        if (productRepository.existsByName(productDTO.getName())) {
            throw new ArgumentAlreadyExistsException("Já existe produto cadastrado com esse nome");
        }
        Product product = new Product();
        assigningDtoToEntities(product, productDTO);
        product = productRepository.save(product);

        return new ProductDTO(product, product.getCategories());
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Pageable pageable) {
        Page<Product> list = productRepository.findAll(pageable);

        return list.map(x -> new ProductDTO(x));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = productRepository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = productRepository.getReferenceById(id);
            assigningDtoToEntities(entity, dto);
            entity = productRepository.save(entity);

            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
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
