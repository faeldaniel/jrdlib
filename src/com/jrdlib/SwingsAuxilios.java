package com.jrdlib;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.util.Date;
import javax.swing.JInternalFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import java.util.logging.Level;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

/**
 *
 * @author Daniel
 */
public class SwingsAuxilios {

    private GeradorLogs objGeradorLogs = new GeradorLogs();

    /**
     * Centraliza um JInternalFrame no centro do JDesktopPane
     * @param objJInternalFrame
     */
    public void centralizarJInternalFrame(JInternalFrame objJInternalFrame) {
        Dimension d = objJInternalFrame.getDesktopPane().getSize();
        objJInternalFrame.setLocation((d.width - objJInternalFrame.getSize().width) / 2, 1);
    }

    /**
     * Define um padrao para o JSpinner onde é possível definir ValorMinimo, Maximo no qual
     * o usuário pode usar
     * @param jSP
     * @param ValorPadrao
     * @param ValorMinimo
     * @param ValorMaximo
     */
    public void JSpinnerMonetario(JSpinner jSP, Double ValorPadrao, Double ValorMinimo, Double ValorMaximo) {
        Double AumentaDe = 0.01;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(ValorPadrao, ValorMinimo, ValorMaximo, AumentaDe);
        jSP.setModel(spinnerModel);
        jSP.setEditor(new JSpinner.NumberEditor(jSP, "#0.00"));
    }

    /**
     * Define um padro para o JSpinner no formto padrao de porcentagem, ou seja, de 0 à 100
     * @param jSP
     */
    public void JSpinnerPorcentagemm(JSpinner jSP) {
        Double ValorPadrao = 0.00;
        Double ValorMinimo = 0.00;
        Double ValorMaximo = 100.00;
        Double AumentaDe = 0.01;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(ValorPadrao, ValorMinimo, ValorMaximo, AumentaDe);
        jSP.setModel(spinnerModel);
        jSP.setEditor(new JSpinner.NumberEditor(jSP, "#0.00"));
    }

    /**
     * Traduz o JFileChooser que no padrão é em ingles
     */
    public void traduzJFileChooser() {

        UIManager.put("FileChooser.lookInLabelMnemonic", "E");
        UIManager.put("FileChooser.lookInLabelText", "Examinar em");

        UIManager.put("FileChooser.saveInLabelMnemonic", "S");
        UIManager.put("FileChooser.saveInLabelText", "Salvar em");

        UIManager.put("FileChooser.upFolderToolTipText", "Um nível acima");
        UIManager.put("FileChooser.upFolderAccessibleName", "Um nível acima");

        UIManager.put("FileChooser.homeFolderToolTipText", "Desktop");
        UIManager.put("FileChooser.homeFolderAccessibleName", "Desktop");

        UIManager.put("FileChooser.newFolderToolTipText", "Criar nova pasta");
        UIManager.put("FileChooser.newFolderAccessibleName", "Criar nova pasta");

        UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
        UIManager.put("FileChooser.listViewButtonAccessibleName", "Lista");

        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Detalhes");
        UIManager.put("FileChooser.detailsViewButtonAccessibleName", "Detalhes");

        UIManager.put("FileChooser.fileNameLabelMnemonic", "N");
        UIManager.put("FileChooser.fileNameLabelText", "Nome do arquivo");

        UIManager.put("FileChooser.filesOfTypeLabelMnemonic", "A");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Arquivos do tipo");

        UIManager.put("FileChooser.fileNameHeaderText", "Nome");
        UIManager.put("FileChooser.fileSizeHeaderText", "Tamanho");
        UIManager.put("FileChooser.fileTypeHeaderText", "Tipo");
        UIManager.put("FileChooser.fileDateHeaderText", "Data");
        UIManager.put("FileChooser.fileAttrHeaderText", "Atributos");

        UIManager.put("FileChooser.cancelButtonText", "Cancelar");
        UIManager.put("FileChooser.cancelButtonMnemonic", "C");
        UIManager.put("FileChooser.cancelButtonToolTipText", "Cancelar");

        UIManager.put("FileChooser.openButtonText", "Abrir");
        UIManager.put("FileChooser.openButtonMnemonic", "A");
        UIManager.put("FileChooser.openButtonToolTipText", "Abrir");

        UIManager.put("FileChooser.saveButtonText", "Salvar");
        UIManager.put("FileChooser.saveButtonToolTipText", "S");
        UIManager.put("FileChooser.saveButtonToolTipText", "Salvar");

        UIManager.put("FileChooser.updateButtonText", "Alterar");
        UIManager.put("FileChooser.updateButtonToolTipText", "A");
        UIManager.put("FileChooser.updateButtonToolTipText", "Alterar");

        UIManager.put("FileChooser.helpButtonText", "Ajuda");
        UIManager.put("FileChooser.helpButtonToolTipText", "A");
        UIManager.put("FileChooser.helpButtonToolTipText", "Ajuda");

        UIManager.put("FileChooser.acceptAllFileFilterText", "Todos os arquivos");
    }

    /**
     * Retorna um caixa de erro Exception mas robusta com registro de log no envento
     * ultilizar em conjunto com a classe GeradorLogs
     * @param Menssagem
     * @param throwable
     */
    public void ErrorMenssagemException(String Menssagem, Throwable throwable) {
        ErrorInfo info = new ErrorInfo("Aviso do Sistema - Erro Inesperado", Menssagem + ", caso o problema persista contate o Administrador do Sistema. ", null, "category", throwable, Level.ALL, null);
        JXErrorPane.showDialog(null, info);
        objGeradorLogs.InserindoLog(Menssagem + " // " + throwable.toString());
    }

    /**
     * Traz o jInternalFrame para frente de todas as janelas
     * @param obj
     */
    public void trazendoJInternalFrameFrente(JInternalFrame obj){
        try {
            obj.getDesktopPane().moveToFront(obj);
            obj.getDesktopPane().getDesktopManager().activateFrame(obj);
            obj.requestFocus();
            obj.setSelected(true);
        } catch (PropertyVetoException ex) {
           // Logger.getLogger(SwingsAuxilios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * MUDA O MASCARA DO Cpf/Cnpj DO JFormattedTextField DE ACORDO COM jRadion ESCOLHIDO.
     * Metodo para ser inserido nos ActionPerformed dos Radios Buttons do Cpf e Cnpj.
     * Colocar o atributo setFocusLostBehavior() como setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST)
     * para que o metodo possa funcionar.
     * @param jFTF_CpfCnpj
     * @param jRB_Cpf
     * @param jRB_Cnpj
     */
    public void mudandoMascaraCpfCnpj(JFormattedTextField jFTF_CpfCnpj, JRadioButton jRB_Cpf, JRadioButton jRB_Cnpj) {

        try {
            if (jRB_Cpf.isSelected()) {
                jFTF_CpfCnpj.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
            } else {
                jFTF_CpfCnpj.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
            }
        } catch (java.text.ParseException ex) {
            new SwingsAuxilios().ErrorMenssagemException("Ocorreu um erro ao tentar mudar a mascara do Cpf/Cnpj", ex);
        }

    }

    /**
     * retorna Sim ou nao no formatdo do Access
     * @param valor
     * @return
     */
    public String simNaoAccess(boolean valor){
        if(valor == true)
            return "1";
       else
           return "0";
    }
    
    
    

}
