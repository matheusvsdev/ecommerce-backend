package com.example.matheusvsdev.ecommerce_backend.controller;

import com.example.matheusvsdev.ecommerce_backend.dto.CategoryDTO;
import com.example.matheusvsdev.ecommerce_backend.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	@Autowired
	private CategoryService service;

	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<CategoryDTO> list = service.findAll();

		return ResponseEntity.ok().body(list);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		CategoryDTO dto = service.findById(id);

		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
	@PostMapping
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<CategoryDTO> insert(@Valid  @RequestBody CategoryDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/{id}")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<CategoryDTO> update(@Valid @PathVariable Long id, @RequestBody CategoryDTO dto) {
		dto = service.update(id, dto);

		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);

		return ResponseEntity.noContent().build();
	}
} 
