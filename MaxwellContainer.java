import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;

/**
 * La clase MaxwellContainer representa un contenedor que contiene cámaras, demonios, agujeros y partículas.
 * El contenedor gestiona la interacción entre estos elementos y permite simular el movimiento de las partículas.
 */
public class MaxwellContainer {

    private int h;                          
    private int w;                          
    private Boolean isVisible;              
    private int time;                       // Tiempo transcurrido en la simulación.
    private TreeMap<String, Particle> particles; // Mapa de partículas en el contenedor.
    private Rectangle wall;                 // Rectángulo que representa la pared del contenedor.
    private TreeMap<Integer, Demon> demons; 
    private List<Hole> holes;               
    private List<Chamber> chambers;         
    private TreeMap<Integer, String> colorMap; 

    /**
     * Crea un contenedor con una altura y un ancho específicos.
     *
     * @param height La altura del contenedor.
     * @param width El ancho del contenedor.
     */
    public MaxwellContainer(int height, int width) {
        if (width > 200 || height > 200 || width < 0 || height < 0) {
            JOptionPane.showMessageDialog(null, "Medidas de contenedor fuera de rango");
        } else {
            w = width;
            h = height;
            wall = new Rectangle(0, 0);
            wall.changeSize((2 * w) + 1, h + 1);
            time = 0;
            chambers = new ArrayList<>();
            chambers.add(new Chamber(w, h, false));
            chambers.add(new Chamber(w, h, true));
            holes = new ArrayList<>();
            demons = new TreeMap<>();
            particles = new TreeMap<>();
            colorMap = createColorMap();
            makeVisible();
        }
    }

    /**
     * Crea un contenedor con una altura, ancho, demonio, partículas azules, partículas rojas y posiciones iniciales.
     *
     * @param height La altura del contenedor.
     * @param width El ancho del contenedor.
     * @param d La posición del demonio.
     * @param b El número de partículas azules.
     * @param r El número de partículas rojas.
     * @param p Las posiciones iniciales de las partículas.
     */
    public MaxwellContainer(int height, int width, int d, int b, int r, int[][] p) {
        if (height > 200 || width > 200 || width < 0 || height < 0) {
            JOptionPane.showMessageDialog(null, "Medidas de contenedor fuera de rango");
        } else {
            w = width;
            h = height;
            wall = new Rectangle(0, 0);
            wall.changeSize((2 * w) + 1, h + 1);
            time = 0;
            chambers = new ArrayList<>();
            chambers.add(new Chamber(w, h, false));
            chambers.add(new Chamber(w, h, true));
            holes = new ArrayList<>();
            demons = new TreeMap<>();
            if (d < 0 || d > h) {
                JOptionPane.showMessageDialog(null, "Demonio fuera de rango");
            } else {
                demons.put(d, new Demon(d, w, h));
            }
            particles = new TreeMap<>();
            int i;
            colorMap = createColorMap();
            for (i = 0; i < r + b; i++) {
                if (particlesMessages(p[i][0], p[i][1], p[i][2], p[i][3], colorMap.get(i))) {
                    if (i < r) {
                        particles.put(colorMap.get(i), new Particle(colorMap.get(i), true, p[i][0], p[i][1], p[i][2], p[i][3], w, h));
                        if (p[i][0] > 0) {
                            chambers.get(1).addParticle(true);
                        } else {
                            chambers.get(0).addParticle(true);
                        }
                    } else {
                        particles.put(colorMap.get(i), new Particle(colorMap.get(i), false, p[i][0], p[i][1], p[i][2], p[i][3], w, h));
                        if (p[i][0] > 0) {
                            chambers.get(1).addParticle(false);
                        } else {
                            chambers.get(0).addParticle(false);
                        }
                    }
                }
            }
            makeVisible();
        }
    }

    public void addDemon(int d) {
        if (demons.get(d) == null) {
            demons.put(d, new Demon(d, w, h));
        }
    }

    /**
     * Elimina el demonio en la posición especificada.
     *
     * @param d La posición del demonio.
     */
    public void delDemon(int d) {
    if (demons.containsKey(d)){
        demons.get(d).delete();
        demons.remove(d);
    }
}

