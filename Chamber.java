/**
 * La clase Chamber representa una cámara en un contenedor que puede contener partículas.
 * La cámara puede estar en el lado izquierdo o derecho del contenedor y puede contener partículas rojas y azules.
 */
public class Chamber {

    private int particlesInside; 
    private int blueParticles;   
    private int redParticles;    
    private Boolean side;        //  (false para izquierda, true para derecha).
    private int w;               
    private int h;               
    private Boolean isVisible;   
    private Rectangle wall;      // Rectángulo que representa la pared de la cámara.

    /**
     * Crea una cámara con un ancho, altura y lado específicos.
     *
     * @param w El ancho de la cámara.
     * @param h La altura de la cámara.
     * @param s El lado de la cámara (false para izquierda, true para derecha).
     */
    public Chamber(int w, int h, Boolean s) {
        particlesInside = 0;
        blueParticles = 0;
        redParticles = 0;
        side = s;
        if (side) {
            wall = new Rectangle(1, 0);
            wall.changeSize(w - 2, h);
        } else {
            wall = new Rectangle(w + 2, 0);
            wall.changeSize(w - 2, h);
        }
        wall.changeColor("blanco");
        makeVisible();
        isVisible = true;
    }

    /**
     * Añade una partícula a la cámara.
     *
     * @param isRed Indica si la partícula es roja (true) o azul (false).
     */
    public void addParticle(boolean isRed) {
        if (isRed) {
            redParticles += 1;
        } else {
            blueParticles += 1;
        }
        particlesInside += 1;
    }

    /**
     * Elimina una partícula de la cámara.
     *
     * @param isRed Indica si la partícula es roja (true) o azul (false).
     */
    public void delParticle(boolean isRed) {
        if (isRed) {
            redParticles -= 1;
        } else {
            blueParticles -= 1;
        }
        particlesInside -= 1;
    }

    /**
     * Hace visible la cámara.
     */
    public void makeVisible() {
        wall.makeVisible();
    }

    /**
     * Hace invisible la cámara.
     */
    public void makeInvisible() {
        wall.makeVisible();
    }

    /**
     * Devuelve el número de partículas azules en la cámara.
     *
     * @return El número de partículas azules.
     */
    public int getBlueParticles() {
        return blueParticles;
    }

    /**
     * Devuelve el número de partículas rojas en la cámara.
     *
     * @return El número de partículas rojas.
     */
    public int getRedParticles() {
        return redParticles;
    }

    /**
     * Elimina todas las partículas de la cámara y la hace invisible.
     */
    public void delete() {
        particlesInside = 0;
        blueParticles = 0;
        redParticles = 0;
        makeInvisible();
    }
}