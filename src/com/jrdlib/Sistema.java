package com.jrdlib;


/**
 *
 * @author Rafael Daniel
 */
public class Sistema {

    private static int codigoUser = 1;
    private static int codigoEmpresa = 1;
    private static String nomeUser = "ADMINISTRADOR";
    private static String nomeEmpresa = "EMPRESA TESTE";
    private static String Sistema = "jRD";
    private static Double Versao = 0.0;
    private static int tipoOperador = 1;
    private static String tipoOperadorDescricao = "ADMINISTRADOR DO SISTEMA";

    /**
     * @return the codigoUser
     */
    public static int getCodigoUser() {
        return codigoUser;
    }

    /**
     * @param aCodigoUser the codigoUser to set
     */
    public static void setCodigoUser(int aCodigoUser) {
        codigoUser = aCodigoUser;
    }

    /**
     * @return the codigoEmpresa
     */
    public static int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    /**
     * @param aCodigoEmpresa the codigoEmpresa to set
     */
    public static void setCodigoEmpresa(int aCodigoEmpresa) {
        codigoEmpresa = aCodigoEmpresa;
    }

    /**
     * @return the nomeUser
     */
    public static String getNomeUser() {
        return nomeUser;
    }

    /**
     * @param aNomeUser the nomeUser to set
     */
    public static void setNomeUser(String aNomeUser) {
        nomeUser = aNomeUser;
    }

    /**
     * @return the nomeEmpresa
     */
    public static String getNomeEmpresa() {
        return nomeEmpresa;
    }

    /**
     * @param aNomeEmpresa the nomeEmpresa to set
     */
    public static void setNomeEmpresa(String aNomeEmpresa) {
        nomeEmpresa = aNomeEmpresa;
    }

    /**
     * @return the Sistema
     */
    public static String getSistema() {
        return Sistema;
    }

    /**
     * @param aSistema the Sistema to set
     */
    public static void setSistema(String aSistema) {
        Sistema = aSistema;
    }

    /**
     * @return the Versao
     */
    public static Double getVersao() {
        return Versao;
    }

    /**
     * @param aVersao the Versao to set
     */
    public static void setVersao(Double aVersao) {
        Versao = aVersao;
    }

    /**
     * @return the tipoOperador
     */
    public static int getTipoOperador() {
        return tipoOperador;
    }

    /**
     * @param aTipoOperador the tipoOperador to set
     */
    public static void setTipoOperador(int aTipoOperador) {
        tipoOperador = aTipoOperador;
    }

    /**
     * @return the tipoOperadorDescricao
     */
    public static String getTipoOperadorDescricao() {
        return tipoOperadorDescricao;
    }

    /**
     * @param aTipoOperadorDescricao the tipoOperadorDescricao to set
     */
    public static void setTipoOperadorDescricao(String aTipoOperadorDescricao) {
        tipoOperadorDescricao = aTipoOperadorDescricao;
    }

    
}