    /**
     * Añade una partícula al contenedor.
     *
     * @param color El color de la partícula.
     * @param isRed Indica si la partícula es roja (true) o azul (false).
     * @param px La posición x de la partícula.
     * @param py La posición y de la partícula.
     * @param vx La velocidad en el eje x.
     * @param vy La velocidad en el eje y.
     */
    public void addParticle(String color, Boolean isRed, int px, int py, int vx, int vy) {
    if (particlesMessages(px, py, vx, vy, color)){
        if (particles.get(color) == null && colorMap.containsValue(color)){
            particles.put(color,new Particle(color,isRed,px,py,vx,vy,w,h));
            if (isRed){
                if (px > 0){
                    chambers.get(1).addParticle(true);
                } else {
                    chambers.get(0).addParticle(true);
                }
                
            }else{
                if (px > 0){
                    chambers.get(1).addParticle(false);
                } else {
                    chambers.get(0).addParticle(false);
                }
            }
        }
        makeVisible();
    }
    }

    /**
     * Elimina una partícula del contenedor por su color.
     *
     * @param color El color de la partícula a eliminar.
     */
    public void delParticle(String color) {
    if (particles.containsKey(color)){
            Particle p = particles.get(color);
        if (p.getIsRed()){
                if (p.getPx() > 0){
                    chambers.get(1).delParticle(true);
                } else {
                    chambers.get(0).delParticle(true);
                }

            }else{
                if (p.getPx() > 0){
                    chambers.get(1).delParticle(false);
                } else {
                    chambers.get(0).delParticle(false);
                }
            }
        p.delete();
        particles.remove(color);
    }
}
    /**
     * Añade un agujero al contenedor en la posición especificada.
     *
     * @param px La posición x del agujero.
     * @param py La posición y del agujero.
     * @param particles El número máximo de partículas que el agujero puede consumir.
     */
    public void addHole(int px, int py, int particles) {
        if (px < -w || px > w || py > h || py < 0) {
            JOptionPane.showMessageDialog(null, "Agujero fuera de rango");
        } else {
            holes.add(new Hole(px, py, particles));
        }
    }

    /**
     * Inicia la simulación durante un número específico de ticks.
     *
     * @param ticks El número de ticks para la simulación.
     */
    public void start(int ticks) {
        int tick = 0;
        while (tick < ticks) {
            ArrayList<Particle> particlesPredictions = new ArrayList<>();
            ArrayList<Integer> openDemons;

            for (Particle p : particles.values()) {
                Particle particle = p.movePrediction();
                particlesPredictions.add(particle);
            }

            particlesPredictions.removeIf(elemento -> elemento == null);
            openDemons = askDemon(particlesPredictions);

            for (Particle p : particlesPredictions) {
                p.move(openDemons);
            }
            tick += 1;
            time += 1;
        }
    }

    /**
     * Reproduce la simulación.
     */
    public void play() {
    }

