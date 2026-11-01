package com.jrdlib;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe para Criptografia de dados (MD5).
 * 
 * @author Fernando Campos de Paiva
 * @since 22/01/2008
 * @version 1.0
 */
public class Cryptografia {

    private String defaultPassword = "";

    public Cryptografia() {
    }

    public Cryptografia(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    /**
     * Função de criptografia MD5 utilizando a senha/salt padrão configurada na instância.
     * 
     * @param str string que deseja criptografar
     * @return string criptografada em hexadecimal
     */
    public String Encrypta(String str) {
        return Encrypta(str, this.defaultPassword);
    }

    /**
     * Função de criptografia MD5 com senha/salt passada por parâmetro.
     * 
     * @param str string que deseja criptografar
     * @param password senha ou salt que deseja usar
     * @return string criptografada em hexadecimal
     */
    @SuppressWarnings("finally")
    public String Encrypta(String str, String password) {
        if (password == null || password.trim().isEmpty()) {
            password = (this.defaultPassword != null) ? this.defaultPassword : "";
        }
        String sign = (str != null ? str : "") + password;
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(sign.getBytes());

            byte[] hash = md.digest();
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString(0xFF & hash[i]));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }

            sign = hexString.toString();
        } catch (Exception ex) {
            Logger.getLogger(Cryptografia.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return sign;
        }
    }
}
