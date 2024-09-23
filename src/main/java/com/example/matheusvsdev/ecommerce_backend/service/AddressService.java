package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.AddressDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.CartDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.OrderDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.Address;
import com.example.matheusvsdev.ecommerce_backend.entities.Order;
import com.example.matheusvsdev.ecommerce_backend.entities.State;
import com.example.matheusvsdev.ecommerce_backend.entities.User;
import com.example.matheusvsdev.ecommerce_backend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;

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
    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
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

}
