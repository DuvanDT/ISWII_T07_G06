package domain;

/**
 * Representa el tipo de contenido o formato de una opción de respuesta.
 */
public enum OptionType {
    TEXT("Texto"),
    BOOLEAN("Booleano"),
    NUMERIC("Numérico"),
    CODE("Código");

    private final String description;

    OptionType(String description) {
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
