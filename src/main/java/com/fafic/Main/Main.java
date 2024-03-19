package com.fafic.Main;


import com.fafic.Model.ViaCep;
import com.fafic.Exception.BadRequestException;
import com.fafic.Exception.CEPInvalidoException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class Main {
    private static final String WEBSERVICE = "https://viacep.com.br/ws/";
    private static final String XML = "/xml";

    public static void main(String[] args) {
        String cepInput = JOptionPane.showInputDialog("Informe Seu CEP: ").replaceAll("\\D+", "");

        if (isValidCEP(cepInput)) {
            InputStream xmlInputStream = requestCep(cepInput);
            ViaCep viaCep = unmarshalXML(xmlInputStream);

            String mensagem = viaCep.cep == null ? "CEP não encontrado" : viaCep.toString();
            JOptionPane.showMessageDialog(null, mensagem);
        }
    }

    public static boolean isValidCEP(String cep) throws CEPInvalidoException {
        if (Objects.isNull(cep) || cep.isBlank()) return false;
        if (cep.length() != 8) throw new CEPInvalidoException("CEP deve conter 8 dígitos");
        return true;
    }

    public static InputStream requestCep(String cep) throws RuntimeException {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(new URI(WEBSERVICE + cep + XML)).build();

            System.out.println("Enviando httpRequest para " + httpRequest.uri());
            HttpResponse<InputStream> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
            if (httpResponse.statusCode() == 400) {
                throw new BadRequestException("400 (Bad Request): Consultado um CEP de formato inválido");
            }

            return httpResponse.body();
        } catch (URISyntaxException | IOException | InterruptedException | BadRequestException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static ViaCep unmarshalXML(InputStream xmlcep) throws RuntimeException {
        try {
            Unmarshaller unmarshaller = JAXBContext.newInstance(ViaCep.class).createUnmarshaller();
            return (ViaCep) unmarshaller.unmarshal(xmlcep);
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}