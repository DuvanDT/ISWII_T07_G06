package domain;

/**
 * Representa los tipos de preguntas soportados en el Banco de Preguntas.
 */
public enum QuestionType {
    MULTIPLE_CHOICE("Selección Múltiple"),
    TRUE_FALSE("Verdadero / Falso"),
    OPEN("Abierta");

    private final String description;

    QuestionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
