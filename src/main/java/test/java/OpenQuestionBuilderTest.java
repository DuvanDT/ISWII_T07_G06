package test.java;

import builder.MultipleChoiceQuestionBuilder;
import builder.OpenQuestionBuilder;
import builder.TrueFalseQuestionBuilder;
import director.QuestionDirector;
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
 * Pruebas unitarias para OpenQuestionBuilder y demostración de construcción
 * de diferentes productos mediante el mismo proceso general con QuestionDirector.
 */
public class OpenQuestionBuilderTest {

    private OpenQuestionBuilder builder;

    @Before
    public void setUp() {
        builder = new OpenQuestionBuilder();
    }

    /**
     * Prueba: Construcción correcta de una pregunta abierta.
     */
    @Test
    public void testConstruccionCorrecta() {
        builder.buildStatement("Explique las responsabilidades de la clase Director y de los Builders en el patrón Builder.");
        builder.buildDifficulty(DifficultyLevel.AVANZADO);
        builder.buildCategory("Arquitectura de Software");
        builder.buildAuthor("Ingeniería del Software II");
        builder.buildJustification("Evalúa comprensión conceptual y diseño de roles en patrones creacionales.");
        builder.buildSource("GoF Design Patterns");
        builder.buildCorrectAnswer("El Director define el orden de los pasos; los Builders implementan los pasos concretos.");

        Question question = builder.build();

        assertNotNull(question);
        assertEquals(QuestionType.OPEN, question.getQuestionType());
        assertEquals(DifficultyLevel.AVANZADO, question.getDifficulty());
        assertTrue("La pauta de respuesta debe contener la explicación",
                question.getCorrectAnswer().contains("Director define el orden"));
    }

    /**
     * Prueba: Validación de campos obligatorios (sin enunciado debe fallar).
     */
    @Test(expected = IllegalStateException.class)
    public void testValidacionEnunciadoObligatorio() {
        builder.build(); // Debe fallar sin enunciado
    }

    /**
     * Prueba: Validación de enunciado vacío.
     */
    @Test(expected = IllegalStateException.class)
    public void testValidacionEnunciadoVacio() {
        builder.buildStatement("   ");
        builder.build(); // Debe fallar
    }

    /**
     * Prueba: Una pregunta abierta no debe tener opciones de selección múltiple.
     */
    @Test
    public void testNoRequiereOpciones() {
        builder.buildStatement("Describa las ventajas de usar pruebas unitarias.");
        Question question = builder.build();

        assertNotNull(question);
        assertTrue("Las opciones de una pregunta abierta deben estar vacías",
                question.getOptions().isEmpty());
    }

    /**
     * Prueba clave del Taller 7:
     * "Construcción de diferentes productos: Se deberá demostrar que el mismo proceso
     * general permite construir diferentes tipos de preguntas mediante diferentes Builders."
     */
    @Test
    public void testConstruccionDiferentesProductosConMismoProceso() {
        QuestionDirector director = new QuestionDirector();

        // 1. Construir pregunta de tipo Abierta mediante Director
        OpenQuestionBuilder openBuilder = new OpenQuestionBuilder();
        director.setBuilder(openBuilder);
        director.constructCompleteQuestion(
                "¿Qué es la inversión de control?",
                DifficultyLevel.INTERMEDIO,
                "Principios SOLID",
                "Profesor Hurtado",
                "Concepto fundamental de diseño de software",
                "Clean Architecture",
                "Principio donde el flujo del programa se invierte respecto a la programación tradicional."
        );
        Question openQuestion = director.getQuestion();

        // 2. Construir pregunta de tipo Verdadero/Falso mediante el MISMO proceso en el Director
        TrueFalseQuestionBuilder tfBuilder = new TrueFalseQuestionBuilder();
        director.setBuilder(tfBuilder);
        director.constructCompleteQuestion(
                "¿El patrón Builder pertenece a la categoría de patrones estructurales?",
                DifficultyLevel.FACIL,
                "Patrones GoF",
                "Profesor Pantoja",
                "Builder es un patrón creacional, no estructural.",
                "GoF Book",
                "Falso"
        );
        Question tfQuestion = director.getQuestion();

        // 3. Construir pregunta de Selección Múltiple mediante el MISMO proceso en el Director
        MultipleChoiceQuestionBuilder mcBuilder = new MultipleChoiceQuestionBuilder();
        director.setBuilder(mcBuilder);
        director.constructCompleteQuestion(
                "¿A qué categoría pertenece el patrón Builder?",
                DifficultyLevel.FACIL,
                "Patrones GoF",
                "Profesor Pantoja",
                "Pertenece a patrones creacionales.",
                "GoF Book",
                "A"
        );
        // Se configuran las opciones requeridas por las reglas de negocio de selección múltiple
        mcBuilder.addOption("A", "Creacional", true);
        mcBuilder.addOption("B", "Estructural", false);
        mcBuilder.addOption("C", "De Comportamiento", false);
        Question mcQuestion = director.getQuestion();

        // Verificaciones: Tres productos distintos generados con el mismo Director
        assertNotNull(openQuestion);
        assertNotNull(tfQuestion);
        assertNotNull(mcQuestion);

        assertEquals(QuestionType.OPEN, openQuestion.getQuestionType());
        assertEquals(QuestionType.TRUE_FALSE, tfQuestion.getQuestionType());
        assertEquals(QuestionType.MULTIPLE_CHOICE, mcQuestion.getQuestionType());

        assertEquals(0, openQuestion.getOptions().size());
        assertEquals(2, tfQuestion.getOptions().size());
        assertEquals(3, mcQuestion.getOptions().size());
    }

    public static void main(String[] args) {
        System.out.println("=== Ejecutando OpenQuestionBuilderTest ===");
        Result result = JUnitCore.runClasses(OpenQuestionBuilderTest.class);
        for (Failure failure : result.getFailures()) {
            System.err.println("FALLO: " + failure.toString());
        }
        System.out.println("Resultado: " + (result.wasSuccessful() ? "EXITOSO (Todas las pruebas pasaron)" : "FALLIDO")
                + " | Total pruebas: " + result.getRunCount()
                + " | Fallos: " + result.getFailureCount());
    }
}
