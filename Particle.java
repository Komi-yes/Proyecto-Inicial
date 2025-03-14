import java.util.ArrayList;

/**
 * La clase Particle representa una partícula en el contenedor.
 * La partícula puede ser roja o azul y tiene una posición, velocidad y dirección.
 */
public class Particle {

    private String baseColor;    
    private String color;        
    private int px;              
    private int py;              
    private int vx;              
    private int vy;              
    private Boolean side;        // Indica el lado de la partícula (false para izquierda, true para derecha).
    public Boolean isVisible;    
    private Circle bigCircle;    
    private Circle smallCircle;  
    private boolean isRed;       
    private int w;               
    private int h;         
    private ArrayList<ArrayList<Integer>> cycle;      

    /**
     * Crea una partícula con un color, posición, velocidad y tamaño específicos.
     *
     * @param color El color de la partícula.
     * @param red Indica si la partícula es roja (true) o azul (false).
     * @param posx La posición x de la partícula.
     * @param posy La posición y de la partícula.
     * @param velx La velocidad en el eje x.
     * @param vely La velocidad en el eje y.
     * @param width El ancho del contenedor.
     * @param height La altura del contenedor.
     */
    public Particle(String color, Boolean red, int posx, int posy, int velx, int vely, int width, int height) {
        w = width;
        h = height;
        isRed = red;
        bigCircle = new Circle();
        bigCircle.changeSize(10);
        if (isRed) {
            bigCircle.changeColor("rojo");
        } else {
            bigCircle.changeColor("azul");
        }
        smallCircle = new Circle();
        smallCircle.changeColor(color);
        px = posx;
        py = posy;
        side = (px > 0) ? true : false;
        bigCircle.moveTo(px + w, h - py);
        smallCircle.moveTo(px + w, h - py);
        vx = velx;
        vy = vely;
        cycle = cycleCalculator(); 
        makeVisible();
        isVisible = true;
    }

    public ArrayList<ArrayList<Integer>> cycleCalculator(){
        ArrayList<ArrayList<Integer>> cycleCalculation = new ArrayList<>();
        int posXCal = px;
        int posYCal = py;
        int velXCal = vx;
        int velYCal = vy;

        while(!cycleCalculation.contains({posXCal, posYCal, velXCal, velYCal})){
            cycleCalculation.add({posXCal, posYCal, velXCal, velYCal});
            ArrayList<Integer> newState = bounceCalculation(posXCal+velXCal, posYCal+velYCal, velXCal, velYCal);
            posXCal = newState.get(0);
            posYCal = newState.get(1);
            velXCal = newState.get(2);
            velYCal = newState.get(3);
        }
    }

    public ArrayList<Integer> bounceCalculation(posXCal, posYCal, velXCal, velYCal){
        int xLeft;
        int yLeft;
         if (side) {
            while (posXCal > w || posXCal < 0 || newpy > h || newpy < 0) {
                if (posXCal > w) {
                    xLeft = posXCal - w;
                    posXCal = w - xLeft;
                    velXCal = -velXCal;
                } else if (posXCal < 0) {
                    xLeft = -posXCal;
                    posXCal = xLeft;
                    velXCal = -velXCal;
                } else if (posYCal > h) {
                    yLeft = posYCal - h;
                    posYCal = h - yLeft;
                    velYCal = -velYCal;
                } else if (posYCal < 0) {
                    yLeft = -posYCal;
                    posYCal = yLeft;
                    velYCal = -velYCal;
                }
            }
        } else {
            while (posXCal < -w || posXCal > 0 || newpy > h || newpy < 0) {
                if (posXCal < -w) {
                    xLeft = posXCal + w;
                    posXCal = -w - xLeft;
                    velXCal = -velXCal;
                } else if (posXCal > 0) {
                    xLeft = -posXCal;
                    posXCal = xLeft;
                    velXCal = -velXCal;
                } else if (posYCal > h) {
                    yLeft = posYCal - h;
                    posYCal = h - yLeft;
                    velYCal = -velYCal;
                } else if (posYCal < 0) {
                    yLeft = -posYCal;
                    posYCal = yLeft;
                    velYCal = -velYCal;
                }
            }
        }
        return {posXCal, posYCal, velXCal, velYCal};
    }

    public ArrayList<Integer> bounce(int newpx, int newpy) {
        int xLeft;
        int yLeft;
        if (side) {
            while (newpx > w || newpx < 0 || newpy > h || newpy < 0) {
                if (newpx > w) {
                    xLeft = newpx - w;
                    newpx = w - xLeft;
                    changeDirection("derecha");
                } else if (newpx < 0) {
                    xLeft = -newpx;
                    newpx = xLeft;
                    changeDirection("izquierda");
                } else if (newpy > h) {
                    yLeft = newpy - h;
                    newpy = h - yLeft;
                    changeDirection("arriba");
                } else if (newpy < 0) {
                    yLeft = -newpy;
                    newpy = yLeft;
                    changeDirection("abajo");
                }
            }
        } else {
            while (newpx < -w || newpx > 0 || newpy > h || newpy < 0) {
                if (newpx < -w) {
                    xLeft = newpx + w;
                    newpx = -w - xLeft;
                    changeDirection("izquierda");
                } else if (newpx > 0) {
                    xLeft = -newpx;
                    newpx = xLeft;
                    changeDirection("derecha");
                } else if (newpy > h) {
                    yLeft = newpy - h;
                    newpy = h - yLeft;
                    changeDirection("arriba");
                } else if (newpy < 0) {
                    yLeft = -newpy;
                    newpy = yLeft;
                    changeDirection("abajo");
                }
            }
        }
        ArrayList<Integer> listPositions = new ArrayList<>();
        listPositions.add(newpx);
        listPositions.add(newpy);
        return listPositions;
    }

