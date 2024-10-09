package com.example.matheusvsdev.ecommerce_backend.controller;

import com.example.matheusvsdev.ecommerce_backend.docs.ProductControllerDocs;
import com.example.matheusvsdev.ecommerce_backend.dto.ProductDTO;
import com.example.matheusvsdev.ecommerce_backend.projection.ProductProjection;
import com.example.matheusvsdev.ecommerce_backend.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
@Tag(name = "Product Controller", description = "Operações relacionadas aos produtos")
public class ProductController implements ProductControllerDocs {

    @Autowired
    private ProductService productService;

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO productDTO) {
        productDTO = productService.insertProduct(productDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(productDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
        Page<ProductDTO> page = productService.findAllPaged(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO dto = productService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/search")
    public Page<ProductProjection> searchProducts(
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false) String name,
            Pageable pageable) {
        return productService.searchProducts(categoryIds, name, pageable);
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        dto = productService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
