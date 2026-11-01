package com.jrdlib;

/**
 * Classe para tramenteos de Caracteres
 *
 * @author Rafael Daniel
 *
 * @version 1.00
 *
 * @since Release 02
 *
 */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Tratamentos {

    private final DecimalFormat formatador = new DecimalFormat();

    /**
     * Converte uma String de Intervalos e Números Isolados para um Array int(primitivo).
     * Intervalos: usar entre o intervalo "-" finaizando com ";" 1-99; 333-888;
     * Números Isolados: usar "," como separador 5, 7, 10, 99 Exemplo: 1-4;
     * 11-13; 21, 33, 99 -> Retorna {1, 2, 3, 4, 11, 12, 13, 21, 33, 99}
     *
     * @param input
     * @return int[]
     */
    public int[] convertStringToArrayInt(String input) {

        List<Integer> numbersList = new ArrayList<>();

        // Dividindo a string por ';' para tratar intervalos e números separados
        String[] parts = input.split(";");

        for (String part : parts) {
            part = part.trim(); // Remove espaços em branco

            // Verifica se contém um intervalo
            if (part.contains("-")) {
                String[] rangeParts = part.split("-");
                int start = Integer.parseInt(rangeParts[0].trim());
                int end = Integer.parseInt(rangeParts[1].trim());

                // Adiciona todos os números do intervalo
                for (int i = start; i <= end; i++) {
                    numbersList.add(i);
                }
            } else {
                // Se não for um intervalo, trata como números separados
                String[] individualNumbers = part.split(",");
                for (String number : individualNumbers) {
                    numbersList.add(Integer.parseInt(number.trim()));
                }
            }
        }

        // Converte a lista para um array
        return numbersList.stream().distinct().mapToInt(i -> i).toArray();
    }

    /**
     * Converte uma String de Intervalos e Números Isolados para um Array Integer.
     * Intervalos: usar entre o intervalo "-" finaizando com ";" 1-99; 333-888;
     * Números Isolados: usar "," como separador 5, 7, 10, 99 Exemplo: 1-4;
     * 11-13; 21, 33, 99 -> Retorna {1, 2, 3, 4, 11, 12, 13, 21, 33, 99}
     * @param input
     * @return 
     */
    public Integer[] convertStringToArrayInteger(String input) {
        List<Integer> numbersList = new ArrayList<>();

        // Dividindo a string por ';' para tratar intervalos e números separados
        String[] parts = input.split(";");

        for (String part : parts) {
            part = part.trim(); // Remove espaços em branco

            // Verifica se contém um intervalo
            if (part.contains("-")) {
                String[] rangeParts = part.split("-");
                Integer start = Integer.valueOf(rangeParts[0].trim());
                Integer end = Integer.valueOf(rangeParts[1].trim());

                // Adiciona todos os números do intervalo
                for (Integer i = start; i <= end; i++) {
                    numbersList.add(i);
                }
            } else {
                // Se não for um intervalo, trata como números separados
                String[] individualNumbers = part.split(",");
                for (String number : individualNumbers) {
                    numbersList.add(Integer.valueOf(number.trim()));
                }
            }
        }

        // Converte a lista para um array de Integer
        return numbersList.stream().distinct().toArray(Integer[]::new);
    }
        
        
        /**
         * Transforma um numero String e um String no formato Integer
         *
         * @param alterar
         * @return String formato Integer
         */
    public String ValorStringParaInteiroString(String alterar) {
        try {
            if (alterar == null || alterar.isEmpty() || alterar.equals(" ")) {
                alterar = "";
            } else {
                alterar = "" + Integer.parseInt(alterar);
                if (alterar.equals("0")) {
                    alterar = "";
                }
            }
        } catch (NumberFormatException ex) {
            alterar = "";
        }
        return alterar;
    }

    /**
     * Evita que o double fique com um valor invalido Como Nan, Inite ou null
     * Caso sim retorna 0.0 Ideal para o metodo JspinnerPorcentagem
     *
     * @param valor
     * @return Double
     */
    public Double EvitaDoubleInvalido(Double valor) {
        if (valor == null || valor.equals(0.0) || Double.isNaN(valor) || Double.isInfinite(valor)) {
            valor = 0.0;
        }
        return valor;
    }

    /**
     * Transforma um valor String em double Caso ele seja nulo, NAN ou infinito
     * retorna ZERO Ex: 0.0
     *
     * @param alterar
     * @return Double
     */
    public Double ValorStringParaDouble(String alterar) {

        double valor;

        if (alterar == null || alterar.isEmpty() || alterar.equals(" ")) {
            valor = 0.0;
        } else {
            valor = Double.parseDouble(alterar.replace(".", "").replace(",", "."));

            if (Double.isNaN(valor) || Double.isInfinite(valor)) {
                valor = 0.0;
            }
        }
        return valor;
    }

    /**
     * Transforma um valor DOUBLE em STRING Monetária Caso ele seja nulo, NAN ou
     * infinito retorna ZERO Ex: 0,00
     *
     * @param alterar
     * @return String
     */
    public String ValorDoubleParaStringMonetario(Double alterar) {

        String valorMonetario;

        formatador.setMinimumFractionDigits(2);
        formatador.setMaximumFractionDigits(2);

        if (alterar == null || alterar == 0.0 || Double.isNaN(alterar) || Double.isInfinite(alterar)) {
            valorMonetario = "0,00";
        } else {
            valorMonetario = formatador.format(alterar);
        }

        return valorMonetario.trim();
    }

    /**
     * Transforma um valor Double em String no formato monetario R$ Caso ele
     * seja nulo, NAN ou infinito retorna nullo e nao zero como o metodo
     * anterior Ex: 0,00
     *
     * @param alterar
     * @return String
     */
    public String ValorDoubleParaStringMonetarioNulo(Double alterar) {

        String valorMonetario;

        formatador.setMinimumFractionDigits(2);
        formatador.setMaximumFractionDigits(2);

        if (alterar == null || alterar == 0.0 || Double.isNaN(alterar) || Double.isInfinite(alterar)) {
            valorMonetario = null;
        } else {
            valorMonetario = formatador.format(alterar);
        }

        return valorMonetario.trim();
    }

    /**
     * Transforma um valor DOUBLE em uma STRING DECIMAL especificando o total de
     * casas Decimais Caso ele seja nulo, NAN ou infinito retorna ZERO
     *
     * @param alterar
     * @param totalMinimaCasasDeciamis
     * @param totalMaximaCasasDeciamis
     * @return
     */
    public String ValorDoubleParaString(Double alterar, int totalMinimaCasasDeciamis, int totalMaximaCasasDeciamis) {

        formatador.setMinimumFractionDigits(totalMinimaCasasDeciamis);
        formatador.setMaximumFractionDigits(totalMaximaCasasDeciamis);

        String valorMonetario;

        if (alterar == null || alterar == 0.0 || Double.isNaN(alterar) || Double.isInfinite(alterar)) {
            valorMonetario = formatador.format(alterar);
        } else {
            valorMonetario = formatador.format(alterar);
        }

        return valorMonetario.trim();
    }

    /**
     * Remove todos os Zeros iniciais de uma String Ex: 000585501 Retorna 585501
     *
     * @param alterar
     * @return String
     */
    public String RemoveZeroEsquerda(String alterar) {

        String valorRetornar = null;

        if (alterar != null || !alterar.isEmpty()) {
            valorRetornar = alterar.replaceAll("^0*", "");
        }

        return valorRetornar;
    }

    /**
     * Remove todos os espacos de uma String que estejam a esquerda
     *
     * @param alterar
     * @return
     */
    public String RemoveEspacosEsquerda(String alterar) {

        String valorRetornar = null;

        if (alterar != null || !alterar.isEmpty()) {
            valorRetornar = alterar.replaceAll("^ *", "");
        }

        return valorRetornar;
    }

    /**
     * Remove todos os espacos de uma String que estejam a direita
     *
     * @param alterar
     * @return
     */
    public String RemoveEspacosDireita(String alterar) {

        String valorRetornar = null;

        if (alterar != null || !alterar.isEmpty()) {
            valorRetornar = alterar.trim();
        }

        return valorRetornar;
    }

    /**
     * Prreenche zero as esquerda para completar a string baseadas na total de
     * posicoes
     *
     * @param alterar
     * @param totalPosicoes
     * @return
     */
    public String PreencherZeroEsquerda(String alterar, int totalPosicoes) {

        String valorRetornar = "";

        for (int i = (alterar.replaceAll("-", "").length()); i < totalPosicoes; i++) {
            valorRetornar = valorRetornar + "0";
        }

        //Caso seja nagativo o valor passado
        if (alterar.contains("-")) {
            valorRetornar = "-" + valorRetornar;
        }

        return valorRetornar + alterar.replaceAll("-", "");
    }

    /**
     * Preenche espacos a direita para completar as posicoes
     *
     * @param alterar
     * @param totalPosicoes
     * @return
     */
    public String PreencherEspacosDireita(String alterar, int totalPosicoes) {

        String valorRetornar = "";

        for (int i = (alterar.length()); i < totalPosicoes; i++) {
            valorRetornar = valorRetornar + " ";
        }

        return alterar + valorRetornar;
    }

    /**
     * Preenche espacos a esquerda para completar as posicoes
     *
     * @param alterar
     * @param totalPosicoes
     * @return
     */
    public String PreencherEspacosEsquerda(String alterar, int totalPosicoes) {

        String valorRetornar = "";

        for (int i = (alterar.length()); i < totalPosicoes; i++) {
            valorRetornar = valorRetornar + " ";
        }

        return valorRetornar + alterar;
    }

    /**
     * Centraliza um texto com posições fixas preenchendo com espaços
     *
     * @param alterar
     * @param totalMaxPosicoes
     * @return
     */
    public String CentralizarCaracteresComEspacos(String alterar, int totalMaxPosicoes) {

        String outString = alterar;
        int totalLengthString = alterar.length();
        int posicaoIncial = 0;

        //Primeiro verifico se possivel centralizar, pois total de posicoes 
        if (totalMaxPosicoes > totalLengthString) {

            outString = "";

            posicaoIncial = (totalMaxPosicoes - totalLengthString) / 2;

            for (int i = 0; i <= totalMaxPosicoes; i++) {

                if (posicaoIncial == i) {
                    outString = outString + alterar;
                    i = i + totalLengthString;
                } else {
                    outString = outString + " ";
                }

            }
        }

        return outString;

    }

    /**
     * Coloca a mascara no formato de um numero de telefone com DD Ex:
     * 1199998888 Retorna (11)9999-8888 Obs: Caso a String nao tenha 10 ou 11 Digitos
     * retornara somente a mascara
     *
     * @param alterar
     * @return String
     */
    public String MascaraTelefone(String alterar) {

        String mascaraTel;

        if (alterar == null || alterar.isEmpty() || alterar.length() < 10 || alterar.length() > 11) {
            mascaraTel = "";
        } else {

            try {
                mascaraTel = "(";
                mascaraTel = mascaraTel + alterar.substring(0, 2) + ")";

                if (alterar.length() == 10) {
                    mascaraTel = mascaraTel + alterar.substring(2, 6) + "-";
                    mascaraTel = mascaraTel + alterar.substring(6);
                } else {
                    mascaraTel = mascaraTel + alterar.substring(2, 7) + "-";
                    mascaraTel = mascaraTel + alterar.substring(7);
                }

            } catch (Exception erro) {

                mascaraTel = "";
            }
        }
        return mascaraTel;

    }

    /**
     * Coloca a mascara no formato de um CEP Ex: 60823130 Retorna 60.823-130
     * Obs: Caso a String nao tenha 8 Digitos retornara somente a mascara
     *
     * @param alterar
     * @return String
     */
    public String MascaraCEP(String alterar) {

        String mascaraCEP;

        if (alterar == null || alterar.isEmpty() || alterar.length() < 8 || alterar.length() > 8) {
            mascaraCEP = "";
        } else {
            mascaraCEP = alterar.substring(0, 2) + ".";
            mascaraCEP = mascaraCEP + alterar.substring(2, 5) + "-";
            mascaraCEP = mascaraCEP + alterar.substring(5, 8);
        }
        return mascaraCEP;
    }

    /**
     * Coloca a mascara no formato de um Cpf ou de um Cnpj Ex: 94790264300
     * Retorna 947.902.643-00 Obs: Caso a String nao tenha 11 ou 14 Digitos
     * retornara somente a mascara
     *
     * @param alterar
     * @return String
     */
    public String MascaraCpfCnpj(String alterar) {

        String MascaraCpfCnpj;

        if (alterar == null || alterar.isEmpty()) {
            MascaraCpfCnpj = "";
        } else if (alterar.length() == 14) {//Se tiver 14 Caracteres e CNPJ
            MascaraCpfCnpj = alterar.substring(0, 2) + ".";
            MascaraCpfCnpj = MascaraCpfCnpj + alterar.substring(2, 5) + ".";
            MascaraCpfCnpj = MascaraCpfCnpj + alterar.substring(5, 8) + "/";
            MascaraCpfCnpj = MascaraCpfCnpj + alterar.substring(8, 12) + "-";
            MascaraCpfCnpj = MascaraCpfCnpj + alterar.substring(12, 14);
        } else if (alterar.length() == 11) {//Se tiver 11 e CPF
            MascaraCpfCnpj = alterar.substring(0, 3) + ".";
            MascaraCpfCnpj = MascaraCpfCnpj + alterar.substring(3, 6) + ".";
            MascaraCpfCnpj = MascaraCpfCnpj + alterar.substring(6, 9) + "-";
            MascaraCpfCnpj = MascaraCpfCnpj + alterar.substring(9, 11);
        } else { //Se nao nao e nada
            MascaraCpfCnpj = "";
        }
        return MascaraCpfCnpj;
    }

    public String QuantidadeDoubleQuantidadeString(Double alterar) {
        String alterado;

        formatador.setMinimumFractionDigits(3);

        if (alterar == null || alterar == 0.0 || Double.isNaN(alterar) || Double.isInfinite(alterar)) {
            alterado = "0";
        } else {
            alterado = formatador.format(alterar);
        }

        return alterado;

    }

    public String QuantidadeDoubleQuantidadeStringUnitario(Double alterar) {
        String alterado;

        formatador.setMinimumFractionDigits(0);

        if (alterar == null || alterar.equals(0.0) || Double.isNaN(alterar) || Double.isInfinite(alterar)) {

            alterado = "0";

        } else {
            alterado = formatador.format(alterar);
        }

        return alterado;

    }

    public String QuantidadeDoubleQuantidadeStringMetros(Double alterar) {
        String alterado;

        formatador.setMinimumFractionDigits(2);

        if (alterar == null || alterar.equals(0.0) || Double.isNaN(alterar) || Double.isInfinite(alterar)) {

            alterado = "0";

        } else {
            alterado = formatador.format(alterar);
        }

        return alterado;

    }

    public String removeCarectesNumeros(String texto) {
        String NovoTexto = "";
        if (texto != null || !texto.isEmpty()) {
            NovoTexto = texto.replace(".", "").replace(",", "").replace("(", "").replace(")", "").replace("-", "").replace("/", "").replace("\\", "").trim();
        }
        return NovoTexto;
    }

    /**
     * Remove todos os caracteres invalidos no padrao e o torna valido maisculo
     *
     * @param texto
     * @return
     */
    public String removeCaracteres(String texto) {

        String textoAlterado;

        if (texto == null || texto.isEmpty()) {
            textoAlterado = "";
        } else {
            textoAlterado = texto;
            textoAlterado = textoAlterado.trim();
            textoAlterado = textoAlterado.replace("á", "a");
            textoAlterado = textoAlterado.replace("à", "a");
            textoAlterado = textoAlterado.replace("â", "a");
            textoAlterado = textoAlterado.replace("ã", "a");
            textoAlterado = textoAlterado.replace("ä", "a");
            textoAlterado = textoAlterado.replace("Á", "a");
            textoAlterado = textoAlterado.replace("À", "A");
            textoAlterado = textoAlterado.replace("Â", "A");
            textoAlterado = textoAlterado.replace("Ã", "A");
            textoAlterado = textoAlterado.replace("Ä", "A");
            textoAlterado = textoAlterado.replace("é", "e");
            textoAlterado = textoAlterado.replace("è", "e");
            textoAlterado = textoAlterado.replace("ê", "e");
            textoAlterado = textoAlterado.replace("ê", "e");
            textoAlterado = textoAlterado.replace("É", "E");
            textoAlterado = textoAlterado.replace("È", "E");
            textoAlterado = textoAlterado.replace("Ê", "E");
            textoAlterado = textoAlterado.replace("Ë", "E");
            textoAlterado = textoAlterado.replace("í", "i");
            textoAlterado = textoAlterado.replace("ì", "i");
            textoAlterado = textoAlterado.replace("î", "i");
            textoAlterado = textoAlterado.replace("ï", "i");
            textoAlterado = textoAlterado.replace("Í", "I");
            textoAlterado = textoAlterado.replace("Ì", "I");
            textoAlterado = textoAlterado.replace("Î", "I");
            textoAlterado = textoAlterado.replace("Ï", "I");
            textoAlterado = textoAlterado.replace("ó", "o");
            textoAlterado = textoAlterado.replace("ò", "o");
            textoAlterado = textoAlterado.replace("õ", "o");
            textoAlterado = textoAlterado.replace("ô", "o");
            textoAlterado = textoAlterado.replace("ö", "o");
            textoAlterado = textoAlterado.replace("Ï", "o");
            textoAlterado = textoAlterado.replace("Ó", "O");
            textoAlterado = textoAlterado.replace("Ò", "O");
            textoAlterado = textoAlterado.replace("Ô", "O");
            textoAlterado = textoAlterado.replace("Õ", "O");
            textoAlterado = textoAlterado.replace("Ö", "O");
            textoAlterado = textoAlterado.replace("ú", "u");
            textoAlterado = textoAlterado.replace("ù", "u");
            textoAlterado = textoAlterado.replace("û", "u");
            textoAlterado = textoAlterado.replace("ü", "u");
            textoAlterado = textoAlterado.replace("Ú", "U");
            textoAlterado = textoAlterado.replace("Ù", "U");
            textoAlterado = textoAlterado.replace("Û", "U");
            textoAlterado = textoAlterado.replace("ç", "c");
            textoAlterado = textoAlterado.replace("Ç", "C");
            textoAlterado = textoAlterado.replace("ñ", "n");
            textoAlterado = textoAlterado.replace("Ñ", "N");

            textoAlterado = textoAlterado.replace(">", " ");
            textoAlterado = textoAlterado.replace(".", " ");
            textoAlterado = textoAlterado.replace(",", " ");
            textoAlterado = textoAlterado.replace("<", " ");
            textoAlterado = textoAlterado.replace(":", " ");
            textoAlterado = textoAlterado.replace(";", " ");
            textoAlterado = textoAlterado.replace("~", " ");
            textoAlterado = textoAlterado.replace("^", " ");
            textoAlterado = textoAlterado.replace("/", " ");
            textoAlterado = textoAlterado.replace("?", " ");
            textoAlterado = textoAlterado.replace("°", " ");
            textoAlterado = textoAlterado.replace("]", " ");
            textoAlterado = textoAlterado.replace("}", " ");
            textoAlterado = textoAlterado.replace("ª", " ");
            textoAlterado = textoAlterado.replace("[", " ");
            textoAlterado = textoAlterado.replace("{", " ");
            textoAlterado = textoAlterado.replace("´", " ");
            textoAlterado = textoAlterado.replace("`", " ");
            textoAlterado = textoAlterado.replace("\\", "");
            textoAlterado = textoAlterado.replace("|", " ");
            textoAlterado = textoAlterado.replace("'", " ");
            textoAlterado = textoAlterado.replace("\"", " ");
            textoAlterado = textoAlterado.replace("!", " ");
            textoAlterado = textoAlterado.replace("@", " ");
            textoAlterado = textoAlterado.replace("#", " ");
            textoAlterado = textoAlterado.replace("$", " ");
            textoAlterado = textoAlterado.replace("%", " ");
            textoAlterado = textoAlterado.replace("¨", " ");
            textoAlterado = textoAlterado.replace("&", " ");
            textoAlterado = textoAlterado.replace("*", " ");
            textoAlterado = textoAlterado.replace("(", " ");
            textoAlterado = textoAlterado.replace(")", " ");
            textoAlterado = textoAlterado.replace("-", " ");
            textoAlterado = textoAlterado.replace("_", " ");
            textoAlterado = textoAlterado.replace("=", " ");
            textoAlterado = textoAlterado.replace("+", " ");
            textoAlterado = textoAlterado.replace("§", " ");
            textoAlterado = textoAlterado.toUpperCase();
        }

        return textoAlterado;
    }

    /**
     * Remove alguns caracteres invalidos no padrao deixando apenas as
     * acenuações e o torna valido maisculo
     *
     * @param texto
     * @return
     */
    public String removeAlgunsCaracteresUpCase(String texto) {

        String textoAlterado;

        if (texto == null || texto.isEmpty()) {
            textoAlterado = "";
        } else {
            textoAlterado = texto;
            textoAlterado = textoAlterado.trim();
            textoAlterado = textoAlterado.replace("á", "a");
            textoAlterado = textoAlterado.replace("à", "a");
            textoAlterado = textoAlterado.replace("â", "a");
            textoAlterado = textoAlterado.replace("ã", "a");
            textoAlterado = textoAlterado.replace("ä", "a");
            textoAlterado = textoAlterado.replace("Á", "a");
            textoAlterado = textoAlterado.replace("À", "A");
            textoAlterado = textoAlterado.replace("Â", "A");
            textoAlterado = textoAlterado.replace("Ã", "A");
            textoAlterado = textoAlterado.replace("Ä", "A");
            textoAlterado = textoAlterado.replace("é", "e");
            textoAlterado = textoAlterado.replace("è", "e");
            textoAlterado = textoAlterado.replace("ê", "e");
            textoAlterado = textoAlterado.replace("ê", "e");
            textoAlterado = textoAlterado.replace("É", "E");
            textoAlterado = textoAlterado.replace("È", "E");
            textoAlterado = textoAlterado.replace("Ê", "E");
            textoAlterado = textoAlterado.replace("Ë", "E");
            textoAlterado = textoAlterado.replace("í", "i");
            textoAlterado = textoAlterado.replace("ì", "i");
            textoAlterado = textoAlterado.replace("î", "i");
            textoAlterado = textoAlterado.replace("ï", "i");
            textoAlterado = textoAlterado.replace("Í", "I");
            textoAlterado = textoAlterado.replace("Ì", "I");
            textoAlterado = textoAlterado.replace("Î", "I");
            textoAlterado = textoAlterado.replace("Ï", "I");
            textoAlterado = textoAlterado.replace("ó", "o");
            textoAlterado = textoAlterado.replace("ò", "o");
            textoAlterado = textoAlterado.replace("õ", "o");
            textoAlterado = textoAlterado.replace("ô", "o");
            textoAlterado = textoAlterado.replace("ö", "o");
            textoAlterado = textoAlterado.replace("Ï", "o");
            textoAlterado = textoAlterado.replace("Ó", "O");
            textoAlterado = textoAlterado.replace("Ò", "O");
            textoAlterado = textoAlterado.replace("Ô", "O");
            textoAlterado = textoAlterado.replace("Õ", "O");
            textoAlterado = textoAlterado.replace("Ö", "O");
            textoAlterado = textoAlterado.replace("ú", "u");
            textoAlterado = textoAlterado.replace("ù", "u");
            textoAlterado = textoAlterado.replace("û", "u");
            textoAlterado = textoAlterado.replace("ü", "u");
            textoAlterado = textoAlterado.replace("Ú", "U");
            textoAlterado = textoAlterado.replace("Ù", "U");
            textoAlterado = textoAlterado.replace("Û", "U");
            textoAlterado = textoAlterado.replace("ç", "c");
            textoAlterado = textoAlterado.replace("Ç", "C");
            textoAlterado = textoAlterado.replace("ñ", "n");
            textoAlterado = textoAlterado.replace("Ñ", "N");

            textoAlterado = textoAlterado.replace(">", " ");
            textoAlterado = textoAlterado.replace("<", " ");
            textoAlterado = textoAlterado.replace(";", " ");
            textoAlterado = textoAlterado.replace("~", " ");
            textoAlterado = textoAlterado.replace("^", " ");
            textoAlterado = textoAlterado.replace("]", " ");
            textoAlterado = textoAlterado.replace("}", " ");
            textoAlterado = textoAlterado.replace("ª", " ");
            textoAlterado = textoAlterado.replace("[", " ");
            textoAlterado = textoAlterado.replace("{", " ");
            textoAlterado = textoAlterado.replace("´", " ");
            textoAlterado = textoAlterado.replace("`", " ");
            textoAlterado = textoAlterado.replace("|", " ");
            textoAlterado = textoAlterado.replace("'", " ");
            textoAlterado = textoAlterado.replace("\"", " ");
            textoAlterado = textoAlterado.replace("#", " ");
            textoAlterado = textoAlterado.replace("$", " ");
            textoAlterado = textoAlterado.replace("%", " ");
            textoAlterado = textoAlterado.replace("¨", " ");
            textoAlterado = textoAlterado.replace("&", " ");
            textoAlterado = textoAlterado.replace("*", " ");
            textoAlterado = textoAlterado.replace("(", " ");
            textoAlterado = textoAlterado.replace(")", " ");
            textoAlterado = textoAlterado.replace("_", " ");
            textoAlterado = textoAlterado.replace("=", " ");
            textoAlterado = textoAlterado.replace("+", " ");
            textoAlterado = textoAlterado.replace("§", " ");
            textoAlterado = textoAlterado.toUpperCase();
        }

        return textoAlterado;
    }

    /**
     * Retorna um numero inteiro String com duas cadas decimais Ideal para uso
     * em converter meses Ex: int 1 retorna 01
     *
     * @param numero
     * @return
     */
    public String numeroInteiroComDuasCasas(int numero) {

        String mesString = "" + numero;

        if (mesString.length() == 1) {
            return "0" + numero;
        } else {
            return "" + numero;
        }

    }

    /**
     * Retorna a extenção de um arquivo informado
     *
     * @param NomeArquivo
     * @return
     */
    public String retornaExtencao(String NomeArquivo) {

        if (NomeArquivo != null) {
            String extencao = "";

            for (int i = NomeArquivo.length(); i >= 0; i = i - 1) {//Executando um for reverso
                if ((i - 1 >= 0)) {//Evita java.lang.StringIndexOutOfBoundsException
                    if (NomeArquivo.substring(i - 1, i).equals(".")) {//se tiver ponto pare o for e retorne a extencao
                        extencao = NomeArquivo.substring(i - 1, NomeArquivo.length());
                        break;
                    }
                }
            }
            return extencao;
        } else {
            return "";
        }
    }

    /**
     * Retorna o Nome do Arquivo sem extencao
     *
     * @param NomeArquivo
     * @return
     */
    public String removeExtencao(String NomeArquivo) {
        if (NomeArquivo != null) {
            return NomeArquivo.replaceAll(NomeArquivo, retornaExtencao(NomeArquivo));
        } else {
            return "";
        }
    }
}
