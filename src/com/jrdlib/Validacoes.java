package com.jrdlib;

/**
 * Classe para validações
 * @author Rafael Daniel
 */
public class Validacoes {

    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    private int calcularDigito(String str, int[] peso) {
        int soma = 0;

        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }

        soma = 11 - soma % 11;

        return (soma > 9) ? 0 : soma;
    }

    /**
     * Verifica se o Cpf é válido
     *
     * @param cpf
     * @return
     */
    public boolean isValidCPF(String cpf) {
        if ((cpf == null) || (cpf.length() != 11)) {
            return false;
        }

        Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);

        return cpf.equals(cpf.substring(0, 9) + digito1.toString()
                + digito2.toString());
    }

    /**
     * Verifica se o Cnpj é válido
     *
     * @param cnpj
     * @return
     */
    public boolean isValidCNPJ(String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14)) {
            return false;
        }

        Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1,
                pesoCNPJ);

        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString()
                + digito2.toString());
    }

    /**
     * Verifica se o Cpf/Cnpj é válido
     *
     * @param Creterio
     * @return boolean
     */
    public boolean Valido(String Creterio) {
        boolean situacao = false;

        if (isValidCPF(Creterio)) {
            situacao = true;
        } else if (isValidCNPJ(Creterio)) {
            situacao = true;
        }

        return situacao;
    }

    /**
     * Valida chave da Nota Fiscal Eletrônica
     * @param chaveNfe
     * @return 
     */
    public static boolean isValidChaveNfe(String chaveNfe) {

        int[] peso = {4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2, 0};

        double soma;
        int verf;

        try {

            if (chaveNfe.length() < 44) {
                return false;
            }

            chaveNfe = chaveNfe.replace(" ", "");
            chaveNfe = chaveNfe.replace("-", "");
            chaveNfe = chaveNfe.replace(".", "");

            soma = 0;
            for (int j = 0; j < chaveNfe.length() - 1; j++) {
                soma += Integer.parseInt(String.valueOf(chaveNfe.charAt(j))) * peso[j];
            }

            if (soma == 0) {
                return false;
            }

            if ((soma % 11) == 0 || (soma % 11) == 1) {

                verf = 0;
            } else {
                verf = (int) (11 - (soma % 11));
            }

            return (verf == Integer.parseInt(String.valueOf(chaveNfe.charAt(43))));

        } catch (Exception e) {
            return false;
        }
    }

}
