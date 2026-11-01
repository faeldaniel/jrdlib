package com.jrdlib;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;

/**
 * Consulta de CEP integrada ao OpenCEP (https://opencep.com/).
 *
 * @author Rafael Daniel
 * @since 05-05-2009
 */
public class BuscaCep {

    private String baseUrl = "https://opencep.com/v1/";
    private String CEP = "";
    private String bairro = "";
    private String cidade = "";
    private String estado = "";
    private String logradouro = "";
    private String complemento = "";
    private String ibge = "";
    private int resultado = 0;
    private String resultado_txt = "";
    private String tipo_logradouro = "";
    private BalloonToolTip objBalloonToolTip = new BalloonToolTip();

    private static final Set<String> TIPOS_LOGRADOURO = new HashSet<String>(Arrays.asList(
        "RUA", "AVENIDA", "AV", "AV.", "ALAMEDA", "TRAVESSA", "PRACA", "PRAÇA",
        "RODOVIA", "ESTRADA", "VIELA", "LARGO", "BECO", "PASSARELA", "QUADRA",
        "SETOR", "SERVIDAO", "SERVIDÃO", "PARQUE", "CHACARA", "CHÁCARA", "FAZENDA",
        "LOTEAMENTO", "CONDOMINIO", "CONDOMÍNIO", "RESIDENCIAL", "DISTRITO", "TRECHO"
    ));

    public BuscaCep() {
    }

    public BuscaCep(String baseUrl) {
        if (baseUrl != null && !baseUrl.trim().isEmpty()) {
            this.baseUrl = baseUrl;
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String cep) {
        CEP = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public String getResultado_txt() {
        return resultado_txt;
    }

    public void setResultado_txt(String resultado_txt) {
        this.resultado_txt = resultado_txt;
    }

    public String getTipo_logradouro() {
        return tipo_logradouro;
    }

    public void setTipo_logradouro(String tipo_logradouro) {
        this.tipo_logradouro = tipo_logradouro;
    }

    public String getLogradouroCompleto() {
        if (tipo_logradouro != null && !tipo_logradouro.trim().isEmpty()) {
            return tipo_logradouro + " " + (logradouro != null ? logradouro : "");
        }
        return logradouro != null ? logradouro : "";
    }

    public Document getDocumento(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    public void LimparCampos() {
        this.CEP = "";
        this.bairro = "";
        this.cidade = "";
        this.estado = "";
        this.logradouro = "";
        this.complemento = "";
        this.ibge = "";
        this.resultado = 0;
        this.resultado_txt = "";
        this.tipo_logradouro = "";
    }

    private void parsearLogradouro(String logradouroCompleto) {
        if (logradouroCompleto == null || logradouroCompleto.trim().isEmpty()) {
            this.tipo_logradouro = "";
            this.logradouro = "";
            return;
        }

        String trimmed = logradouroCompleto.trim();
        int spaceIndex = trimmed.indexOf(' ');
        if (spaceIndex > 0) {
            String primeiraPalavra = trimmed.substring(0, spaceIndex).trim();
            if (TIPOS_LOGRADOURO.contains(primeiraPalavra.toUpperCase())) {
                this.tipo_logradouro = primeiraPalavra;
                this.logradouro = trimmed.substring(spaceIndex + 1).trim();
                return;
            }
        }
        this.tipo_logradouro = "";
        this.logradouro = trimmed;
    }

    /**
     * Consulta o CEP no serviço OpenCEP (https://opencep.com/).
     * 
     * @param cep Código de Endereçamento Postal (com ou sem máscara)
     */
    public void ConsultaCep(String cep) {
        LimparCampos();
        if (cep == null || cep.trim().isEmpty()) {
            return;
        }

        String cepNumerico = cep.replaceAll("[^0-9]", "");
        if (cepNumerico.length() != 8) {
            this.resultado = 0;
            this.resultado_txt = "CEP inválido - deve conter 8 dígitos";
            return;
        }

        this.CEP = cepNumerico;

        HttpURLConnection conn = null;
        try {
            String urlStr = this.baseUrl + (this.baseUrl.endsWith("/") ? "" : "/") + cepNumerico;
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) jrdlib/1.0");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(7000);
            conn.setReadTimeout(7000);

            int status = conn.getResponseCode();
            if (status == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder jsonText = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonText.append(line);
                }
                reader.close();

                JSONObject json = new JSONObject(jsonText.toString());
                this.CEP = json.optString("cep", cepNumerico);
                parsearLogradouro(json.optString("logradouro", ""));
                this.complemento = json.optString("complemento", "");
                this.bairro = json.optString("bairro", "");
                this.cidade = json.optString("localidade", "");
                this.estado = json.optString("uf", "");
                this.ibge = json.optString("ibge", "");
                this.resultado = 1;
                this.resultado_txt = "sucesso - cep completo";
            } else if (status == 404) {
                this.resultado = 0;
                this.resultado_txt = "cep não encontrado";
            } else {
                this.resultado = 0;
                this.resultado_txt = "erro HTTP: " + status;
            }
        } catch (Exception ex) {
            this.resultado = 0;
            this.resultado_txt = "Servidor encontra-se fora do ar ou sem conexão";
            JOptionPane.showMessageDialog(null, "Servidor de CEP encontra-se fora do ar ou sem conexão, tente mais tarde.\nErro: " + ex.getMessage(),
                    "Aviso do Sistema", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * Busca o CEP e preenche automaticamente os campos de endereço Swing.
     * 
     * @param jFTF_Cep
     * @param jTF_Endereco
     * @param jTF_Bairro
     * @param jTF_Cidade
     * @param jTF_UF
     */
    public void efetuandoPesquisaCep(final JFormattedTextField jFTF_Cep, final JTextField jTF_Endereco, final JTextField jTF_Bairro, final JTextField jTF_Cidade, final JTextField jTF_UF) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                objBalloonToolTip.showErrorMessage(jFTF_Cep, "Efetuando pesquisa aguarde...");
                ConsultaCep(jFTF_Cep.getText());
                if (getResultado() == 1) {
                    jTF_Endereco.setText(getLogradouroCompleto());
                    jTF_Bairro.setText(getBairro());
                    jTF_Cidade.setText(getCidade());
                    jTF_UF.setText(getEstado());
                    objBalloonToolTip.ClosedErrorMessage();
                } else {
                    objBalloonToolTip.ClosedErrorMessage();
                    objBalloonToolTip.setToolTipAvisos(jFTF_Cep, "Cep não localizado !!!", 3000);
                    jTF_Endereco.setText("");
                    jTF_Bairro.setText("");
                    jTF_Cidade.setText("");
                    jTF_UF.setText("");
                    jFTF_Cep.setSelectionStart(0);
                }
            }
        }).start();
    }
}
