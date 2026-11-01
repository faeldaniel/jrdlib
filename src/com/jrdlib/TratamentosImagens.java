/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrdlib;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Rafael Daniel
 */
public class TratamentosImagens {

    private final String tipoPng = "PNG";
    private final String tipoJpg = "JPG";
    private final String tipoGif = "GIF";

  public void saveIcon(String imageType, String imagePath, String destinationPath) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(imagePath));

        // Rescale the image to 256x256 using a Graphics2D object
        BufferedImage scaledImage = new BufferedImage(256, 256, originalImage.getType());
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, 256, 256, null);
        g2d.dispose();

        // Write the scaled image to the destination path
        ImageIO.write(scaledImage, imageType, new File(destinationPath));
    }

    public void setIconJLabel(String caminhoImagem, int widthImagem, int heightImagem, JLabel objLabel) {
        ImageIcon imgIcon = new ImageIcon(caminhoImagem);
        imgIcon.setImage(imgIcon.getImage().getScaledInstance(widthImagem, heightImagem, 100));
        objLabel.setText(null);
        objLabel.setIcon(new ImageIcon(imgIcon.getImage()));
    }

    /**
     * @return the tipoPng
     */
    public String getTipoPng() {
        return tipoPng;
    }

    /**
     * @return the tipoJpg
     */
    public String getTipoJpg() {
        return tipoJpg;
    }

    /**
     * @return the tipoGif
     */
    public String getTipoGif() {
        return tipoGif;
    }
}
