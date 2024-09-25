package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.entities.Order;
import com.example.matheusvsdev.ecommerce_backend.entities.User;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.EmailSendException;
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
        catch (Exception e){
            throw new EmailSendException("Erro ao enviar o email");
        }
    }

    public void paymentConfirmation(Order order) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMMM/yyyy");
        String dataEntrega = order.getMoment().plusDays(15).format(formatter);
        String dataFormatada = order.getMoment().format(formatter);

        String[] part1 = dataFormatada.split("/");
        part1[1] = part1[1].substring(0, 1).toUpperCase() + part1[1].substring(1);
        dataFormatada = String.join("/", part1);

        String[] part2 = dataEntrega.split("/");
        part2[1] = part2[1].substring(0, 1).toUpperCase() + part2[1].substring(1);
        dataEntrega = String.join("/", part2);

        String text = "Olá," +
                "\n\nAgradecemos por comprar na Ecommerce.com.br. Seu pagamento foi confirmado e seu pedido está sendo processado. Nós te avisaremos quando os itens forem enviados." +
                "\n\nSua entrega está prevista para: " + dataEntrega +
                "\n\nSeu pedido será enviado para: " + order.getUser().getFirstName() + "\n" + order.getAddress().toString() +
                "\n\nResumo do pedido" +
                "\n\nPedido de número " + order.getId() +
                "\nRealizado em " + dataFormatada +
                "\n\nTotal do pedido: R$" + order.getTotal() +
                "\nForma de pagamento: " + order.getPayment().getPaymentMethod();

        sendEmail(order.getUser().getEmail(), "Pagamento confirmado para o pedido Ecommerce.com.br\n#" + order.getId(), text);
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMMM/yyyy");
        String dataEntrega = order.getMoment().plusDays(15).format(formatter);
        String dataFormatada = order.getMoment().format(formatter);

        String[] part1 = dataFormatada.split("/");
        part1[1] = part1[1].substring(0, 1).toUpperCase() + part1[1].substring(1);
        dataFormatada = String.join("/", part1);

        String[] part2 = dataEntrega.split("/");
        part2[1] = part2[1].substring(0, 1).toUpperCase() + part2[1].substring(1);
        dataEntrega = String.join("/", part2);

        String text = "Pedido do comerciante Ecommerce.com" +
                "\n\nPedido nº " + order.getId() +
                "\n\nComprado em: " + "Ecommerce.com" +
                "\n\nEntrega prevista: " + dataEntrega +
                "\n\nItens: " + order.getItems().toString() +
                "\n\nSeu pedido será enviado para:\n" + order.getUser().getFirstName() + "\n"
                                                        + order.getAddress().getCity() + ",\n"
                                                        + order.getAddress().getState() + "\n"
                                                        + "Brasil" +
                "\n\nResumo do pedido " + order.getId() +
                "\n\nRealizado em " + dataFormatada +
                "\n\nFrete: " + order.getFreightCost() +
                "\n\nValor: R$" + order.getTotal() +
                "\n\nPagamento feito com: " + order.getPayment().getPaymentMethod();

        sendEmail(order.getUser().getEmail(), "Seu pedido Ecommerce.com.br #" + order.getId() + " " + " de " + order.getItems().size() + " item(s)", text);
    }
}
