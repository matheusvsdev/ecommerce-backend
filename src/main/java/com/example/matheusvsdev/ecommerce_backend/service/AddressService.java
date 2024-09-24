package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.*;
import com.example.matheusvsdev.ecommerce_backend.entities.*;
import com.example.matheusvsdev.ecommerce_backend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        User user = authService.autenthicated();

        Address address = new Address();
        address.setCep(addressDTO.getCep());
        address.setState(addressDTO.getState());
        address.setCity(addressDTO.getCity());
        address.setNeighborhood(addressDTO.getNeighborhood());
        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setComplement(addressDTO.getComplement());
        address.setClient(user);

        addressRepository.save(address);

        return new AddressDTO(address);
    }

    @Transactional(readOnly = true)
    public AddressDTO findById(Long id) {
        Address address = addressRepository.findById(id).get();

        authService.validateSelfOrAdmin(address.getClient().getId());
        return new AddressDTO(address);
    }

    @Transactional(readOnly = true)
    public List<AddressDTO> findAll() {
        User user = authService.autenthicated();

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

        Address address = addressRepository.getReferenceById(id);
        assigningDtoToEntities(address, dto);
        address = addressRepository.save(address);

        authService.validateSelfOrAdmin(address.getClient().getId());

        return new AddressDTO(address);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        User user = authService.autenthicated();

        addressRepository.existsById(id);
        addressRepository.deleteById(id);
    }

    public void assigningDtoToEntities(Address address, AddressDTO dto) {
        address.setCity(dto.getCity());
        address.setNeighborhood(dto.getNeighborhood());
        address.setStreet(dto.getStreet());
        address.setNumber(dto.getNumber());
        address.setComplement(dto.getComplement());
        address.setCep(dto.getCep());
    }
}
