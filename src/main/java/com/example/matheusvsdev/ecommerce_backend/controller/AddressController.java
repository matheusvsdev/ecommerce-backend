package com.example.matheusvsdev.ecommerce_backend.controller;

import com.example.matheusvsdev.ecommerce_backend.dto.AddressDTO;
import com.example.matheusvsdev.ecommerce_backend.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<AddressDTO> insert(@Valid @RequestBody AddressDTO addressDTO) {
        addressDTO = addressService.insertAddress(addressDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addressDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(addressDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAll() {
        List<AddressDTO> cartList = addressService.findAll();
        return ResponseEntity.ok(cartList);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable Long id) {
        AddressDTO address = addressService.findById(id);
        return ResponseEntity.ok().body(address);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable Long id, @Valid @RequestBody AddressDTO dto) {
        dto = addressService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
