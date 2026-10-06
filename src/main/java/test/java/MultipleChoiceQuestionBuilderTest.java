package test.java;

import builder.MultipleChoiceQuestionBuilder;
import domain.DifficultyLevel;
import domain.Question;
import domain.QuestionType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.*;

/**
 * Pruebas unitarias para MultipleChoiceQuestionBuilder.
 */
public class MultipleChoiceQuestionBuilderTest {

    private MultipleChoiceQuestionBuilder builder;

    @Before
    public void setUp() {
        builder = new MultipleChoiceQuestionBuilder();
    }

    /**
     * Prueba: Construcción correcta de una pregunta válida de selección múltiple.
     */
    @Test
    public void testConstruccionCorrecta() {
        builder.buildStatement("¿Cuál patrón de diseño creacional permite construir objetos complejos paso a paso?");
        builder.buildDifficulty(DifficultyLevel.MEDIO);
        builder.buildCategory("Patrones de Diseño");
        builder.buildAuthor("Profesor LISW-II");
        builder.buildJustification("El patrón Builder separa la construcción de un objeto de su representación.");
        builder.buildSource("Design Patterns - GoF");

        builder.addOption("A", "Factory Method", false);
        builder.addOption("B", "Builder", true);
        builder.addOption("C", "Singleton", false);
        builder.addOption("D", "Prototype", false);

        Question question = builder.build();

        assertNotNull("La pregunta construida no debe ser nula", question);
        assertEquals("El tipo debe ser MULTIPLE_CHOICE", QuestionType.MULTIPLE_CHOICE, question.getQuestionType());
        assertEquals("La dificultad debe coincidir", DifficultyLevel.MEDIO, question.getDifficulty());
        assertEquals("La categoría debe coincidir", "Patrones de Diseño", question.getCategory());
        assertEquals("El autor debe coincidir", "Profesor LISW-II", question.getAuthor());
        assertEquals("Debe tener 4 opciones", 4, question.getOptions().size());
        assertTrue("La respuesta correcta debe contener el identificador B", question.getCorrectAnswer().contains("B"));
    }

    /**
     * Prueba: Validación de campos obligatorios (sin enunciado debe fallar).
     */
    @Test(expected = IllegalStateException.class)
    public void testValidacionEnunciadoObligatorioNulo() {
        builder.addOption("A", "Opción 1", true);
        builder.addOption("B", "Opción 2", false);
        builder.build(); // Debe lanzar IllegalStateException
    }

    /**
     * Prueba: Validación de enunciado vacío.
     */
    @Test(expected = IllegalStateException.class)
    public void testValidacionEnunciadoVacio() {
        builder.buildStatement("   ");
        builder.addOption("A", "Opción 1", true);
        builder.addOption("B", "Opción 2", false);
        builder.build(); // Debe lanzar IllegalStateException
    }

    /**
     * Prueba: Validación específica por tipo (debe tener al menos dos opciones).
     */
    @Test(expected = IllegalStateException.class)
    public void testValidacionOpcionesRequeridasMenorADos() {
        builder.buildStatement("Pregunta con una sola opción");
        builder.addOption("A", "Única opción", true);
        builder.build(); // Debe fallar porque requiere al menos 2 opciones
    }

    /**
     * Prueba: Validación específica por tipo (sin opciones debe fallar).
     */
    @Test(expected = IllegalStateException.class)
    public void testValidacionSinOpciones() {
        builder.buildStatement("Pregunta sin opciones");
        builder.build(); // Debe fallar
    }

    /**
     * Prueba: Respuestas válidas (una respuesta que no existe entre las opciones debe fallar).
     */
    @Test(expected = IllegalStateException.class)
    public void testRespuestaCorrectaNoExistenteDebeFallar() {
        builder.buildStatement("¿Cuál es la capital del Cauca?");
        builder.addOption("A", "Cali", false);
        builder.addOption("B", "Pasto", false);
        builder.buildCorrectAnswer("Z - Popayán"); // "Z" no existe entre las opciones
        builder.build(); // Debe lanzar IllegalStateException
    }

    /**
     * Prueba: Asignación de respuesta correcta existente por identificador.
     */
    @Test
    public void testRespuestaCorrectaExistentePorIdentificador() {
        builder.buildStatement("¿Cuál es la capital del Cauca?");
        builder.addOption("A", "Cali", false);
        builder.addOption("B", "Popayán", false);
        builder.buildCorrectAnswer("B");

        Question question = builder.build();
        assertNotNull(question);
        assertTrue("La respuesta debe estar asociada a la opción B", question.getCorrectAnswer().contains("Popayán"));
        assertTrue("La opción B debe estar marcada como correcta", question.getOptionByIdentifier("B").isCorrect());
    }

    /**
     * Prueba: El método reset reinicia el estado del builder.
     */
    @Test
    public void testResetLimpiaEstado() {
        builder.buildStatement("Pregunta preliminar");
        builder.addOption("A", "Opc 1", true);
        builder.addOption("B", "Opc 2", false);

        builder.reset();

        assertNull("El enunciado debe ser nulo tras el reset", builder.getQuestion().getStatement());
        assertTrue("Las opciones deben estar vacías tras el reset", builder.getQuestion().getOptions().isEmpty());
    }

    public static void main(String[] args) {
        System.out.println("=== Ejecutando MultipleChoiceQuestionBuilderTest ===");
        Result result = JUnitCore.runClasses(MultipleChoiceQuestionBuilderTest.class);
        for (Failure failure : result.getFailures()) {
            System.err.println("FALLO: " + failure.toString());
        }
        System.out.println("Resultado: " + (result.wasSuccessful() ? "EXITOSO (Todas las pruebas pasaron)" : "FALLIDO")
                + " | Total pruebas: " + result.getRunCount()
                + " | Fallos: " + result.getFailureCount());
    }
}
