package com.splashs;


// ShapelyImagePane.java
// Andrew Davison, ad@fivedots.coe.psu.ac.th, November 2004

/* A simple GUI application, which illustrates how to 
create an irregular-shaped window.

The application doesn't have a title bar, instead it's
up to the user to include iconification and quit 'buttons'
in the window's display GIF. 

The application can be moved around by dragging on any 
part of its opaque frame.

The application is an undecorated JFrame with a 3-layer
JLayeredPane component on top. In back-to-front order, the
layers are:

1. ScreenPanel: this JPanel shows a picture of the screen 
_behind_ the JFrame, which is updated as the JFrame moves --
this gives the illusion of transparency.

2. BackgroundPanel: this JPanel contains the transparent GIF
which represents the 'frame' of the application. It includes
'hot spots' which are areas where the user can click to quit
and iconify the application. Other kinds of hot spots can
be added.

3. GUIPanel: this JPanel contains the GUI components for the
application. For this example, the user can click on a 
button to open a JFileChooser to select an image to display
in a scrolling pane.


This approach has a few drawbacks:

1. The updating of the ScreenPanel image is a bit slow, so the
background 'lags' behind a little as the application is moved.

2. If a window is changing behind a 'transparent' part of this 
application, the changes aren't shown. Remember that the transparency
is really just a screenshot. The screenshot is only updated
when this appl. is de-iconified, or brought to the front after
being behind another application. More frequent updates cause
excessive flicking.

3. If you move another application, without clicking on this one,
then the changes on screen will not be reflected in this 
appl's screen background. However, the background will be
updated when focus is returned to this application.

(Problems 2 and 3 are related: this application hasn't any way
of knowing what other applications are doing.)

4. You can't click on a window showing through the 'transparent'
part of this application. If this application is currently 
not in focus, a click on it's 'transparent' part will bring
it to the front, and into focus.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ShapelyImagePane extends JFrame {

    private static String BACK_FNM = "Logo.png";
    // the transparent GIF used by BackgroundPanel
    private static final int FOCUS_INTERVAL = 500;  // 0.5 second
    private int xScr, yScr;            // the window's location on screen
    private long focusGainedTime = 0;
    private boolean justInitialised;
    private ScreenPanel scrPanel;        // for drawing the screen
    private BackGroundPanel backPanel;   // for the background image

    public ShapelyImagePane() {
        super("Carregando....");

        setUndecorated(true);  // no title bar, borders
        setResizable(false);   // cannot be minimized/maximized

        // create the background panel
        backPanel = new BackGroundPanel(BACK_FNM, this);
        int bgWidth = backPanel.getWidth();
        int bgHeight = backPanel.getHeight();

        // center the JFrame on screen, and set its size
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        xScr = (d.width - bgWidth) / 2;
        yScr = (d.height - bgHeight) / 2;
        setLocation(xScr, yScr);
        setSize(bgWidth, bgHeight);    // same size as background image

        // initialise screen panel
        scrPanel = new ScreenPanel(bgWidth, bgHeight, xScr, yScr);


        // put the parts together in layers
        JLayeredPane lPane = new JLayeredPane();
        lPane.add(scrPanel, new Integer(1));
        lPane.add(backPanel, new Integer(2));


        // add the layers to the JFrame
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(lPane, BorderLayout.CENTER);

        justInitialised = true;
        setFocusListening();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    } // end of ShapelyImagePane()

    private void setFocusListening() /* We want to redraw the appl. only when focus switches from
    another, different application to this one, not from one
    GUI component to another in the application.

    We want to refresh as rarely as possible since it's a slow
    operation with a discernable flicker.
     */ {
        addWindowFocusListener(new WindowAdapter() {

            @Override
            public void windowGainedFocus(WindowEvent evt) {
                if (justInitialised) {      // this allows us to ignore the focus
                    justInitialised = false;  // gained at the start
                    return;
                }
                /* Time the focus changes, and ignore ones that occur near to each
                other in time. This avoids an infinite chain of focus events,
                since the call to refresh() triggers a new focus event. */
                long focusTime = System.currentTimeMillis();
                if ((focusTime - focusGainedTime) > FOCUS_INTERVAL) {
                    focusGainedTime = focusTime;
                    Window oppWin = evt.getOppositeWindow();
                    if (oppWin == null) // if focus came from another app
                    {
                        refresh();
                    }
                }
            }
        });
    } // end of setFocusListening

    private void refresh() /* Regenerate the image in the screenPanel by calling its refresh()
    method.

    We must make this application invisible while it takes a snap
    of the screen, since we don't want the appl. to be included in the snap.

    The call to setVisible(true) triggers a new focus event, but since
    it's so soon after the previous one, it's ignored back in
    windowGainedFocus(). That's good or we'd end up with an unending
    series of calls to refresh().
     */ {
        setVisible(false);   // hide ourselves

        try {
            Thread.sleep(30);
        } // this short delay helps to ensure that
        catch (InterruptedException ex) {
        }  // we've really disappeared before...

        scrPanel.refresh(xScr, yScr);   // take a new snap

        setVisible(true);    // show ourselves
        setLocation(xScr, yScr);
        repaint();   // this updates the screenPanel, and anything else
    }  // end of refresh()

    public void moveFrame(int x, int y) /* The frame is moved, which requires a movement of the
    screen image stored in screenPanel. The image is only moved,
    no new snap is generated, which is a slow operation. */ // called by backPanel
    {
        xScr = x;
        yScr = y;
        scrPanel.moveScreen(xScr, yScr);
        setLocation(xScr, yScr);
        repaint();    // this updates the screenPanel, and anything else
    }  // end of moveFrame()

    public void iconify() // called by backPanel to iconify the appl.
    {
        int state = getExtendedState();
        state |= Frame.ICONIFIED;      // set the iconified bit
        setExtendedState(state);       // iconify the frame
    } // end of iconify()

// ---------------------------------
    public static void main(String args[]) {
        new ShapelyImagePane();
    }

    /**
     * Informa o caminho da imagem
     * @param NovoCaminho
     */
    public void setCaminhoLogo(String NovoCaminho){
        if(NovoCaminho != null)
            BACK_FNM = NovoCaminho;
    }
} // end of ShapelyImagePane class


