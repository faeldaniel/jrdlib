package com.jrdlib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

/**
 *
 * @author Rafael
 * @see Arquivos. diretorios entre outras informacoes
 */
public class ManipulacaoArquivos {

    /**
     *
     * @return
     */
    public String diretorioCorrenteJar() {
        String Dir = System.getProperty("user.dir") + System.getProperty("file.separator");
        return Dir;
    }

    public static void copiandoArquivo(File source, File destination) throws IOException {
        if (destination.exists()) {
            destination.delete();
        }

        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;

        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destinationChannel = new FileOutputStream(destination).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(),
                    destinationChannel);
        } finally {
            if (sourceChannel != null && sourceChannel.isOpen()) {
                sourceChannel.close();
            }
            if (destinationChannel != null && destinationChannel.isOpen()) {
                destinationChannel.close();
            }
        }
    }

    public static void criandoPasta() {

    }

    /**
     *
     * @param file
     * @return
     */
    public static boolean isFileInUse(File file) {

        boolean verificadorEspecifico = false;
        FileChannel channel = null;
        try {
            channel = new RandomAccessFile(file, "rw").getChannel();
        } catch (FileNotFoundException fnfe) {

            if (!file.exists()) {
                System.out.println("Arquivo " + file.getName() + " não existe");
            } else {
                System.out.println("Arquivo " + file.getName() + " esta em uso por outro processo !");
                verificadorEspecifico = true;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (channel == null) {
                return verificadorEspecifico;
            }
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException ioe) {
                    System.out.println(ioe.toString());
                }
            }
        }
        return false;
    }

    public static void deletarDiretorio(File file)
            throws IOException {

        if (file.isDirectory()) {

            //directory is empty, then delete it
            if (file.list().length == 0) {

                file.delete();
                System.out.println("Directory is deleted : "
                        + file.getAbsolutePath());

            } else {

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    deletarDiretorio(fileDelete);
                }

                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                    System.out.println("Directory is deleted : "+ file.getAbsolutePath());
                }
            }

        } else {
            //if file, then delete it
            file.delete();
            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }

}
