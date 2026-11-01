package com.jrdlib.filders;

/**
 * Classe so aceita letras maiusculas
 *
 * @author Rafael Daniel
 *
 * @version 1.00
 *
 * @since Release 01
 *
 */
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class NumerosMonetario extends PlainDocument {

    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (Character.isDigit(str.charAt(0)) || str.equals(",") || str.equals(".")) {
            super.insertString(offs, str, a);
        }
    }

}
