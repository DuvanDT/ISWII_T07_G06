package builder;

import domain.DifficultyLevel;
import domain.Option;
import domain.Question;

/**
 * Clase abstracta Builder que define las operaciones para construir
 * paso a paso los diferentes componentes de una pregunta (Question).
 */
public abstract class QuestionBuilder {
    protected Question question;

    /**
     * Obtiene la pregunta que se encuentra en construcción o ya construida.
     *
     * @return objeto Question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * Método equivalente al estándar GoF getResult().
     *
     * @return objeto Question
     */
    public Question getResult() {
        return question;
    }

    /**
     * Reinicia el constructor preparando una nueva instancia limpia de Question.
     */
    public abstract void reset();

    /**
     * Establece el enunciado de la pregunta.
     *
     * @param statement Texto del enunciado
     */
    public void buildStatement(String statement) {
        if (this.question != null) {
            this.question.setStatement(statement);
        }
    }

    /**
     * Establece el nivel de dificultad de la pregunta.
     *
     * @param difficulty Dificultad (FACIL, MEDIO, DIFICIL, etc.)
     */
    public void buildDifficulty(DifficultyLevel difficulty) {
        if (this.question != null) {
            this.question.setDifficulty(difficulty);
        }
    }

    /**
     * Establece la categoría o área temática de la pregunta.
     *
     * @param category Nombre de la categoría
     */
    public void buildCategory(String category) {
        if (this.question != null) {
            this.question.setCategory(category);
        }
    }

    /**
     * Establece el autor de la pregunta.
     *
     * @param author Nombre del autor o docente
     */
    public void buildAuthor(String author) {
        if (this.question != null) {
            this.question.setAuthor(author);
        }
    }

    /**
     * Establece la justificación o retroalimentación pedagógica.
     *
     * @param justification Texto de justificación
     */
    public void buildJustification(String justification) {
        if (this.question != null) {
            this.question.setJustification(justification);
        }
    }

    /**
     * Establece la fuente bibliográfica o referencia de la pregunta.
     *
     * @param source Texto de la fuente
     */
    public void buildSource(String source) {
        if (this.question != null) {
            this.question.setSource(source);
        }
    }

    /**
     * Establece la respuesta correcta o clave de solución.
     *
     * @param correctAnswer Clave o texto de la respuesta correcta
     */
    public void buildCorrectAnswer(String correctAnswer) {
        if (this.question != null) {
            this.question.setCorrectAnswer(correctAnswer);
        }
    }

    /**
     * Establece el puntaje de la pregunta.
     *
     * @param score Puntaje numérico
     */
    public void buildScore(double score) {
        if (this.question != null) {
            this.question.setScore(score);
        }
    }

    /**
     * Agrega una opción a la pregunta en construcción.
     *
     * @param option Objeto Option
     */
    public void addOption(Option option) {
        if (this.question != null && option != null) {
            this.question.addOption(option);
        }
    }

    /**
     * Agrega una opción con texto y bandera de si es correcta.
     *
     * @param text      Texto de la opción
     * @param isCorrect Indica si es la opción correcta
     */
    public void addOption(String text, boolean isCorrect) {
        if (this.question != null) {
            this.question.addOption(text, isCorrect);
        }
    }

    /**
     * Agrega una opción con identificador, texto y bandera de si es correcta.
     *
     * @param identifier Identificador (ej. "A", "B")
     * @param text       Texto de la opción
     * @param isCorrect  Indica si es la opción correcta
     */
    public void addOption(String identifier, String text, boolean isCorrect) {
        if (this.question != null) {
            this.question.addOption(identifier, text, isCorrect);
        }
    }

    /**
     * Método para que cada builder configure o asegure las opciones según su tipo.
     */
    public abstract void buildOptions();

    /**
     * Construye y retorna la pregunta final validando las reglas de negocio aplicables.
     *
     * @return Question válida
     * @throws IllegalStateException si no cumple las reglas de negocio
     */
    public abstract Question build();

    /**
     * Valida las reglas de negocio comunes a todo tipo de pregunta.
     */
    protected void validateCommon() {
        if (question == null) {
            throw new IllegalStateException("La pregunta no ha sido inicializada. Debe llamar a reset().");
        }
        if (question.getStatement() == null || question.getStatement().trim().isEmpty()) {
            throw new IllegalStateException("El enunciado de la pregunta es obligatorio y no puede estar vacío.");
        }
    }
}
