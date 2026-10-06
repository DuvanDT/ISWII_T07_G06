package test.java;

import builder.TrueFalseQuestionBuilder;
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
 * Pruebas unitarias para TrueFalseQuestionBuilder.
 */
public class TrueFalseQuestionBuilderTest {

    private TrueFalseQuestionBuilder builder;

    @Before
    public void setUp() {
        builder = new TrueFalseQuestionBuilder();
    }

    /**
     * Prueba: Construcción correcta con respuesta Verdadero.
     */
    @Test
    public void testConstruccionCorrectaVerdadero() {
        builder.buildStatement("El patrón Builder separa la construcción de un objeto complejo de su representación.");
        builder.buildDifficulty(DifficultyLevel.FACIL);
        builder.buildCategory("Patrones de Diseño");
        builder.buildAuthor("Docente Unicauca");
        builder.buildCorrectAnswer("Verdadero");

        Question question = builder.build();

        assertNotNull(question);
        assertEquals(QuestionType.TRUE_FALSE, question.getQuestionType());
        assertEquals("Verdadero", question.getCorrectAnswer());
        assertEquals("Debe tener exactamente 2 opciones", 2, question.getOptions().size());
        assertTrue("La opción Verdadero debe estar marcada como correcta",
                question.getOptionByIdentifier("V").isCorrect());
        assertFalse("La opción Falso debe estar marcada como incorrecta",
                question.getOptionByIdentifier("F").isCorrect());
    }

    /**
     * Prueba: Construcción correcta con respuesta Falso usando booleano.
     */
    @Test
    public void testConstruccionCorrectaFalso() {
        builder.buildStatement("El patrón Builder solo permite construir un único tipo de producto sin variación.");
        builder.buildDifficulty(DifficultyLevel.MEDIO);
        builder.buildCorrectAnswer(false);

        Question question = builder.build();

        assertNotNull(question);
        assertEquals("Falso", question.getCorrectAnswer());
        assertTrue("La opción Falso debe estar marcada como correcta",
                question.getOptionByIdentifier("F").isCorrect());
        assertFalse("La opción Verdadero debe estar marcada como incorrecta",
                question.getOptionByIdentifier("V").isCorrect());
    }

    /**
     * Prueba: Validación de campos obligatorios (sin enunciado debe fallar).
     */
    @Test(expected = IllegalStateException.class)
    public void testValidacionEnunciadoObligatorio() {
        builder.buildCorrectAnswer("Verdadero");
        builder.build(); // Debe lanzar IllegalStateException
    }

    /**
     * Prueba: Validación de enunciado vacío.
     */
    @Test(expected = IllegalStateException.class)
    public void testValidacionEnunciadoVacio() {
        builder.buildStatement("   ");
        builder.buildCorrectAnswer("Verdadero");
        builder.build(); // Debe lanzar IllegalStateException
    }

    /**
     * Prueba: Validación de respuesta requerida (no puede construirse sin indicar si es V o F).
     */
    @Test(expected = IllegalStateException.class)
    public void testValidacionSinRespuestaCorrecta() {
        builder.buildStatement("Una pregunta sin indicar si es verdadera o falsa");
        builder.build(); // Debe fallar
    }

    /**
     * Prueba: Validación de respuesta inválida (no puede ser un texto arbitrario).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidacionRespuestaInvalida() {
        builder.buildStatement("Enunciado de prueba");
        builder.buildCorrectAnswer("Tal vez"); // No es ni Verdadero ni Falso
    }

    /**
     * Prueba: Verificación de estructura fija de dos opciones.
     */
    @Test
    public void testTieneExactamenteDosOpciones() {
        builder.buildStatement("Pregunta sobre Builder");
        builder.buildCorrectAnswer("V");

        Question question = builder.build();
        assertEquals(2, question.getOptions().size());
        assertEquals("V", question.getOptions().get(0).getIdentifier());
        assertEquals("F", question.getOptions().get(1).getIdentifier());
    }

    public static void main(String[] args) {
        System.out.println("=== Ejecutando TrueFalseQuestionBuilderTest ===");
        Result result = JUnitCore.runClasses(TrueFalseQuestionBuilderTest.class);
        for (Failure failure : result.getFailures()) {
            System.err.println("FALLO: " + failure.toString());
        }
        System.out.println("Resultado: " + (result.wasSuccessful() ? "EXITOSO (Todas las pruebas pasaron)" : "FALLIDO")
                + " | Total pruebas: " + result.getRunCount()
                + " | Fallos: " + result.getFailureCount());
    }
}