    /**
     * Mueve la partícula y maneja las colisiones con los demonios.
     *
     * @param openDemons Una lista de demonios abiertos.
     */
    public void move(ArrayList<Integer> openDemons) {
        double yHit;
        int intYHit;
        int newpx;
        int newpy;
        int xLeft;
        int yLeft;
        makeInvisible();
        newpx = px + vx;
        newpy = py + vy;
        yHit = py + vy * (px / vx);
        intYHit = (int) yHit;
        if ((openDemons.contains(intYHit)) && (yHit % intYHit == 0)) {
            if (side) {
                side = false;
                while (newpx < -w || newpx > 0 || newpy > h || newpy < 0) {
                    if (newpx < -w) {
                        xLeft = newpx + w;
                        newpx = -w - xLeft;
                        changeDirection("izquierda");
                    } else if (newpy > h) {
                        yLeft = newpy - h;
                        newpy = h - yLeft;
                        changeDirection("arriba");
                    } else if (newpy < 0) {
                        yLeft = -newpy;
                        newpy = yLeft;
                        changeDirection("abajo");
                    }
                }
            } else {
                side = true;
                while (newpx > w || newpx < 0 || newpy > h || newpy < 0) {
                    if (newpx > w) {
                        xLeft = newpx - w;
                        newpx = w - xLeft;
                        changeDirection("derecha");
                    } else if (newpy > h) {
                        yLeft = newpy - h;
                        newpy = h - yLeft;
                        changeDirection("arriba");
                    } else if (newpy < 0) {
                        yLeft = -newpy;
                        newpy = yLeft;
                        changeDirection("abajo");
                    }
                }
            }
        } else {
            ArrayList<Integer> listPositions = bounce(newpx, newpy);
            newpx = listPositions.get(0);
            newpy = listPositions.get(1);
        }

        px = newpx;
        py = newpy;
        smallCircle.moveTo(px + w, h - py);
        bigCircle.moveTo(px + w, h - py);
        makeVisible();
    }

    /**
     * Predice el movimiento de la partícula.
     *
     * @return La partícula después de la predicción de movimiento.
     */
    public Particle movePrediction() {
        int newpx;
        int newpy;
        int xLeft;
        int yLeft;
        newpx = px + vx;
        newpy = py + vy;
        makeInvisible();
        if (side) {
            while (newpx > w || newpx < 0 || newpy > h || newpy < 0) {
                if (newpx > w) {
                    xLeft = newpx - w;
                    newpx = w - xLeft;
                    changeDirection("derecha");
                } else if (newpx < 0) {
                    return this;
                } else if (newpy > h) {
                    yLeft = newpy - h;
                    newpy = h - yLeft;
                    changeDirection("arriba");
                } else if (newpy < 0) {
                    yLeft = -newpy;
                    newpy = yLeft;
                    changeDirection("abajo");
                }
            }
        } else {
            while (newpx < -w || newpx > 0 || newpy > h || newpy < 0) {
                if (newpx < -w) {
                    xLeft = newpx + w;
                    newpx = -w - xLeft;
                    changeDirection("izquierda");
                } else if (newpx > 0) {
                    return this;
                } else if (newpy > h) {
                    yLeft = newpy - h;
                    newpy = h - yLeft;
                    changeDirection("arriba");
                } else if (newpy < 0) {
                    yLeft = -newpy;
                    newpy = yLeft;
                    changeDirection("abajo");
                }
            }
        }
        px = newpx;
        py = newpy;
        smallCircle.moveTo(px + w, h - py);
        bigCircle.moveTo(px + w, h - py);
        makeVisible();
        return null;
    }

    /**
     * Cambia la dirección de la partícula al chocar con una pared.
     *
     * @param wall La pared con la que chocó la partícula.
     */
    private void changeDirection(String wall) {
        if (wall.equals("derecha") || wall.equals("izquierda")) {
            vx = -vx;
        } else if (wall.equals("arriba") || wall.equals("abajo")) {
            vy = -vy;
        }
    }

    /**
     * Hace visible la partícula.
     */
    public void makeVisible() {
        bigCircle.makeVisible();
        smallCircle.makeVisible();
        isVisible = true;
    }

    /**
     * Hace invisible la partícula.
     */
    public void makeInvisible() {
        smallCircle.makeInvisible();
        bigCircle.makeInvisible();
        isVisible = false;
    }

    /**
     * Devuelve la posición x de la partícula.
     *
     * @return La posición x de la partícula.
     */
    public int getPx() {
        return px;
    }

    /**
     * Devuelve la posición y de la partícula.
     *
     * @return La posición y de la partícula.
     */
    public int getPy() {
        return py;
    }

    /**
     * Devuelve la velocidad en el eje x de la partícula.
     *
     * @return La velocidad en el eje x de la partícula.
     */
    public int getVx() {
        return vx;
    }

    /**
     * Devuelve la velocidad en el eje y de la partícula.
     *
     * @return La velocidad en el eje y de la partícula.
     */
    public int getVy() {
        return vy;
    }

    /**
     * Devuelve si la partícula es roja.
     *
     * @return true si la partícula es roja, false en caso contrario.
     */
    public boolean getIsRed() {
        return isRed;
    }

    public boolean getSide(){
        return side;
    }

    /**
     * Elimina la partícula y la hace invisible.
     */
    public void delete() {
        smallCircle.makeInvisible();
        bigCircle.makeInvisible();
    }

}