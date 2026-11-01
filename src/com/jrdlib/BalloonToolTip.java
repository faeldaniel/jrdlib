package com.jrdlib;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import javax.swing.JComponent;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.EdgedBalloonStyle;
import net.java.balloontip.styles.MinimalBalloonStyle;
import net.java.balloontip.utils.TimingUtils;

/**
 *
 * @author Daniel
 */
public class BalloonToolTip {

    private BalloonTip errBalloon = null;

    public void setToolTip(final JComponent comp, final String text) {
        // BalloonTipStyle style = new MinimalBalloonStyle(new Color(169, 205, 221, 220), 5);
        BalloonTipStyle style = new EdgedBalloonStyle(new Color(255, 255, 204, 255), Color.BLACK);
        BalloonTip objBalloonTip = new BalloonTip(comp, comp, style, false);
        TimingUtils.showTimedBalloon(objBalloonTip, 9000);

    }

    /**
     *Um balao tipo de erro
     * @param comp
     * @param Texto
     *
     */
    public void showErrorMessage(JComponent comp, String Texto) {
        if (errBalloon != null) {
            errBalloon.closeBalloon();
        }
        BalloonTipStyle style = new MinimalBalloonStyle(new Color(246, 197, 192, 220), 5);
        errBalloon = new BalloonTip(comp, "<html><font color=\"#6f150d\">" + Texto + "</font></html>", style, true);
        TimingUtils.showTimedBalloon(errBalloon, 9000);
    }

    public void ClosedErrorMessage() {
        if (errBalloon != null) {
            errBalloon.closeBalloon();
        }
    }

    public void setToolTipAvisos(final JComponent comp, final String text, int Tempo) {
        BalloonTipStyle style = new EdgedBalloonStyle(new Color(255, 255, 204, 255), Color.BLACK);
        BalloonTip objBalloonTip = new BalloonTip(comp, text, style, true);
        TimingUtils.showTimedBalloon(objBalloonTip, Tempo);
    }
}
