package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.entities.Order;
import com.example.matheusvsdev.ecommerce_backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String body) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            emailSender.send(message);
        }
        catch (MailException e){
            throw new MailException("Failed to send email") {
            };
        }
    }

    public void paymentConfirmation(Order order) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataEntrega = order.getMoment().plusDays(15).format(formatter);
        String dataFormatada = order.getMoment().format(formatter);

        String text = "Olá," +
                "\n\nAgradecemos por comprar na Ecommerce.com.br. Seu pagamento foi confirmado e seu pedido está sendo processado. Nós te avisaremos quando os itens forem enviados." +
                "\n\nSua entrega está prevista para: " + dataEntrega +
                "\n\nSeu pedido será enviado para: " + order.getClient().getFirstName() + "\n" + order.getAddress().toString() +
                "\n\nResumo do pedido" +
                "\n\nPedido de número " + order.getId() +
                "\nRealizado em " + dataFormatada +
                "\n\nTotal do pedido: R$" + order.getTotal() +
                "\nForma de pagamento: " + order.getPayment().getPaymentMethod();

        sendEmail(order.getClient().getEmail(), "Pagamento confirmado para o pedido Ecommerce.com.br\n#" + order.getId(), text);
    }

    public void userCreationEmailBody(User user) {

        String text = "Dados da conta\n\n" +
                "Nome: " + user.getFirstName() +
                "\n\nSobrenome: " + user.getLastName() +
                "\n\nCPF: " + user.getCpf() +
                "\n\nContato: " + user.getPhone() +
                "\n\nData de nascimento: " + user.getBirthDate() +
                "\n\nUsername: " + user.getUsername();

        sendEmail(user.getEmail(), "Parabéns, sua conta foi criada com sucesso!", text);
    }

    public void orderConfirmation(Order order) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataEntrega = order.getMoment().plusDays(15).format(formatter);

        String text = "Pedido do comerciante Ecommerce.com" +
                "\n\nComprado em: " + "Ecommerce.com" +
                "\n\nEntrega prevista: " + dataEntrega +
                "\n\nItens: " + order.getItems().toString() +
                "\n\nValor: R$" + order.getOrderedAmount() +
                "\n\nFrete: " + order.getFreightCost() +
                "\n\nValor total do pedido: R$" + order.getTotal() +
                "\n\nForma de pagamento: " + order.getPayment().getPaymentMethod();

        sendEmail(order.getClient().getEmail(), "Seu pedido Ecommerce.com.br #" + order.getId() + " " + " de " + order.getItems().size() + " item(s)", text);
    }
}
