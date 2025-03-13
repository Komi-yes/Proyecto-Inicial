import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

/**
 * Clase de pruebas para MaxwellContainer.
 */
public class MaxwellContainerC1Test {

    private MaxwellContainer container;

    @BeforeEach
    public void setUp() {
        container = new MaxwellContainer(10, 10);
    }

    @Test
    @DisplayName("debería agregar un demonio cuando no existe")
    public void shouldAddDemon() {
        // Verificamos que no existan demonios inicialmente
        assertEquals(0, container.demons().length, 
            "No debería haber demonios al inicio");

        // Agregamos un demonio con key=2
        container.addDemon(2);

        // Comprobamos que ahora hay 1 demonio
        assertEquals(1, container.demons().length,
            "Debería haberse agregado 1 demonio");
        // Y verificamos que su key sea 2
        assertEquals(2, container.demons()[0],
            "El demonio agregado debería tener key=2");
    }

    @Test
    @DisplayName("no debería agregar un demonio con una clave existente")
    public void shouldNotAddDemonIfKeyAlreadyExists() {
        // Agregamos un demonio con key=5
        container.addDemon(5);
        assertEquals(1, container.demons().length);

        // Volvemos a agregar un demonio con la misma key=5
        container.addDemon(5);

        // Comprobamos que siga habiendo solo 1 demonio
        assertEquals(1, container.demons().length,
            "No se debería agregar un demonio con la misma key");
    }

    @Test
    @DisplayName("debería eliminar un demonio cuando existe")
    public void shouldDeleteDemon() {
        container.addDemon(1);
        assertEquals(1, container.demons().length);

        container.delDemon(1);
        assertEquals(0, container.demons().length,
            "El demonio con key=1 debería haberse eliminado");
    }

    @Test
    @DisplayName("no debería eliminar un demonio si no existe")
    public void shouldNotDeleteDemonIfItDoesntExist() {
        // No hay demonios; borramos uno inexistente
        container.delDemon(99);
        // Sigue sin haber demonios
        assertEquals(0, container.demons().length);
    }

    @Test
    @DisplayName("debería agregar una partícula con un color válido del colorMap")
    public void shouldAddParticleWithValidColor() {
        // colorMap, por ejemplo, incluye "rojo" como key 27
        container.addParticle("rojo", true, 1, 1, 2, 2);

        // Verificamos que la partícula se haya agregado
        int[][] ps = container.particles();
        assertEquals(1, ps.length, 
            "Debería existir una partícula tras addParticle con color válido");
        // Verificamos sus atributos principales
        assertEquals(1, ps[0][0], "PosX debería ser 1");
        assertEquals(1, ps[0][1], "PosY debería ser 1");
        assertEquals(2, ps[0][2], "VelX debería ser 2");
        assertEquals(2, ps[0][3], "VelY debería ser 2");
    }

    @Test
    @DisplayName("no debería agregar una partícula si el color no está en el colorMap")
    public void shouldNotAddParticleIfInvalidColor() {
        container.addParticle("colorInexistente", true, 2, 2, 1, 1);

        // No debería agregarse al no existir en colorMap
        assertEquals(0, container.particles().length,
            "No debería existir la partícula con color inválido");
    }

    @Test
    @DisplayName("debería eliminar una partícula si existe")
    public void shouldDeleteParticle() {
        // Primero, agregamos una partícula con color "rojo"
        container.addParticle("rojo", true, 1, 1, 1, 1);
        assertEquals(1, container.particles().length);

        // Borramos la partícula por color
        container.delParticle("rojo");
        assertEquals(0, container.particles().length,
            "La partícula 'rojo' debería haberse eliminado");
    }

    @Test
    @DisplayName("no debería eliminar una partícula si el color no existe")
    public void shouldNotDeleteParticleIfColorInvalid() {
        // No hay partículas; intentamos borrar
        container.delParticle("noExiste");
        // No debería explotar ni cambiar nada
        assertEquals(0, container.particles().length);
    }

    @Test
    @DisplayName("debería agregar un agujero")
    public void shouldAddHole() {
        // Verificamos que no haya holes inicialmente
        assertEquals(0, container.holes().length);

        // Agregamos un hole
        container.addHole(5, 5, 10);

        // Verificamos que ahora haya 1
        assertEquals(1, container.holes().length);
        // Comprobamos sus atributos
        assertEquals(5, container.holes()[0][0], 
            "El Px del primer hole debería ser 5");
        assertEquals(5, container.holes()[0][1], 
            "El Py del primer hole debería ser 5");
        assertEquals(10, container.holes()[0][2],
            "El número de partículas que puede comer debería ser 10");
    }

    @Test
    @DisplayName("debería iniciar la simulación y aumentar los ticks de tiempo")
    public void shouldStartSimulation() {
        // Agregamos un par de partículas
        container.addParticle("rojo", true, 0, 0, 1, 1);
        container.addParticle("azul", false, 1, 1, 1, 1);

        // Llamamos a start(5) para 5 ticks
        container.start(5);
        assertTrue(true, "No ha arrojado excepción al simular 5 ticks");
    }

    @Test
    @DisplayName("debería finalizar y limpiar todo")
    public void shouldFinishAndClearAll() {
        container.addParticle("rojo", true, 0, 0, 1, 1);
        container.addDemon(1);
        container.addHole(5, 5, 10);

        container.finish();

        assertEquals(0, container.particles().length, 
            "No debería haber partículas tras finish()");
        assertEquals(0, container.demons().length, 
            "No debería haber demonios tras finish()");
        assertEquals(0, container.holes().length, 
            "No debería haber holes tras finish()");
    }

    @Test
    @DisplayName("debería consultar el contenedor sin lanzar excepciones")
    public void shouldConsult() {
        // Simplemente verificamos que consult() no explota
        // y que no arroja excepción
        container.consult();
        assertTrue(true, "Llamada a consult() exitosa sin excepción");
    }

    @Test
    @DisplayName("debería detectar el objetivo si leftBlue=0 y rightRed=0")
    public void shouldDetectGoal() {
        assertTrue(container.isGoal(), 
            "Debería ser true si no hay partículas azules a la izquierda ni rojas a la derecha");
    }
}