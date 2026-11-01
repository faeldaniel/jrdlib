package com.jrdlib;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class Impressoes {

    /**
     * Imprime diretamente na Porta Paralela
     *
     * @param TextoImprimir
     */
    public void imprimirTextoPortaParalela(String TextoImprimir) {
        try {
            FileOutputStream fos = null;
            PrintStream ps = null;
            if (System.getProperty("os.name").contains("Windows")) {
                //fos = new FileOutputStream("LPT1:");
                 fos = new FileOutputStream("COM3:");
                ps = new PrintStream(fos);
                ps.println(TextoImprimir);
            } else if (System.getProperty("os.name").contains("Linux")) {
                fos = new FileOutputStream("/dev/usb/lp0");
                ps = new PrintStream(fos);
                ps.println(TextoImprimir);
            } else {
                JOptionPane.showMessageDialog(null, "Sistema Operacional não identificado",
                        "Aviso do Sistema - Tentativa de Impressão", JOptionPane.ERROR_MESSAGE);
            }
            fos.flush();
            ps.flush();
            fos.close();
            ps.close();
        } catch (Exception ex) {
            new SwingsAuxilios().ErrorMenssagemException("Não foi possível efetuar a impressão, logo abaixo você pode ver detalhes do erro que podem ajudá-lo a enteder o problema\nErro retornado: " + ex.getMessage(), ex);
        }
    }

    public void imprimirTextoPortaUsb(String TextoImprimir) {
        try {
            FileOutputStream fos = null;
            PrintStream ps = null;
            if (System.getProperty("os.name").contains("Windows")) {
                fos = new FileOutputStream("USB000");
                ps = new PrintStream(fos);
                ps.println(TextoImprimir);
            } else if (System.getProperty("os.name").contains("Linux")) {
                fos = new FileOutputStream("/dev/usb/lp0");
                ps = new PrintStream(fos);
                ps.println(TextoImprimir);
            } else {
                JOptionPane.showMessageDialog(null, "Sistema Operacional não identificado",
                        "Aviso do Sistema - Tentativa de Impressão", JOptionPane.ERROR_MESSAGE);
            }
            fos.flush();
            ps.flush();
            fos.close();
            ps.close();
        } catch (Exception ex) {
            new SwingsAuxilios().ErrorMenssagemException("Não foi possível efetuar a impressão, logo abaixo você pode ver detalhes do erro que podem ajudá-lo a enteder o problema\nErro retornado: " + ex.getMessage(), ex);
        }
    }

    /**
     * Imprimir diretamente na impressora informada
     * @param TextoImpressao
     * @param nomeImpressora 
     */
    public void ImprimirDiretamenteNomeImpressora(String TextoImpressao, String nomeImpressora) {

        PrintService impressora = null;
        //Listando as impressoras disponiveis
        DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, null);

        for (PrintService p : ps) {
            if (p.getName().contains(nomeImpressora)) {
                impressora = p;
                break;

            }
        }

        if (impressora == null) {

            JOptionPane.showMessageDialog(null, "Nenhuma impressora encontrada com o nome especificado,\nverique se ainda existe uma impressora instalada com o nome informado.", "Aviso do Sistema", JOptionPane.ERROR_MESSAGE);

        } else {

            try {

                DocPrintJob dpj = impressora.createPrintJob();
                InputStream stream = new ByteArrayInputStream(TextoImpressao.getBytes());

                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                Doc doc = new SimpleDoc(stream, flavor, null);
                dpj.print(doc, null);


            } catch (PrintException ex) {
                new SwingsAuxilios().ErrorMenssagemException("Não foi possível efetuar a impressão, logo abaixo você pode ver detalhes do erro que podem ajudá-lo a enteder o problema\nErro retornado: " + ex.getMessage(), ex);
            }

        }


    }

    /**
     * Envia uma impressão em modo de texto para a impressao padrão instalada
     * @param TextoImpressao
     */
    public void imprimirTextoImpressoraPadrao(String TextoImpressao) {

        //Pegando a impressora padrao do sistema operacional
        PrintService ps = PrintServiceLookup.lookupDefaultPrintService();

        try {

            DocPrintJob dpj = ps.createPrintJob();
            InputStream stream = new ByteArrayInputStream(TextoImpressao.getBytes());

            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc doc = new SimpleDoc(stream, flavor, null);
            dpj.print(doc, null);


        } catch (PrintException ex) {
             new SwingsAuxilios().ErrorMenssagemException("Não foi possível efetuar a impressão, logo abaixo você pode ver detalhes do erro que podem ajudá-lo a enteder o problema\nErro retornado: " + ex.getMessage(), ex);
        }
    }
    
    public void imprimirImpressoraPadrao(){
        
    }
    
 
     private boolean imprimirArquivo(String arquivo) throws Exception {
        FileInputStream arq = new FileInputStream(arquivo);
        

        DocFlavor formatoDoc = DocFlavor.INPUT_STREAM.JPEG;
        Doc documento = new SimpleDoc(arq,formatoDoc,null);
        PrintRequestAttributeSet configuracoes =  new HashPrintRequestAttributeSet();
        //*** Configura a quantidade de cópias ***
        //configuracoes.add(new Copies(qtdCopias));
        //*** Configura o tamanho do papel ***
        //configuracoes.add(MediaSize.getMediaSizeForName(MediaSizeName.ISO_A4));
        //*** Configura dupla página ***
        //configuracoes.add(Sides.DUPLEX);
       // PrintService[] servicos = PrintServiceLookup.lookupPrintServices(formatoDoc, configuracoes);
         PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
        
           DocPrintJob job = ps.createPrintJob();
           job.print(documento, configuracoes);

        return false;
    }
     
}
