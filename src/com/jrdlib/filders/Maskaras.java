package com.jrdlib.filders;


import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Rafael
 */
public class Maskaras {


    /** Cria maskara para campos JFormattedTextField
      * @return opcoes 0 CPF, 1 CNPJ, 2 TELEFONE
     * */
    public void MaskarasJFTF(JFormattedTextField jFTF, int opcao){
        switch(opcao){
             case 0:
                 try {
                    MaskFormatter ftm = new MaskFormatter("###.###.###-##");
                    ftm.setValidCharacters("0123456789");
                    jFTF.setFormatterFactory(new DefaultFormatterFactory(ftm));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro não grave em:\n"+ex);
            }
             break;

             case 1:
                 try {
                    MaskFormatter ftm = new MaskFormatter("##.###.###/####-##");
                    ftm.setValidCharacters("0123456789");
                    jFTF.setFormatterFactory(new DefaultFormatterFactory(ftm));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro não grave em:\n"+ex);
            }
             break;

             case 2:
                 try {
                    MaskFormatter ftm = new MaskFormatter("(##)####-####");
                    ftm.setValidCharacters("0123456789");
                    jFTF.setFormatterFactory(new DefaultFormatterFactory(ftm));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro não grave em:\n"+ex);
            }
             break;
         }
    }


}
