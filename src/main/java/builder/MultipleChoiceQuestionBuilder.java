package builder;

import domain.Option;
import domain.Question;
import domain.QuestionType;
import java.util.List;

/**
 * Builder concreto para la creación de preguntas de tipo Selección Múltiple.
 * Aplica reglas de negocio específicas:
 * - Tipo fijado a MULTIPLE_CHOICE.
 * - Debe tener al menos 2 opciones.
 * - La respuesta correcta debe corresponder a una opción existente.
 */
public class MultipleChoiceQuestionBuilder extends QuestionBuilder {

    public MultipleChoiceQuestionBuilder() {
        reset();
    }

    @Override
    public void reset() {
        this.question = new Question();
        this.question.setQuestionType(QuestionType.MULTIPLE_CHOICE);
    }

    @Override
    public void buildOptions() {
        assignDefaultIdentifiers();
    }

    /**
     * Asigna identificadores tipo A, B, C, D... a las opciones que no tengan uno definido.
     */
    private void assignDefaultIdentifiers() {
        if (question == null || question.getOptions() == null) return;
        List<Option> options = question.getOptions();
        for (int i = 0; i < options.size(); i++) {
            Option opt = options.get(i);
            if (opt.getIdentifier() == null || opt.getIdentifier().trim().isEmpty()) {
                char idChar = (char) ('A' + i);
                opt.setIdentifier(String.valueOf(idChar));
            }
        }
    }

    @Override
    public Question build() {
        validateCommon();

        List<Option> options = question.getOptions();
        if (options == null || options.size() < 2) {
            throw new IllegalStateException("Una pregunta de selección múltiple debe tener al menos 2 opciones.");
        }

        assignDefaultIdentifiers();

        // Validar correspondencia de la respuesta correcta con una opción existente
        String correctAnswer = question.getCorrectAnswer();
        boolean matchedOptionFound = false;

        if (correctAnswer != null && !correctAnswer.trim().isEmpty()) {
            for (Option opt : options) {
                if (correctAnswer.equalsIgnoreCase(opt.getIdentifier()) ||
                    correctAnswer.equalsIgnoreCase(opt.getText())) {
                    opt.setCorrect(true);
                    matchedOptionFound = true;
                    // Sincronizar identificador canónico como respuesta correcta
                    question.setCorrectAnswer(opt.getIdentifier() + " - " + opt.getText());
                    break;
                }
            }
            if (!matchedOptionFound) {
                throw new IllegalStateException("La respuesta correcta especificada ('" + correctAnswer + "') no corresponde a ninguna opción existente.");
            }
        } else {
            // Verificar si alguna opción fue marcada como correcta directamente
            for (Option opt : options) {
                if (opt.isCorrect()) {
                    matchedOptionFound = true;
                    question.setCorrectAnswer(opt.getIdentifier() + " - " + opt.getText());
                    break;
                }
            }
            if (!matchedOptionFound) {
                throw new IllegalStateException("Debe existir al menos una opción correcta en la pregunta de selección múltiple.");
            }
        }

        Question built = this.question;
        return built;
    }
}
