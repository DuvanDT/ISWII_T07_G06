package builder;

import domain.Question;
import domain.QuestionType;

/**
 * Builder concreto para la creación de preguntas de tipo Abierta.
 * Aplica reglas de negocio específicas:
 * - Tipo fijado a OPEN.
 * - No requiere opciones predefinidas.
 * - Puede incluir una pauta, rúbrica o criterios de evaluación como respuesta esperada.
 */
public class OpenQuestionBuilder extends QuestionBuilder {

    public OpenQuestionBuilder() {
        reset();
    }

    @Override
    public void reset() {
        this.question = new Question();
        this.question.setQuestionType(QuestionType.OPEN);
        this.question.clearOptions();
    }

    @Override
    public void buildOptions() {
        if (question != null) {
            question.clearOptions();
        }
    }

    @Override
    public Question build() {
        validateCommon();

        // En preguntas abiertas no debe haber opciones de selección
        if (question.getOptions() != null && !question.getOptions().isEmpty()) {
            question.clearOptions();
        }

        // Si no se proporcionó una pauta de respuesta específica, se fija una por defecto
        if (question.getCorrectAnswer() == null || question.getCorrectAnswer().trim().isEmpty()) {
            question.setCorrectAnswer("Evaluación cualitativa según criterios del docente / rúbrica analítica.");
        }

        return this.question;
    }
}
