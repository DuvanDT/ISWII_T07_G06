package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa el Producto final en el patrón Builder: una pregunta en el Banco de Preguntas.
 */
public class Question {
    private String statement;
    private DifficultyLevel difficulty;
    private String category;
    private String author;
    private String justification;
    private String source;
    private QuestionType questionType;
    private List<Option> options;
    private String correctAnswer;
    private double score;

    public Question() {
        this.options = new ArrayList<>();
        this.score = 1.0;
        this.difficulty = DifficultyLevel.MEDIO;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options != null ? options : new ArrayList<>();
    }

    public void addOption(Option option) {
        if (option != null) {
            this.options.add(option);
        }
    }

    public void addOption(String text, boolean isCorrect) {
        addOption(new Option(text, isCorrect));
    }

    public void addOption(String identifier, String text, boolean isCorrect) {
        addOption(new Option(identifier, text, isCorrect));
    }

    public void clearOptions() {
        this.options.clear();
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public List<Option> getCorrectOptions() {
        List<Option> correctList = new ArrayList<>();
        for (Option opt : options) {
            if (opt.isCorrect()) {
                correctList.add(opt);
            }
        }
        return Collections.unmodifiableList(correctList);
    }

    public Option getOptionByIdentifier(String identifier) {
        if (identifier == null) return null;
        for (Option opt : options) {
            if (identifier.equalsIgnoreCase(opt.getIdentifier())) {
                return opt;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("================================================================================\n");
        sb.append(String.format(" Tipo: %-20s | Dificultad: %-12s | Puntaje: %.1f pts\n",
                (questionType != null ? questionType.getDescription() : "No especificado"),
                (difficulty != null ? difficulty.getDisplayName() : "No especificada"),
                score));
        sb.append(String.format(" Categoría: %-15s | Autor: %-18s | Fuente: %s\n",
                (category != null && !category.isEmpty() ? category : "General"),
                (author != null && !author.isEmpty() ? author : "Anónimo"),
                (source != null && !source.isEmpty() ? source : "No referenciada")));
        sb.append("--------------------------------------------------------------------------------\n");
        sb.append(" Enunciado:\n   ").append(statement != null ? statement : "(Sin enunciado)").append("\n");

        if (options != null && !options.isEmpty()) {
            sb.append(" Opciones:\n");
            for (Option opt : options) {
                sb.append("   ").append(opt.toString()).append("\n");
            }
        }

        if (correctAnswer != null && !correctAnswer.trim().isEmpty()) {
            sb.append(" Respuesta Correcta / Guía:\n   ").append(correctAnswer).append("\n");
        }

        if (justification != null && !justification.trim().isEmpty()) {
            sb.append(" Justificación:\n   ").append(justification).append("\n");
        }
        sb.append("================================================================================");
        return sb.toString();
    }
}
