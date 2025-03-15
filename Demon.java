import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        closed = true;
        isVisible = true;
    }

        public Demon(int distance, int w, int h, boolean cal) {
        d = distance;
        closed = true;
        isVisible = false;
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

    public byte demonCalculation(ArrayList<Particle> particleList){ // retorna -1 = imposible, 0 = cerrado, 1 = abierto
        ArrayList<Integer> particleCross = new ArrayList<>();
        ArrayList<Integer> particleBounce = new ArrayList<>();
        for (Particle p : particleList){
            if ((p.getIsRed() && p.getSide()) || (!p.getIsRed() && !p.getSide())){
                particleCross.add(p.getCycle().size());
            } 
            else {
                particleBounce.add(p.getCycle().size());
            }
        }
        if (!Collections.disjoint(particleCross, particleBounce)){
            return -1;
        }
        else{
            if (Collections.min(particleCross) > Collections.min(particleBounce)) {
                open();
                return 1;
            }
            else{
                return 0;
            }
        }
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