import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 * This is a modification of the general purpose Canvas, specially made for
 * the BlueJ "shapes" example. 
 *
 * @author: Bruce Quig
 * @author: Michael Kolling (mik)
 *
 * @version: 1.6 (shapes)
 */
public class Canvas{
    // Note: The implementation of this class (specifically the handling of
    // shape identity and colors) is slightly more complex than necessary. This
    // is done on purpose to keep the interface and instance fields of the
    // shape objects in this project clean and simple for educational purposes.

    private static Canvas canvasSingleton;

    /**
     * Factory method to get the canvas singleton object.
     */
    public static Canvas getCanvas(){
        if(canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Shapes Demo", 400, 200, 
                                         Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    //  ----- instance part -----

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List <Object> objects;
    private HashMap <Object,ShapeDescription> shapes;
    
    /**
     * Create a Canvas.
     * @param title  title to appear in Canvas Frame
     * @param width  the desired width for the canvas
     * @param height  the desired height for the canvas
     * @param bgClour  the desired background colour of the canvas
     */
    private Canvas(String title, int width, int height, Color bgColour){
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
        objects = new ArrayList <Object>();
        shapes = new HashMap <Object,ShapeDescription>();
    }

    /**
     * Set the canvas visibility and brings canvas to the front of screen
     * when made visible. This method can also be used to bring an already
     * visible canvas to the front of other windows.
     * @param visible  boolean value representing the desired visibility of
     * the canvas (true or false) 
     */
    public void setVisible(boolean visible){
        if(graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background colour
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Draw a given shape onto the canvas.
     * @param  referenceObject  an object to define identity for this shape
     * @param  color            the color of the shape
     * @param  shape            the shape object to be drawn on the canvas
     */
     // Note: this is a slightly backwards way of maintaining the shape
     // objects. It is carefully designed to keep the visible shape interfaces
     // in this project clean and simple for educational purposes.
    public void draw(Object referenceObject, String color, Shape shape){
        objects.remove(referenceObject);   // just in case it was already there
        objects.add(referenceObject);      // add at the end
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }
 
    /**
     * Erase a given shape's from the screen.
     * @param  referenceObject  the shape object to be erased 
     */
    public void erase(Object referenceObject){
        objects.remove(referenceObject);   // just in case it was already there
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Set the foreground colour of the Canvas.
     * @param  newColour   the new colour for the foreground of the Canvas 
     */
    public void setForegroundColor(String colorString) {
    if (colorString.equalsIgnoreCase("rojo")) {
        graphic.setColor(new Color(255, 0, 0));       // #FF0000
    } else if (colorString.equalsIgnoreCase("verde")) {
        graphic.setColor(new Color(0, 255, 0));       // #00FF00
    } else if (colorString.equalsIgnoreCase("azul")) {
        graphic.setColor(new Color(0, 0, 255));       // #0000FF
    } else if (colorString.equalsIgnoreCase("amarillo")) {
        graphic.setColor(new Color(255, 255, 0));     // #FFFF00
    } else if (colorString.equalsIgnoreCase("magenta")) {
        graphic.setColor(new Color(255, 0, 255));     // #FF00FF
    } else if (colorString.equalsIgnoreCase("cian")) {
        graphic.setColor(new Color(0, 255, 255));     // #00FFFF
    } else if (colorString.equalsIgnoreCase("naranja")) {
        graphic.setColor(new Color(255, 165, 0));     // #FFA500
    } else if (colorString.equalsIgnoreCase("morado")) {
        graphic.setColor(new Color(128, 0, 128));     // #800080
    } else if (colorString.equalsIgnoreCase("rosa")) {
        graphic.setColor(new Color(255, 192, 203));   // #FFC0CB
    } else if (colorString.equalsIgnoreCase("marron")) {
        graphic.setColor(new Color(165, 42, 42));     // #A52A2A
    } else if (colorString.equalsIgnoreCase("lima")) {
        graphic.setColor(new Color(191, 255, 0));     // #BFFF00
    } else if (colorString.equalsIgnoreCase("turquesa")) {
        graphic.setColor(new Color(64, 224, 208));    // #40E0D0
    } else if (colorString.equalsIgnoreCase("salmon")) {
        graphic.setColor(new Color(250, 128, 114));   // #FA8072
    } else if (colorString.equalsIgnoreCase("borgona")) {
        graphic.setColor(new Color(128, 0, 32));      // #800020
    } else if (colorString.equalsIgnoreCase("oliva")) {
        graphic.setColor(new Color(128, 128, 0));     // #808000
    } else if (colorString.equalsIgnoreCase("oro")) {
        graphic.setColor(new Color(255, 215, 0));     // #FFD700
    } else if (colorString.equalsIgnoreCase("plata")) {
        graphic.setColor(new Color(192, 192, 192));   // #C0C0C0
    } else if (colorString.equalsIgnoreCase("beige")) {
        graphic.setColor(new Color(245, 245, 220));   // #F5F5DC
    } else if (colorString.equalsIgnoreCase("menta")) {
        graphic.setColor(new Color(152, 255, 152));   // #98FF98
    } else if (colorString.equalsIgnoreCase("lavanda")) {
        graphic.setColor(new Color(230, 230, 250));   // #E6E6FA
    } else if (colorString.equalsIgnoreCase("coral")) {
        graphic.setColor(new Color(255, 127, 80));    // #FF7F50
    } else if (colorString.equalsIgnoreCase("vainilla")) {
        graphic.setColor(new Color(243, 229, 171));   // #F3E5AB
    } else if (colorString.equalsIgnoreCase("azul marino")) {
        graphic.setColor(new Color(0, 0, 128));       
    } else if (colorString.equalsIgnoreCase("gris")) {
        graphic.setColor(new Color(128, 128, 128));   
    } else if (colorString.equalsIgnoreCase("fucsia")) {
        graphic.setColor(new Color(255, 0, 255));     
    } else if (colorString.equalsIgnoreCase("verde bosque")) {
        graphic.setColor(new Color(34, 139, 34));     
    } else if (colorString.equalsIgnoreCase("guinda")) {
        graphic.setColor(new Color(128, 0, 0));       
    } else if (colorString.equalsIgnoreCase("lila")) {
        graphic.setColor(new Color(200, 162, 200));   
    } else if (colorString.equalsIgnoreCase("azul celeste")) {
        graphic.setColor(new Color(135, 206, 235));   
    } else if (colorString.equalsIgnoreCase("azul acero")) {
        graphic.setColor(new Color(70, 130, 180));    
    } else if (colorString.equalsIgnoreCase("verde pastel")) {
        graphic.setColor(new Color(119, 221, 119));   
    } else if (colorString.equalsIgnoreCase("chocolate")) {
        graphic.setColor(new Color(210, 105, 30));   
    } else if (colorString.equalsIgnoreCase("esmeralda")) {
        graphic.setColor(new Color(80, 200, 120));    
    } else if (colorString.equalsIgnoreCase("azul royal")) {
        graphic.setColor(new Color(65, 105, 225));    
    } else if (colorString.equalsIgnoreCase("mostaza")) {
        graphic.setColor(new Color(255, 219, 88));    
    } else if (colorString.equalsIgnoreCase("frambuesa")) {
        graphic.setColor(new Color(227, 11, 93));     
    } else if (colorString.equalsIgnoreCase("caramelo")) {
        graphic.setColor(new Color(175, 110, 77));    
    } else if (colorString.equalsIgnoreCase("perla")) {
        graphic.setColor(new Color(226, 223, 210));   
    } else if (colorString.equalsIgnoreCase("caqui")) {
        graphic.setColor(new Color(240, 230, 140));   
    } else if (colorString.equalsIgnoreCase("cobre")) {
        graphic.setColor(new Color(184, 115, 51));    
    } else if (colorString.equalsIgnoreCase("verde musgo")) {
        graphic.setColor(new Color(138, 154, 91));    
    } else if (colorString.equalsIgnoreCase("azul electrico")) {
        graphic.setColor(new Color(125, 249, 255));   
    } else if  (colorString.equals("blanco")){
            graphic.setColor(Color.white);
    } else {
        graphic.setColor(Color.black);
    }
}

    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to specify a small delay which can be
     * used when producing animations.
     * @param  milliseconds  the number 
     */
    public void wait(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e){
            // ignoring exception at the moment
        }
    }

    /**
     * Redraw ell shapes currently on the Canvas.
     */
    private void redraw(){
        erase();
        for(Iterator i=objects.iterator(); i.hasNext(); ) {
                       shapes.get(i.next()).draw(graphic);
        }
        canvas.repaint();
    }
       
    /**
     * Erase the whole canvas. (Does not repaint.)
     */
    private void erase(){
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }


    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel{
        public void paint(Graphics g){
            g.drawImage(canvasImage, 0, 0, null);
        }
    }
    
    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class ShapeDescription{
        private Shape shape;
        private String colorString;

        public ShapeDescription(Shape shape, String color){
            this.shape = shape;
            colorString = color;
        }

        public void draw(Graphics2D graphic){
            setForegroundColor(colorString);
            graphic.draw(shape);
            graphic.fill(shape);
        }
    }

}

//main