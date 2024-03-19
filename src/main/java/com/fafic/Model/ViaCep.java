package com.fafic.Model;


import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "xmlcep")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"cep", "logradouro", "complemento", "bairro", "localidade", "uf", "ibge", "gia", "ddd", "siafi"})
public class ViaCep {
    public String  cep;
    public String  logradouro;
    public String  complemento;
    public String  bairro;
    public String  localidade;
    public String  uf;
    public String  ibge;
    public String  gia;
    public String  ddd;
    public String  siafi;

    public String toString() {
        return "ViaCep\n" +
                "CEP: " + cep + '\n' +
                "LOGRADOURO: " + logradouro + '\n' +
                "COMPLEMENTO: " + complemento + '\n' +
                "BAIRRO: " + bairro + '\n' +
                "LOCALIDADE: " + localidade + '\n' +
                "UF: " + uf + '\n' +
                "IBGE: " + ibge + '\n' +
                "GIA: " + gia + '\n' +
                "DDD: " + ddd + '\n' +
                "SIAFI: " + siafi + '\n';
    }
}
