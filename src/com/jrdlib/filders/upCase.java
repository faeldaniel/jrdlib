package com.jrdlib.filders;


/**Classe so aceita letras maiusculas

 * @author Rafael Daniel

 * @version 1.00

 * @since Release 01

 */
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class upCase extends PlainDocument {

    /** JTextField somente maisculo
     * @return exem: jTF.setDocument(new upCase());
     * */
    private int iMaxLength;

    public upCase(int maxlen) {
        super();
        iMaxLength = maxlen;
    }

    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (str == null) {
            return;
        }

        if (str.equals(">")
                || str.equals(".")
                || str.equals(",")
                || str.equals("<")
                || str.equals(":")
                || str.equals(";")
                || str.equals("~")
                || str.equals("^")
                || str.equals("/")
                || str.equals("?")
                || str.equals("°")
                || str.equals("]")
                || str.equals("}")
                || str.equals("ª")
                || str.equals("[")
                || str.equals("{")
                || str.equals("´")
                || str.equals("`")
                || str.equals("\\")
                || str.equals("|")
                || str.equals("'")
                || str.equals("\"")
                || str.equals("!")
                || str.equals("@")
                || str.equals("#")
                || str.equals("$")
                || str.equals("%")
                || str.equals("¨")
                || str.equals("&")
                || str.equals("*")
                || str.equals("(")
                || str.equals(")")
                || str.equals("-")
                || str.equals("_")
                || str.equals("=")
                || str.equals("+")
                || str.equals("§")
                || str.equals("²")
                || str.equals("£")
                || str.equals("¢")
                || str.equals("¬")
                || str.equals("¹")) {
            return;
        }

        str = str.replace("á", "A");
        str = str.replace("à", "A");
        str = str.replace("â", "A");
        str = str.replace("ã", "A");
        str = str.replace("ä", "A");
        str = str.replace("Á", "A");
        str = str.replace("À", "A");
        str = str.replace("Â", "A");
        str = str.replace("Ã", "A");
        str = str.replace("Ä", "A");
        str = str.replace("é", "E");
        str = str.replace("è", "E");
        str = str.replace("ê", "E");
        str = str.replace("ê", "E");
        str = str.replace("É", "E");
        str = str.replace("È", "E");
        str = str.replace("Ê", "E");
        str = str.replace("Ë", "E");
        str = str.replace("í", "I");
        str = str.replace("ì", "I");
        str = str.replace("î", "I");
        str = str.replace("ï", "I");
        str = str.replace("Í", "I");
        str = str.replace("Ì", "I");
        str = str.replace("Î", "I");
        str = str.replace("Ï", "I");
        str = str.replace("ó", "O");
        str = str.replace("ò", "O");
        str = str.replace("õ", "O");
        str = str.replace("ô", "O");
        str = str.replace("ö", "O");
        str = str.replace("Ï", "O");
        str = str.replace("Ó", "O");
        str = str.replace("Ò", "O");
        str = str.replace("Ô", "O");
        str = str.replace("Õ", "O");
        str = str.replace("Ö", "O");
        str = str.replace("ú", "U");
        str = str.replace("ù", "U");
        str = str.replace("û", "U");
        str = str.replace("ü", "U");
        str = str.replace("Ú", "U");
        str = str.replace("Ù", "U");
        str = str.replace("Û", "U");
        str = str.replace("ç", "C");
        str = str.replace("Ç", "C");
        str = str.replace("ñ", "N");
        str = str.replace("Ñ", "N");

        if (iMaxLength <= 0) // aceitara qualquer no. de caracteres
        {
            super.insertString(offset, str.toUpperCase(), attr);
            return;
        }

        int ilen = (getLength() + str.length());
        if (ilen <= iMaxLength) {    // se o comprimento final for menor...
            super.insertString(offset, str.toUpperCase(), attr);   // ...aceita str
        } else {
            if (getLength() == iMaxLength) {
                return; // nada a fazer
            }
            String newStr = str.substring(0, (iMaxLength - getLength()));
            super.insertString(offset, str.toUpperCase(), attr);
        }
    }
}