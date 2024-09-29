package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.*;
import com.example.matheusvsdev.ecommerce_backend.entities.*;
import com.example.matheusvsdev.ecommerce_backend.repository.AddressRepository;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.DatabaseException;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AuthService authService;

    @Transactional
    public AddressDTO insertAddress(AddressDTO addressDTO) {
        User user = authService.authenticated();

        Address address = new Address();
        assigningDtoToEntities(address, addressDTO);
        address.setClient(user);

        addressRepository.save(address);

        return new AddressDTO(address);
    }

    @Transactional(readOnly = true)
    public AddressDTO findById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));

        return new AddressDTO(address);
    }

    @Transactional(readOnly = true)
    public List<AddressDTO> findAll() {
        User user = authService.authenticated();

        List<Address> addresses;

        if (user.hasRole("ROLE_ADMIN")) {
            addresses = addressRepository.findAll();
        } else {
            addresses = addressRepository.findByClientId(user.getId());
        }
        return addresses.stream().map(x -> new AddressDTO(x)).collect(Collectors.toList());
    }

    @Transactional
    public AddressDTO update(Long id, AddressDTO dto) {
        try {
            Address address = addressRepository.getReferenceById(id);
            assigningDtoToEntities(address, dto);
            address = addressRepository.save(address);

            authService.validateSelfOrAdmin(address.getClient().getId());

            return new AddressDTO(address);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Endereço não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        User user = authService.authenticated();
        if(!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Endereço não encontrado");
        }
        try {
            addressRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
        authService.validateSelfOrAdmin(user.getId());
    }

    public void assigningDtoToEntities(Address address, AddressDTO dto) {
        address.setState(dto.getState());
        address.setCep(dto.getCep());
        address.setCity(dto.getCity());
        address.setNeighborhood(dto.getNeighborhood());
        address.setStreet(dto.getStreet());
        address.setNumber(dto.getNumber());
        address.setComplement(dto.getComplement());
    }
}
