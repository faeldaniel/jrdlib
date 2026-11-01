package com.jrdlib;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * Utilitário para checagem remota de versão e atualizações via XML.
 * 
 * @author Daniel
 */
public class ChecagensRemota {

    private String baseUrl;

    public ChecagensRemota() {
    }

    public ChecagensRemota(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Document getDocumento(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    private String montarUrl(String urlBase, String sistema) {
        if (urlBase == null || urlBase.trim().isEmpty()) {
            throw new IllegalStateException("URL base para checagem remota não configurada. Defina a URL via construtor, setter ou passe por parâmetro.");
        }
        String separador = urlBase.contains("?") ? "&" : "?";
        return urlBase + separador + "Sistema=" + (sistema != null ? sistema : "");
    }

    /**
     * Checa a última versão do sistema utilizando a URL base configurada na instância.
     * @param sistema Identificador do sistema
     * @return versão remota ou 0.0 em caso de erro
     */
    public Double VersaoRemoto(String sistema) {
        return VersaoRemoto(this.baseUrl, sistema);
    }

    /**
     * Checa a última versão do sistema informando a URL base do serviço.
     * @param urlBase URL do serviço (ex: "http://meuservidor.com.br/infoLastVersion.php")
     * @param sistema Identificador do sistema
     * @return versão remota ou 0.0 em caso de erro
     */
    public Double VersaoRemoto(String urlBase, String sistema) {
        Double versaoRemota = 0.;
        try {
            URL url = new URL(montarUrl(urlBase, sistema));
            Document document = getDocumento(url);
            Element root = document.getRootElement();
            for (Iterator i = root.elementIterator(); i.hasNext();) {
                Element element = (Element) i.next();
                if (element.getQualifiedName().equals("versao")) {
                    versaoRemota = Double.parseDouble(element.getText().trim());
                }
            }
        } catch (Exception ex) {
        }
        return versaoRemota;
    }

    /**
     * Link para download do atualizador utilizando a URL base configurada na instância.
     * @param sistema Identificador do sistema
     * @return código/versão do link de update
     */
    public Double DownloadUpdate(String sistema) {
        return DownloadUpdate(this.baseUrl, sistema);
    }

    /**
     * Link para download do atualizador informando a URL base do serviço.
     * @param urlBase URL do serviço (ex: "http://meuservidor.com.br/infoLastVersion.php")
     * @param sistema Identificador do sistema
     * @return código/versão do link de update
     */
    public Double DownloadUpdate(String urlBase, String sistema) {
        Double versaoRemota = 0.;
        try {
            URL url = new URL(montarUrl(urlBase, sistema));
            Document document = getDocumento(url);
            Element root = document.getRootElement();
            for (Iterator i = root.elementIterator(); i.hasNext();) {
                Element element = (Element) i.next();
                if (element.getQualifiedName().equals("link_update")) {
                    versaoRemota = Double.parseDouble(element.getText().trim());
                }
            }
        } catch (Exception ex) {
        }
        return versaoRemota;
    }
}
