package domain;

/**
 * Representa los niveles de dificultad que puede tener una pregunta en el Banco de Preguntas.
 */
public enum DifficultyLevel {
    BASICO("Básico"),
    INTERMEDIO("Intermedio"),
    AVANZADO("Avanzado"),
    FACIL("Fácil"),
    MEDIO("Medio"),
    DIFICIL("Difícil"),
    ALTO("Alto"),
    BAJO("Bajo");

    private final String displayName;

    DifficultyLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
