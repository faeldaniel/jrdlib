package com.jrdlib;

/*
 * To change this template, choose Tools | Templates
 * and open the template fileInputStream the editor.
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Daniel
 */
public class Compactacao {

    private FileInputStream fileInputStream;
    private ZipOutputStream zipOutputStream;    


    public void compactandoArquivo(String Arquivo) throws Exception {
        // Create a buffer for reading the files
        byte[] buf = new byte[1024];

        // Compress the files
        fileInputStream = new FileInputStream(Arquivo);

        // Create the ZIP file
        zipOutputStream = new ZipOutputStream(new FileOutputStream(new Tratamentos().removeExtencao(Arquivo) + ".zip"));
        //Especifica que haverá compressão
        zipOutputStream.setMethod(zipOutputStream.DEFLATED);
        //Metodo de compressão
        zipOutputStream.setLevel(9);

        // Add ZIP entry to output stream.
        zipOutputStream.putNextEntry(new ZipEntry(Arquivo));

        // Transfer bytes from the file to the ZIP file
        int len;
        while ((len = fileInputStream.read(buf)) > 0) {
            zipOutputStream.write(buf, 0, len);
        }

        // Complete the entry
        zipOutputStream.closeEntry();
        fileInputStream.close();

        // Complete the ZIP file
        zipOutputStream.close();
    }

    /**
     *
     * @param filenames
     * @throws Exception
     * Ex: String[] filenames = new String[]{"filename1", "filename2"}; 
     */
    public void compactandoVariosArquivo(String[] filenames) throws Exception {

        // Create a buffer for reading the files
        byte[] buf = new byte[1024];

        // Create the ZIP file
        String outFilename = "outfile.zip";
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));

        // Compress the files
        for (int i = 0; i < filenames.length; i++) {
            FileInputStream in = new FileInputStream(filenames[i]);

            // Add ZIP entry to output stream.
            out.putNextEntry(new ZipEntry(filenames[i]));

            // Transfer bytes from the file to the ZIP file
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            // Complete the entry
            out.closeEntry();
            in.close();
        }

        // Complete the ZIP file
        out.close();

    }
}
