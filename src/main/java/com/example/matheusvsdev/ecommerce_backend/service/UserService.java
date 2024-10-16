package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.*;
import com.example.matheusvsdev.ecommerce_backend.entities.Role;
import com.example.matheusvsdev.ecommerce_backend.entities.User;
import com.example.matheusvsdev.ecommerce_backend.projection.UserDetailsProjection;
import com.example.matheusvsdev.ecommerce_backend.repository.RoleRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.UserRepository;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ArgumentAlreadyExistsException;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.DatabaseException;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public UserDTO createUser(InsertUserDTO userDTO) {
        User user = new User();
        assigningDtoToEntities(user, userDTO);

        emailAndCpfValidation(userDTO);

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        addUserRole(user, userDTO);

        user = userRepository.save(user);

        return new UserDTO(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> list = userRepository.findAll(pageable);
        return list.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {

        try {
            emailAndCpfValidation(dto);
            User entity = userRepository.getReferenceById(id);
            assigningDtoToEntities(entity, dto);
            entity = userRepository.save(entity);

            return new UserDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);
        if (result.size() == 0) {
            throw new UsernameNotFoundException("Email not found");
        }
        User user = new User();
        user.setEmail(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());

        Boolean isActive = result.get(0).getActive();
        if (!isActive) {
            throw new UsernameNotFoundException("Conta inativa. Para reativar, acessar o campo de Suporte.");
        }
        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }
        return user;
    }

    public void emailAndCpfValidation(UserDTO userDTO) {
        if (userRepository.existsByCpf(userDTO.getCpf())) {
            throw new ArgumentAlreadyExistsException("CPF já cadastrado");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ArgumentAlreadyExistsException("Email já cadastrado");
        }
    }

    public void addUserRole(User user, UserDTO userDTO) {

        user.getRoles().clear();
        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByAuthority("ROLE_CLIENT");
            user.getRoles().add(defaultRole);

            emailService.userCreationEmailBody(user);
        } else {
            for (RoleDTO roleDTO : userDTO.getRoles()) {
                Role role = roleRepository.findByAuthority(roleDTO.getAuthority());
                if (role != null) {
                    user.getRoles().add(role);
                }
            }
        }
    }

    public void assigningDtoToEntities(User entity, UserDTO dto) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setCpf(dto.getCpf());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
    }
}
