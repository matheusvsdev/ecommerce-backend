package com.example.matheusvsdev.ecommerce_backend.resource;

import com.example.matheusvsdev.ecommerce_backend.dto.AddressDTO;
import com.example.matheusvsdev.ecommerce_backend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDTO> insert(@RequestBody AddressDTO addressDTO) {
        addressDTO = addressService.insertAddress(addressDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addressDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(addressDTO);
    }

    @GetMapping("/users/{clientId}")
    public ResponseEntity<List<AddressDTO>> getAddressesByClientId(@PathVariable Long clientId) {
        List<AddressDTO> addresses = addressService.findByClientId(clientId)
                .stream()
                .map(AddressDTO::new) // Convertendo para DTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(addresses);
    }
}
