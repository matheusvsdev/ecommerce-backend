package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.CategoryDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.Category;
import com.example.matheusvsdev.ecommerce_backend.repository.CategoryRepository;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ArgumentAlreadyExistsException;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.DatabaseException;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();

		return list.stream().map(x -> new CategoryDTO(x)).toList();
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		if (repository.existsByName(dto.getName())) {
			throw new ArgumentAlreadyExistsException("Já existe categoria cadastrada com esse nome");
		}
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);

		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getReferenceById(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);

			return new CategoryDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Categoria não encontrada");
		}
	}

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
    	if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Categoria não encontrada");
		}
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
    }
}
