package com.fafic.Main;


import com.fafic.Model.ViaCep;
import com.fafic.Exception.CEPInvalidoException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class Main {
    private static final String WEBSERVICE = "https://viacep.com.br/ws/";
    private static final String XML = "/xml";

    public static void main(String[] args) {
        String cepInput = JOptionPane.showInputDialog("Informe Seu CEP: ").replaceAll("\\D+", "");

        if (isValidCEP(cepInput)) {
            ViaCep viaCep = unmarshalXML(WEBSERVICE + cepInput + XML);
            String mensagem = viaCep.cep == null ? "CEP não encontrado" : viaCep.toString();
            JOptionPane.showMessageDialog(null, mensagem);
        }
    }

    public static boolean isValidCEP(String cep) throws CEPInvalidoException {
        if (Objects.isNull(cep) || cep.isBlank()) return false;
        if (cep.length() != 8) throw new CEPInvalidoException("CEP deve conter 8 dígitos");
        return true;
    }

    public static ViaCep unmarshalXML(String host) throws RuntimeException {
        try {
            URI uri = new URI(host);
            Unmarshaller unmarshaller = JAXBContext.newInstance(ViaCep.class).createUnmarshaller();
            return (ViaCep) unmarshaller.unmarshal(uri.toURL());
        } catch (JAXBException | MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}