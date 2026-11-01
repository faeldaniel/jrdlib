package com.splashs;


// BackGroundPanel.java
// Andrew Davison, ad@fivedots.coe.psu.ac.th, April 2005

/* This JPanel contains the transparent GIF which represents the 'frame' 
   of the application. It includes 'hot spots' which are areas where the 
   user can click to quit and iconify the application. Other kinds of 
   hot spots can be added.

   When an appl. is being developed, it's useful to:
       * switch off the hot spots by setting usingHotSpots == false.
         switch them on again once they've been set up in
         initHotSpots() and processHotSpots();
         
       * uncomment the (x,y) println in setMouseListening() so
         the coordinate of a mouse press is printed. This println
         can be used to find the location of things in the background
         image, such as the quit/iconify 'button's, or to plan
         where the GUI components should be placed in GUIPanel.

  Changes 16th April 2005
     * Added an isIconify() method, and used it in mouseDragged() to 
       prevent mouse drag processing when the appl. is iconified.
       Thanks to Andres Vera <avera@transbank.cl> for bringing the bug 
       to my attention.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;



public class BackGroundPanel extends JPanel
{
  private static final int NUM_HOT_SPOTS = 2;


  private ShapelyImagePane top;     // so can move and iconify JFrame

  private int scrWidth, scrHeight;  // screen size
  private int xScr, yScr;           // app. location on screen

  private int xPosn, yPosn;         // (x,y) inside appl. when mouse pressed
  private boolean isDraggable = false;

  // background image info
  private BufferedImage backIm = null;
  private int imWidth, imHeight;
  private int imTransparency;    // the image's transparency characteristics

  // hot spot information on background image
  private Rectangle[] spotLocs;
  private String[] spotNames;
  private boolean usingHotSpots = true;
         /* Set this to false to switch off hot spots, which is useful
            during the design phase for an appl. */


  public BackGroundPanel(String backFnm, ShapelyImagePane p)
  {
    top = p;

    setOpaque(false);    // invisible JPanel
    setFocusable(false);

    // get screen dimensions
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    scrWidth = d.width;  scrHeight = d.height;

    loadImage(backFnm);
    setBounds(0, 0, imWidth, imHeight);   // JPanel size == image size

    if (usingHotSpots)
      initHotSpots();

    setMouseListening();
    setMouseMotionListening();
  }  // end of BackGroundPanel()



  private void loadImage(String fnm)
  // fnm contains the transparent GIF used as the appl.'s background
  {
    try {
      backIm =  ImageIO.read(getClass().getResource(fnm));
    } 
    catch(IOException e) {
      System.out.println("Load Image error for " + fnm + ":\n" + e); 
    }
    imWidth = backIm.getWidth();
    imHeight = backIm.getHeight();
    imTransparency = backIm.getColorModel().getTransparency();
  } // end of loadImage()



  private void initHotSpots()
  /* The numbers here were obtained by noting down the position of
     the 'iconify' and 'quit' images in the background image. They
     will vary depending on the choice of image.
  */
  {
    spotLocs = new Rectangle[NUM_HOT_SPOTS];
    spotLocs[0] = new Rectangle(456, 70, 15, 15);  // quit
    spotLocs[1] = new Rectangle(456, 89, 15, 15);  // iconify

    spotNames = new String[NUM_HOT_SPOTS];
    spotNames[0] = new String("quit"); 
    spotNames[1] = new String("iconify");
       // spotNames[] is used for debugging purposes
  }  // end of initHotSpots()


  private void setMouseListening()
  {
    addMouseListener(new MouseAdapter() 
    {
      public void mousePressed(MouseEvent e) 
      { Point p = getLocationOnScreen();
        xScr = p.x; yScr = p.y;               // store screen coordinates
        xPosn = e.getX(); yPosn = e.getY();   // get JPanel coordinates

        // System.out.println("(x,y): (" + xPosn + ", " + yPosn + ")");
               /* You can use this println for deciding where GUI components 
                  should be placed in GUIPanel. */  

        if (usingHotSpots)
          processHotSpots(xPosn, yPosn);

        isDraggable = !isTransparent(xPosn,yPosn);
         // isDraggable is true only if the user clicked in an opaque area
      }
     });
  }  // end of setMouseListening()


  private void setMouseMotionListening()
  {
    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) 
      { 
        // System.out.println( "mouseDragged in iconify: " + isIconified());
        if (isDraggable && !isIconified()) {    // extended test
          xScr += (e.getX() - xPosn);   // add change in posn to the screen coords
          yScr += (e.getY() - yPosn);
          top.moveFrame(xScr, yScr);    // move to the new screen position
        }
       }
     });
  }  // end of setMouseMotionListening()



  private void processHotSpots(int xPosn, int yPosn)
  // determine if the mouse click was in a hot spot, and respond
  {
    int i;
    for(i=0; i < NUM_HOT_SPOTS; i++)
      if (spotLocs[i].contains(xPosn, yPosn))
        break;

    // System.out.println("Clicked on hot spot: " + spotNames[i]);
        // this println is useful for debugging the hotspots 

    if (i < NUM_HOT_SPOTS) {    // clicked in a hot spot
      switch (i) {
        case 0: System.exit(0); break;
        case 1: top.iconify(); break;
        default: System.out.println("Unknown hot spot: " + i); break;
      }
    }
  }  // end of processHotSpots()


  private boolean isTransparent(int x, int y)
  // is (x,y) in a transparent part of the background image?
  {
    if (backIm == null)
      return true;   // since no image!

    if (imTransparency == Transparency.OPAQUE)
      return false;   // since none of the image is transparent

    // range checking for (x,y)
    if ((x > imWidth) || (y > imHeight))
      return true;   // since beyond extent of image

    int pixel = backIm.getRGB(x,y);
    int alphaVal = (pixel >> 24) & 255;    // extract alpha value from the pixel
    if (alphaVal == 0)
      return true;
    else
      return false;
  }  // end of isTransparent()


  private boolean isIconified()
  // determine if the appl. is iconified
  {
     return ((top.getExtendedState() & JFrame.ICONIFIED) == JFrame.ICONIFIED); 
  }  // end of isIconified()


  protected void paintComponent(Graphics g) 
  // draw the background image
  { super.paintComponent(g);
    g.drawImage(backIm, 0, 0, null);
  } // end of paintComponent();


  public int getWidth()
  {  return imWidth;  }

  public int getHeight()
  {  return imHeight;  }


}  // end of BackGroundPanel
