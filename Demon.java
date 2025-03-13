/**
 * La clase Demon representa un demonio que puede abrir o cerrar un pasaje entre cámaras.
 * El demonio puede estar visible o invisible y puede estar en un estado abierto o cerrado.
 */
public class Demon {

    private int d;               // Distancia del demonio desde la parte inferior del contenedor.
    private Boolean isVisible;   
    private Boolean closed;      // Indica si el demonio está cerrado (true) o abierto (false).
    private Rectangle rectangle; // Rectángulo que representa al demonio.

    /**
     * Crea un demonio en una posición específica.
     *
     * @param distance La distancia desde la parte inferior del contenedor.
     * @param w El ancho del contenedor.
     * @param h La altura del contenedor.
     */
    public Demon(int distance, int w, int h) {
        d = distance;
        rectangle = new Rectangle(w - 2, h - d);
        rectangle.changeColor("rojo");
        rectangle.changeSize(5, 5);
        closed = false;
        isVisible = true;
    }

    /**
     * Abre el demonio.
     */
    public void open() {
        closed = false;
    }

    /**
     * Cierra el demonio.
     */
    public void close() {
        closed = true;
    }

    /**
     * Verifica la situación de las partículas que colisionan con el demonio.
     *
     * @param collisionParticles Las partículas que colisionan con el demonio.
     * @return Un arreglo de enteros que representa la situación (no implementado).
     */
    public int[] checkSituation(Particle[] collisionParticles) {
        return null;
    }

    /**
     * Verifica si una partícula puede pasar por el demonio.
     *
     * @param particle La partícula a verificar.
     * @return true si la partícula puede pasar, false en caso contrario (no implementado).
     */
    public Boolean checkParticle(Particle particle) {
        return null;
    }

    /**
     * Hace visible al demonio.
     */
    public void makeVisible() {
        rectangle.makeVisible();
        isVisible = true;
    }

    /**
     * Hace invisible al demonio.
     */
    public void makeInvisible() {
        rectangle.makeInvisible();
        isVisible = false;
    }

    /**
     * Cierra y hace invisible al demonio.
     */
    public void delete() {
        close();
        makeInvisible();
    }
}