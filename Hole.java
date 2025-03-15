/**
 * La clase Hole representa un agujero en el contenedor que puede "comer" partículas.
 * El agujero tiene una posición y un límite de partículas que puede consumir.
 */
public class Hole {

    private int px;              
    private int py;              
    private int maxParticles;    // Número máximo de partículas que el agujero puede consumir.
    private int particlesEaten;  // Número de partículas que el agujero ha consumido.
    private Boolean isVisible;   // Indica si el agujero es visible.
    private Circle circle;       // Círculo que representa al agujero.

    /**
     * Crea un agujero en una posición específica con un límite de partículas.
     *
     * @param posx La posición x del agujero.
     * @param posy La posición y del agujero.
     * @param particles El número máximo de partículas que el agujero puede consumir.
     */
    public Hole(int posx, int posy, int particles) {
        circle = new Circle();
        circle.changeColor("negro");
        px = posx;
        py = posy;
        circle.moveTo(px, py);
        maxParticles = particles;
        particlesEaten = 0;
        makeVisible();
        isVisible = true;
    }

    public Hole(int posx, int posy, int particles,boolean cal) {
        px = posx;
        py = posy;
        maxParticles = particles;
        particlesEaten = 0;
        isVisible = false;
    }

    /**
     * Consume una partícula.
     */
    public void eatParticle() {
    }

    /**
     * Hace visible el agujero.
     */
    public void makeVisible() {
        circle.makeVisible();
        isVisible = true;
    }

    /**
     * Hace invisible el agujero.
     */
    public void makeInvisible() {
        circle.makeInvisible();
        isVisible = false;
    }

    /**
     * Devuelve la posición x del agujero.
     *
     * @return La posición x del agujero.
     */
    public int getPx() {
        return px;
    }

    /**
     * Devuelve la posición y del agujero.
     *
     * @return La posición y del agujero.
     */
    public int getPy() {
        return py;
    }

    /**
     * Devuelve el número de partículas que el agujero puede consumir.
     *
     * @return El número de partículas restantes.
     */
    public int getParticlesLeft() {
        return maxParticles - particlesEaten;
    }

    /**
     * Elimina el agujero y lo hace invisible.
     */
    public void delete() {
        maxParticles = 0;
        particlesEaten = 0;
        makeInvisible();
    }
}