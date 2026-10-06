package director;

import builder.QuestionBuilder;
import domain.DifficultyLevel;
import domain.Question;

/**
 * Clase Director en el patrón Builder.
 * Responsable de orquestar el proceso y orden de construcción de una Question,
 * sin conocer los detalles específicos de cada tipo de pregunta concreta.
 */
public class QuestionDirector {
    private QuestionBuilder builder;

    public QuestionDirector() {
    }

    public QuestionDirector(QuestionBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(QuestionBuilder builder) {
        this.builder = builder;
    }

    public void changeBuilder(QuestionBuilder builder) {
        this.builder = builder;
    }

    public QuestionBuilder getBuilder() {
        return builder;
    }

    /**
     * Orquesta la construcción completa de una pregunta con todos sus metadatos.
     */
    public void constructCompleteQuestion(String statement,
                                          DifficultyLevel difficulty,
                                          String category,
                                          String author,
                                          String justification,
                                          String source,
                                          String correctAnswer) {
        ensureBuilderConfigured();
        builder.reset();
        builder.buildStatement(statement);
        builder.buildDifficulty(difficulty);
        builder.buildCategory(category);
        builder.buildAuthor(author);
        builder.buildJustification(justification);
        builder.buildSource(source);
        builder.buildOptions();
        if (correctAnswer != null && !correctAnswer.trim().isEmpty()) {
            builder.buildCorrectAnswer(correctAnswer);
        }
    }

    /**
     * Orquesta la construcción de una pregunta básica (enunciado, dificultad, categoría y autor).
     */
    public void constructBasicQuestion(String statement,
                                       DifficultyLevel difficulty,
                                       String category,
                                       String author) {
        ensureBuilderConfigured();
        builder.reset();
        builder.buildStatement(statement);
        builder.buildDifficulty(difficulty);
        builder.buildCategory(category);
        builder.buildAuthor(author);
        builder.buildOptions();
    }

    /**
     * Obtiene el producto terminado validado a través del builder actual.
     *
     * @return objeto Question construido
     */
    public Question getQuestion() {
        ensureBuilderConfigured();
        return builder.build();
    }

    private void ensureBuilderConfigured() {
        if (builder == null) {
            throw new IllegalStateException("No se ha configurado un QuestionBuilder en el QuestionDirector.");
        }
    }
}
