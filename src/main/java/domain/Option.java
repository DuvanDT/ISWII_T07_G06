package domain;

import java.util.Objects;

/**
 * Representa una opción de respuesta asociada a una pregunta.
 */
public class Option {
    private String identifier;
    private String text;
    private boolean correct;
    private OptionType optionType;
    private String feedback;

    public Option() {
        this.optionType = OptionType.TEXT;
    }

    public Option(String text, boolean correct) {
        this("", text, correct, OptionType.TEXT, null);
    }

    public Option(String identifier, String text, boolean correct) {
        this(identifier, text, correct, OptionType.TEXT, null);
    }

    public Option(String identifier, String text, boolean correct, OptionType optionType) {
        this(identifier, text, correct, optionType, null);
    }

    public Option(String identifier, String text, boolean correct, OptionType optionType, String feedback) {
        this.identifier = identifier != null ? identifier : "";
        this.text = text != null ? text : "";
        this.correct = correct;
        this.optionType = optionType != null ? optionType : OptionType.TEXT;
        this.feedback = feedback;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public OptionType getOptionType() {
        return optionType;
    }

    public void setOptionType(OptionType optionType) {
        this.optionType = optionType;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return correct == option.correct &&
                Objects.equals(identifier, option.identifier) &&
                Objects.equals(text, option.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, text, correct);
    }

    @Override
    public String toString() {
        String idPrefix = (identifier != null && !identifier.trim().isEmpty()) ? "[" + identifier + "] " : "";
        String correctIndicator = correct ? " [* CORRECTA]" : "";
        return idPrefix + text + correctIndicator;
    }
}