    /**
     * Muestra información sobre el estado del contenedor.
     */
    public void consult() {
        boolean rightSize = false;
        boolean demonsRightPosition = true;
        boolean rightAmountOfParticles = false;
        int i;
        int[] demons;
        int redParticles = 0;
        int blueParticles = 0;
        int[][] holes;
        if (getWidth() >= 2 && getWidth() <= 200 && getHeight() >= 2 && getHeight() <= 200) {
            rightSize = true;
        }
        demons = demons();
        for (i = 0; i < demons.length; i++) {
            if (demons[i] < 0 && demons[i] > getHeight()) {
                demonsRightPosition = false;
            }
        }
        for (i = 0; i < 2; i++) {
            redParticles += chambers.get(i).getRedParticles();
            blueParticles += chambers.get(i).getBlueParticles();
        }
        if (redParticles >= 0 && blueParticles >= 0 && redParticles + blueParticles <= 50) {
            rightAmountOfParticles = true;
        }
        holes = holes();
        String message = "El contenedor tiene el tamaño adecuado: " + rightSize + "\n"
                + "Los demonios están en el rango adecuado: " + demonsRightPosition + "\n"
                + "Existe la cantidad correcta de partículas: " + rightAmountOfParticles + "\n"
                + "Cantidad de particulas rojas: " + redParticles + "\n"
                + "Cantidad de particulas azules: " + blueParticles + "\n"
                + "Cantidad de demonios: " + demons.length + "\n"
                + "Cantidad de agujeros: " + holes.length;
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Verifica si se ha alcanzado el objetivo de la simulación.
     *
     * @return true si se ha alcanzado el objetivo, false en caso contrario.
     */
    public boolean isGoal() {
        int leftBlueParticles = chambers.get(0).getBlueParticles();
        int rightRedParticles = chambers.get(1).getRedParticles();
        return leftBlueParticles == 0 && rightRedParticles == 0;
    }

    /**
     * Devuelve las posiciones de los demonios en el contenedor.
     *
     * @return Un arreglo con las posiciones de los demonios.
     */
    public int[] demons() {
        int[] ds = new int[demons.size()];
        int i = 0;
        for (int key : demons.keySet()) {
            ds[i++] = key;
        }
        return ds;
    }

    /**
     * Devuelve las posiciones y velocidades de las partículas en el contenedor.
     *
     * @return Un arreglo bidimensional con las posiciones y velocidades de las partículas.
     */
    public int[][] particles() {
        int[][] ps = new int[particles.size()][4];
        int i = 0;
        for (Particle p : particles.values()) {
            ps[i][0] = p.getPx();
            ps[i][1] = p.getPy();
            ps[i][2] = p.getVx();
            ps[i][3] = p.getVy();
            i++;
        }
        return ps;
    }

    /**
     * Devuelve las posiciones y límites de los agujeros en el contenedor.
     *
     * @return Un arreglo bidimensional con las posiciones y límites de los agujeros.
     */
    public int[][] holes() {
        int[][] hs = new int[holes.size()][3];
        int i = 0;
        for (Hole h : holes) {
            hs[i][0] = h.getPx();
            hs[i][1] = h.getPy();
            hs[i][2] = h.getParticlesLeft();
            i++;
        }
        return hs;
    }

    /**
     * Hace visible el contenedor y todos sus elementos.
     */
    public void makeVisible() {
        wall.makeVisible();
        for (Chamber c : chambers) {
            c.makeVisible();
        }
        for (Hole h : holes) {
            h.makeVisible();
        }
        for (Demon d : demons.values()) {
            d.makeVisible();
        }
        for (Particle p : particles.values()) {
            p.makeVisible();
        }
    }

    /**
     * Hace invisible el contenedor y todos sus elementos.
     */
    public void makeInvisible() {
        for (Particle p : particles.values()) {
            p.makeInvisible();
        }
        for (Demon d : demons.values()) {
            d.makeInvisible();
        }
        for (Hole h : holes) {
            h.makeInvisible();
        }
        for (Chamber c : chambers) {
            c.makeInvisible();
        }
        wall.makeInvisible();
    }

    /**
     * Finaliza la simulación y elimina todos los elementos del contenedor.
     */
    public void finish() {
        for (Particle p : particles.values()) {
            p.delete();
        }
        particles.clear();
        for (Demon d : demons.values()) {
            d.delete();
        }
        demons.clear();
        for (Hole h : holes) {
            h.delete();
        }
        holes.clear();
        for (Chamber c : chambers) {
            c.delete();
        }
        chambers.clear();
        wall.makeInvisible();
    }

    /**
     * Verifica si el contenedor está en un estado válido (no implementado).
     *
     * @return true si el contenedor está en un estado válido, false en caso contrario.
     */
    public Boolean ok() {
        return true;
    }

    /**
     * Devuelve la altura del contenedor.
     *
     * @return La altura del contenedor.
     */
    public int getHeight() {
        return h;
    }

    /**
     * Devuelve el ancho del contenedor.
     *
     * @return El ancho del contenedor.
     */
    public int getWidth() {
        return w;
    }
    
    /**
     * Devuelve el contenedor en la posicion dada.
     *
     * @return Contenedor.
     */
    public Chamber getChamber(int i){
        return chambers.get(i);
    }

    /**
     * Crea un mapa de colores para las partículas.
     *
     * @return Un TreeMap que asocia índices con nombres de colores.
     */
    private TreeMap<Integer, String> createColorMap() {
        TreeMap<Integer, String> colorMap = new TreeMap<>();
        colorMap.put(0, "lila");
        colorMap.put(1, "verde");
        colorMap.put(2, "azul");
        colorMap.put(3, "amarillo");
        colorMap.put(4, "magenta");
        colorMap.put(5, "cian");
        colorMap.put(6, "naranja");
        colorMap.put(7, "morado");
        colorMap.put(8, "rosa");
        colorMap.put(9, "marron");
        colorMap.put(10, "lima");
        colorMap.put(11, "turquesa");
        colorMap.put(12, "salmon");
        colorMap.put(13, "borgona");
        colorMap.put(14, "oliva");
        colorMap.put(15, "oro");
        colorMap.put(16, "plata");
        colorMap.put(17, "beige");
        colorMap.put(18, "menta");
        colorMap.put(19, "lavanda");
        colorMap.put(20, "coral");
        colorMap.put(21, "vainilla");
        colorMap.put(22, "azul marino");
        colorMap.put(23, "gris");
        colorMap.put(24, "fucsia");
        colorMap.put(25, "verde bosque");
        colorMap.put(26, "guinda");
        colorMap.put(27, "rojo");
        colorMap.put(28, "azul celeste");
        colorMap.put(29, "azul acero");
        colorMap.put(30, "verde pastel");
        colorMap.put(31, "chocolate");
        colorMap.put(32, "esmeralda");
        colorMap.put(33, "azul royal");
        colorMap.put(34, "mostaza");
        colorMap.put(35, "frambuesa");
        colorMap.put(36, "caramelo");
        colorMap.put(37, "perla");
        colorMap.put(38, "caqui");
        colorMap.put(39, "cobre");
        colorMap.put(40, "verde musgo");
        colorMap.put(41, "azul electrico");
        return colorMap;
    }

    /**
     * Verifica si una partícula está dentro del rango del contenedor.
     *
     * @param px La posición x de la partícula.
     * @param py La posición y de la partícula.
     * @param vx La velocidad en el eje x.
     * @param vy La velocidad en el eje y.
     * @param color El color de la partícula.
     * @return true si la partícula está dentro del rango, false en caso contrario.
     */
    private boolean particlesMessages(int px, int py, int vx, int vy, String color) {
        if (px < -w || px > w || py < 0 || py > h) {
            JOptionPane.showMessageDialog(null, "Particula fuera de rango");
            return false;
        }
        if (!colorMap.containsValue(color)) {
            JOptionPane.showMessageDialog(null, "El color no existe");
            return false;
        }
        return true;
    }

    /**
     * Pregunta a los demonios si están abiertos
     *
     * @param particlesPredictions Las predicciones de movimiento de las partículas.
     * @return Una lista de demonios abiertos.
     */
    private ArrayList<Integer> askDemon(ArrayList<Particle> particlesPredictions) {
        ArrayList<Integer> openDemons = new ArrayList<>();
        TreeMap<Integer, ArrayList<Particle>> possibleParticles = new TreeMap<>();
        double yHit;
        int intYHit;
        int pasan = 0;
        int rebotan = 0;
        for (int key : demons.keySet()) {
            possibleParticles.put(key, new ArrayList<>());
        }
        for (Particle p : particlesPredictions){
            yHit = p.getPy() + p.getVy() * (p.getPx() / p.getVx());
            intYHit = (int) yHit;
            if(demons.containsKey(intYHit)){
                possibleParticles.get(intYHit).add(p);
            }
        }

        for (Map.Entry<Integer, ArrayList<Particle>> entry : possibleParticles.entrySet()) {
            ArrayList <Particle> possibleParticlesList = entry.getValue();
            if (possibleParticlesList.size() > 1){
                for(Particle p: possibleParticlesList){
                    if ((particle.getIsRed() && particle.getSide()) || (!particle.getIsRed() && !particle.getSide())){
                        pasan += 1;
                    }
                    else {
                        rebotan += 1;
                    }
                }
                if (pasan > 0 && rebotan > 0){
                    calculocastroso(entry.getValue());
                }
                else if (pasan > 0 && rebotan == 0){
                    demons.get(entry.getKey()).open();
                    openDemons.add(entry.getKey());
                }
            }
            else if (possibleParticlesList.size() = 1){
                Particle particle = possibleParticlesList.get(0);
                if ((particle.getIsRed() && particle.getSide()) || (!particle.getIsRed() && !particle.getSide())){
                    demons.get(entry.getKey()).open();
                    openDemons.add(entry.getKey());
                }
            }
        }
        return openDemons;
    }
}