package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.OrderDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.OrderItemDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class InvoiceService {

    public static int generateRandomNumber(int digits) {
        Random random = new Random();
        int min = (int) Math.pow(10, digits - 1);
        int max = (int) Math.pow(10, digits) - 1;
        return random.nextInt((max - min) + 1) + min;
    }

    public void generateInvoice(OrderDTO orderDTO) throws IOException, DocumentException {
        Document document = new Document();

        // Gera o caminho do arquivo DANFE
        String filePath = "DANFE-" + orderDTO.getId() + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Adiciona título
        Paragraph title = new Paragraph("DANFE - Documento Auxiliar da Nota Fiscal Eletrônica (Simulado)",
                new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Adiciona chave de acesso fictícia
        document.add(new Paragraph("Chave de Acesso: " + orderDTO.getId() + generateRandomNumber(5)));
        document.add(new Paragraph("Data de Emissão: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        document.add(new Paragraph("Emitente: Ecommerce-Backend"));
        document.add(new Paragraph("CNPJ: 12.345.678/0001-99"));
        document.add(new Paragraph("Destinatário: " + orderDTO.getClient().getName()));
        document.add(new Paragraph("CPF/CNPJ: 123.456.789-00"));
        document.add(new Paragraph(" "));

        // Tabela de Itens
        PdfPTable table = new PdfPTable(4); // 4 colunas
        table.setWidths(new float[]{2, 5, 2, 2});

        table.addCell("Quantidade");
        table.addCell("Descrição");
        table.addCell("Preço Unitário");
        table.addCell("Total");

        for (OrderItemDTO item : orderDTO.getItems()) {
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(item.getName());
            table.addCell(String.format("R$%.2f", item.getPrice()));
            table.addCell(String.format("R$%.2f", item.getQuantity() * item.getPrice()));
        }

        document.add(table);

        // Adiciona valor total
        Paragraph total = new Paragraph("Valor Total: " + String.format("R$%.2f", orderDTO.getTotal()));
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);

        document.close();
    }
}
