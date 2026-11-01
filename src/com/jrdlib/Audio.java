/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrdlib;

import java.io.File;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class Audio {

    // Toca um som  
    public static void tocar(final File arquivo, final boolean repetir) {
        try {
            // Obtém os dados sonoros  
            final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(arquivo);

            // Carrega o formato do audio e cria uma linha  
            final AudioFormat audioFormat = audioInputStream.getFormat();
            final DataLine.Info dataLineInfo = new DataLine.Info(Clip.class,
                    audioInputStream.getFormat(),
                    ((int) audioInputStream.getFrameLength() * audioFormat.getFrameSize()));

            // Carrega o som para o dispositivo  
            final Clip clip = (Clip) AudioSystem.getLine(dataLineInfo);
            clip.addLineListener(new LineListener() {

                // Evento do LineListener  
                @Override
                public void update(final LineEvent e) {
                    if (e.getType() == LineEvent.Type.STOP) {
                        e.getLine().close();
                    }
                }
            });
            clip.open(audioInputStream);

            // Toca o som  
            if (repetir) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.loop(0);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
