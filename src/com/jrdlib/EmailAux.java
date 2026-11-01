/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrdlib;

import java.util.Date;
import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * Utilitário para envio de e-mails via SMTP.
 * 
 * @author Rafael Daniel
 */
public class EmailAux {

    private String smtpHost;
    private int smtpPort = 587;
    private String usuario;
    private String senha;
    private boolean auth = true;
    private boolean starttls = false;

    public EmailAux() {
    }

    public EmailAux(String smtpHost, int smtpPort, String usuario, String senha) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.usuario = usuario;
        this.senha = senha;
        this.auth = (usuario != null && !usuario.isEmpty());
    }

    public EmailAux(String smtpHost, int smtpPort, String usuario, String senha, boolean starttls) {
        this(smtpHost, smtpPort, usuario, senha);
        this.starttls = starttls;
    }

    /**
     * Enviar e-mail para um único destinatário utilizando as configurações SMTP da instância.
     * @param De
     * @param Para
     * @param Assunto
     * @param Menssagem
     * @throws Exception 
     */
    public void enviandoEmail(String De, String Para, String Assunto, String Menssagem) throws Exception {
        if (smtpHost == null || smtpHost.trim().isEmpty()) {
            throw new IllegalStateException("Servidor SMTP não configurado. Defina as configurações SMTP via construtor, setters ou passe por parâmetro.");
        }
        enviandoEmail(this.smtpHost, this.smtpPort, this.usuario, this.senha, this.starttls, De, Para, Assunto, Menssagem);
    }

    /**
     * Enviar e-mail para um único destinatário informando os parâmetros do servidor SMTP.
     * @param smtpHost Servidor SMTP
     * @param smtpPort Porta SMTP
     * @param usuario Usuário da conta
     * @param senha Senha da conta
     * @param De Remetente
     * @param Para Destinatário
     * @param Assunto Assunto
     * @param Menssagem Mensagem HTML
     * @throws Exception
     */
    public void enviandoEmail(String smtpHost, int smtpPort, final String usuario, final String senha,
                              String De, String Para, String Assunto, String Menssagem) throws Exception {
        enviandoEmail(smtpHost, smtpPort, usuario, senha, false, De, Para, Assunto, Menssagem);
    }

    /**
     * Enviar e-mail para um único destinatário informando todos os parâmetros SMTP incluindo STARTTLS.
     * @param smtpHost Servidor SMTP
     * @param smtpPort Porta SMTP
     * @param usuario Usuário da conta
     * @param senha Senha da conta
     * @param starttls Se deve usar STARTTLS
     * @param De Remetente
     * @param Para Destinatário
     * @param Assunto Assunto
     * @param Menssagem Mensagem HTML
     * @throws Exception
     */
    public void enviandoEmail(String smtpHost, int smtpPort, final String usuario, final String senha, boolean starttls,
                              String De, String Para, String Assunto, String Menssagem) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", String.valueOf(smtpPort));
        boolean usarAuth = (usuario != null && !usuario.trim().isEmpty());
        props.put("mail.smtp.auth", usarAuth ? "true" : "false");
        if (starttls) {
            props.put("mail.smtp.starttls.enable", "true");
        }

        Session session;
        if (usarAuth) {
            session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(usuario, senha);
                }
            });
        } else {
            session = Session.getInstance(props);
        }

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(De));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(Para));
        msg.setSentDate(new Date());
        msg.setSubject(Assunto);
        msg.setText(Menssagem, "utf-8", "html");
        Transport.send(msg);
    }

    /**
     * Envia e-mail para vários destinatários utilizando as configurações SMTP da instância.
     * @param De
     * @param addressTo
     * @param Assunto
     * @param Menssagem
     * @throws Exception 
     */
    public void enviandoEmails(String De, InternetAddress[] addressTo, String Assunto, String Menssagem) throws Exception {
        if (smtpHost == null || smtpHost.trim().isEmpty()) {
            throw new IllegalStateException("Servidor SMTP não configurado. Defina as configurações SMTP via construtor, setters ou passe por parâmetro.");
        }
        enviandoEmails(this.smtpHost, this.smtpPort, this.usuario, this.senha, this.starttls, De, addressTo, Assunto, Menssagem);
    }

    /**
     * Envia e-mail para vários destinatários informando os parâmetros do servidor SMTP.
     * @param smtpHost Servidor SMTP
     * @param smtpPort Porta SMTP
     * @param usuario Usuário da conta
     * @param senha Senha da conta
     * @param De Remetente
     * @param addressTo Destinatários
     * @param Assunto Assunto
     * @param Menssagem Mensagem HTML
     * @throws Exception
     */
    public void enviandoEmails(String smtpHost, int smtpPort, final String usuario, final String senha,
                               String De, InternetAddress[] addressTo, String Assunto, String Menssagem) throws Exception {
        enviandoEmails(smtpHost, smtpPort, usuario, senha, false, De, addressTo, Assunto, Menssagem);
    }

    /**
     * Envia e-mail para vários destinatários informando todos os parâmetros SMTP incluindo STARTTLS.
     * @param smtpHost Servidor SMTP
     * @param smtpPort Porta SMTP
     * @param usuario Usuário da conta
     * @param senha Senha da conta
     * @param starttls Se deve usar STARTTLS
     * @param De Remetente
     * @param addressTo Destinatários
     * @param Assunto Assunto
     * @param Menssagem Mensagem HTML
     * @throws Exception
     */
    public void enviandoEmails(String smtpHost, int smtpPort, final String usuario, final String senha, boolean starttls,
                               String De, InternetAddress[] addressTo, String Assunto, String Menssagem) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", String.valueOf(smtpPort));
        boolean usarAuth = (usuario != null && !usuario.trim().isEmpty());
        props.put("mail.smtp.auth", usarAuth ? "true" : "false");
        if (starttls) {
            props.put("mail.smtp.starttls.enable", "true");
        }

        Session session;
        if (usarAuth) {
            session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(usuario, senha);
                }
            });
        } else {
            session = Session.getInstance(props);
        }

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(De));
        msg.setRecipients(Message.RecipientType.TO, addressTo);
        msg.setSentDate(new Date());
        msg.setSubject(Assunto);
        msg.setText(Menssagem, "utf-8", "html");
        Transport.send(msg);
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public boolean isStarttls() {
        return starttls;
    }

    public void setStarttls(boolean starttls) {
        this.starttls = starttls;
    }
}
