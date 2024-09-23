package com.example.matheusvsdev.ecommerce_backend.resource;

import com.example.matheusvsdev.ecommerce_backend.dto.AddressDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.CartDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.OrderDTO;
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

    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAll() {
        List<AddressDTO> cartList = addressService.findAll();
        return ResponseEntity.ok(cartList);
    }
}
