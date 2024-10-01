package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.EmailDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.NewPasswordDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.ReactiveAccountDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.AccountRecover;
import com.example.matheusvsdev.ecommerce_backend.entities.PasswordRecover;
import com.example.matheusvsdev.ecommerce_backend.entities.User;
import com.example.matheusvsdev.ecommerce_backend.repository.AccountRecoverRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.PasswordRecoverRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.UserRepository;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AccountRecoveryService {

    @Value("${email.account-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.account-recover.uri}")
    private String recoverUri;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRecoverRepository accountRecoverRepository;

    @Autowired
    private EmailService emailService;

    public void createRecoverToken(EmailDTO body) {
        User user = userRepository.findByEmail(body.getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("Email não encontrado");
        }

        String token = UUID.randomUUID().toString();

        AccountRecover accountRecover = new AccountRecover();
        accountRecover.setEmail(body.getEmail());
        accountRecover.setToken(token);
        accountRecover.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));
        accountRecover = accountRecoverRepository.save(accountRecover);

        String text = "Acesse o link para reativar sua conta\n\n"
                + recoverUri + token + ". Validade de " + tokenMinutes + " minutos";

        emailService.sendEmail(body.getEmail(), "Reativar conta", text);
    }

    @Transactional
    public void reactiveAccount(ReactiveAccountDTO reactiveAccountDTO) {
        List<AccountRecover> result = accountRecoverRepository
                .searchValidToken(reactiveAccountDTO.getToken(), Instant.now());

        User user = userRepository.findByEmail(result.get(0).getEmail());
        user.setActive(true);

        user = userRepository.save(user);
    }
}
