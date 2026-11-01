package com.splashs;


// ScreenPanel.java
// Andrew Davison, ad@fivedots.coe.psu.ac.th, November 2004

/* This JPanel shows a picture of the screen _behind_ the 
   application's JFrame, which is updated as the JFrame moves --
   this gives the illusion of transparency.

   A snap is taken of the entire screen, and then the relevant
   rectangle is shown depending on the current location of the 
   JFrame.

   We try to avoid regenerating the image too often, since it's
   a slow task and causes a flicker. A new image is taken when:

     1. the application starts;
     2. when the application is de-iconified;
     3. when the application gains focus from another application
        and comes to the front.
*/

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;


public class ScreenPanel extends JPanel
{
  private int bgWidth, bgHeight;   // of background image

  private int scrWidth, scrHeight;  // of screen
  private Rectangle scrRect;   // for the screen

  // where the image is drawn in this panel
  private int xDraw = 0;   
  private int yDraw = 0;   

  private Robot robot = null;
  private BufferedImage scrIm = null;   // stores the entire screen image
  private BufferedImage subIm = null;   // the sub-region currently being displayed


  public ScreenPanel(int bgW, int bgH, int xScr, int yScr)
  {
    bgWidth = bgW; bgHeight = bgH;
    setBounds(0, 0, bgWidth, bgHeight);

    // get screen dimensions and rectangle
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    scrWidth = d.width;  scrHeight = d.height;
    scrRect = new Rectangle(0,0, scrWidth, scrHeight);

    try {
      robot = new Robot();
    }
    catch(Exception e)
    { System.out.println("Error with robot"); }

    refresh(xScr, yScr);
  } // end of ScreenPanel()


  public void refresh(int xScr, int yScr)
  /* Take a new shap of the screen, and show it at
     (xScr, yScr). Called from here and by the top-level JFrame.
  */
  {
    scrIm = robot.createScreenCapture(scrRect);    // take a snap
    moveScreen(xScr, yScr);
  }  // end of refresh()


  public void moveScreen(int xScr, int yScr)
  /*  This method calculates the subregion, subIm, that should be
      displayed, when this component is next redrawn.

      It's complicated by the fact that the application may be
      partially off the screen, on the left, right, top, or
      bottom. This requires us to also calculate the (xDraw,yDraw)
      coordinate where subIm should be rendered in this JPanel.
  */
  // called from here and by the top-level JFrame
  {
    xDraw = 0;          // default drawing position in this JPanel
    yDraw = 0;

    int x = xScr;      // default (x,y) for screen subimage
    int y = yScr;
    int w = bgWidth;   // default width and height of subimage
    int h = bgHeight;

    if (xScr > (scrWidth-bgWidth))  // panel rhs is off screen
      w = scrWidth - xScr;
    else if (xScr < 0) {   // panel lhs is off screen
      xDraw = -xScr;
      x = 0;
      w = scrWidth - xDraw;
    }

    if (yScr > (scrHeight-bgHeight))  // panel bottom is off screen
      h = scrHeight - yScr;
    else if (yScr < 0) {   // panel top is off screen
      yDraw = -yScr;
      y = 0;
      h = scrHeight - yDraw;
    }

    subIm =  scrIm.getSubimage(x, y, w, h);
  }  // end of moveScreen()


    @Override
  protected void paintComponent(Graphics g) 
  // draw the screen subregion
  { super.paintComponent(g);
    g.drawImage(subIm, xDraw, yDraw, null);
  } // end of paintComponent();


} // end of ScreenPanel class
