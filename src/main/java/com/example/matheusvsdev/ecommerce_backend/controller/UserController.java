package com.example.matheusvsdev.ecommerce_backend.controller;

import com.example.matheusvsdev.ecommerce_backend.docs.UserControllerDocs;
import com.example.matheusvsdev.ecommerce_backend.dto.UpdateUserDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.UserDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.UserResponseDTO;
import com.example.matheusvsdev.ecommerce_backend.service.UpdateOwnUserService;
import com.example.matheusvsdev.ecommerce_backend.service.UserService;
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

@RestController
@RequestMapping(value = "/users")
@Tag(name = "User Controller", description = "Operações relacionadas aos usuários")
@SecurityRequirement(name = "bearerAuth")
public class UserController implements UserControllerDocs {

    @Autowired
    private UserService userService;

    @Autowired
    private UpdateOwnUserService updateOwnUserService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserResponseDTO user = userService.createUser(userDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @PreAuthorize(("hasRole('ROLE_ADMIN')"))
    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        Page<UserDTO> list = userService.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO dto = userService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
        UserDTO newDto = userService.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping(value = "/me")
    public ResponseEntity<UserDTO> updateSelf(@Valid @RequestBody UpdateUserDTO dto) {
        UserDTO newDto = updateOwnUserService.updateSelf(dto);
        return ResponseEntity.ok().body(newDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @DeleteMapping(value = "/me/delete")
    public ResponseEntity<Void> deleteSelf() {
        updateOwnUserService.inactiveAccount();
        return ResponseEntity.noContent().build();
    }
}
