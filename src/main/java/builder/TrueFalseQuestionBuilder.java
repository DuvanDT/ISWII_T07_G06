package builder;

import domain.Option;
import domain.Question;
import domain.QuestionType;

/**
 * Builder concreto para la creación de preguntas de tipo Verdadero / Falso.
 * Aplica reglas de negocio específicas:
 * - Tipo fijado a TRUE_FALSE.
 * - Tiene exactamente dos opciones: "Verdadero" y "Falso".
 * - La respuesta correcta debe ser obligatoria y corresponder a "Verdadero" o "Falso".
 */
public class TrueFalseQuestionBuilder extends QuestionBuilder {

    public TrueFalseQuestionBuilder() {
        reset();
    }

    @Override
    public void reset() {
        this.question = new Question();
        this.question.setQuestionType(QuestionType.TRUE_FALSE);
        buildOptions();
    }

    @Override
    public void buildOptions() {
        if (question == null) return;
        question.clearOptions();
        question.addOption(new Option("V", "Verdadero", false));
        question.addOption(new Option("F", "Falso", false));
    }

    /**
     * Permite fijar la respuesta correcta usando un booleano primitivo.
     *
     * @param isTrue true para Verdadero, false para Falso
     */
    public void buildCorrectAnswer(boolean isTrue) {
        buildCorrectAnswer(isTrue ? "Verdadero" : "Falso");
    }

    @Override
    public void buildCorrectAnswer(String correctAnswer) {
        if (correctAnswer == null || correctAnswer.trim().isEmpty()) {
            throw new IllegalArgumentException("La respuesta correcta no puede ser nula ni vacía.");
        }

        String normalized = correctAnswer.trim().toLowerCase();
        String canonicalAnswer;

        if (normalized.equals("verdadero") || normalized.equals("v") || normalized.equals("true") || normalized.equals("1")) {
            canonicalAnswer = "Verdadero";
        } else if (normalized.equals("falso") || normalized.equals("f") || normalized.equals("false") || normalized.equals("0")) {
            canonicalAnswer = "Falso";
        } else {
            throw new IllegalArgumentException("Respuesta inválida: '" + correctAnswer + "'. Para preguntas de tipo Verdadero/Falso la respuesta debe ser 'Verdadero' o 'Falso'.");
        }

        if (this.question != null) {
            this.question.setCorrectAnswer(canonicalAnswer);
            // Sincronizar el estado de las opciones
            for (Option opt : this.question.getOptions()) {
                opt.setCorrect(opt.getText().equalsIgnoreCase(canonicalAnswer));
            }
        }
    }

    @Override
    public Question build() {
        validateCommon();

        if (question.getOptions() == null || question.getOptions().size() != 2) {
            buildOptions();
        }

        String correctAnswer = question.getCorrectAnswer();
        if (correctAnswer == null || correctAnswer.trim().isEmpty()) {
            throw new IllegalStateException("Para una pregunta de Verdadero/Falso debe definirse la respuesta correcta ('Verdadero' o 'Falso').");
        }

        boolean hasCorrectOption = false;
        for (Option opt : question.getOptions()) {
            if (opt.isCorrect()) {
                hasCorrectOption = true;
                break;
            }
        }

        if (!hasCorrectOption) {
            // Sincronizar si fue asignada previamente
            buildCorrectAnswer(correctAnswer);
        }

        return this.question;
    }
}
