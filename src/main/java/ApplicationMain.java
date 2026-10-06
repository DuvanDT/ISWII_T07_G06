import builder.MultipleChoiceQuestionBuilder;
import builder.OpenQuestionBuilder;
import builder.QuestionBuilder;
import builder.TrueFalseQuestionBuilder;
import director.QuestionDirector;
import domain.DifficultyLevel;
import domain.Option;
import domain.Question;
import domain.QuestionType;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal que actúa como Cliente en el patrón Builder.
 * Cumple con los requerimientos del Taller 7:
 *  a. Crear un objeto QuestionDirector.
 *  b. Crear los diferentes objetos Builders.
 *  c. Utilizar la clase Director para construir preguntas.
 *  d. Mostrar en consola las preguntas construidas.
 *
 * Además proporciona una interfaz de consola interactiva para construir
 * y validar preguntas desde la terminal.
 */
public class ApplicationMain {

    private static final List<Question> questionBank = new ArrayList<>();

    public static void main(String[] args) {
        // Soporte para banderas por línea de comando
        if (args.length > 0) {
            if ("--demo".equalsIgnoreCase(args[0]) || "-d".equalsIgnoreCase(args[0])) {
                runDemonstration();
                return;
            } else if ("--test".equalsIgnoreCase(args[0]) || "-t".equalsIgnoreCase(args[0])) {
                runUnitTests();
                return;
            }
        }

        Scanner scanner = new Scanner(System.in);

        // Si no hay entrada interactiva (ejecución no interactiva o piped), se corre la demo automáticamente
        boolean interactive = true;
        try {
            if (System.console() == null && System.in.available() == 0) {
                // Entrada no interactiva vacía, ejecutamos demo directamente
                runDemonstration();
                return;
            }
        } catch (Exception ignored) {
        }

        System.out.println("================================================================================");
        System.out.println("       SISTEMA DE GESTIÓN DE BANCO DE PREGUNTAS - PATRÓN BUILDER");
        System.out.println("           Laboratorio de Ingeniería del Software II - Taller 7");
        System.out.println("================================================================================");

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Seleccione una opción: ");
            String input;
            try {
                if (!scanner.hasNextLine()) {
                    // Fin de flujo de entrada
                    break;
                }
                input = scanner.nextLine().trim();
            } catch (Exception e) {
                break;
            }

            switch (input) {
                case "1":
                    runDemonstration();
                    break;
                case "2":
                    createMultipleChoiceInteractive(scanner);
                    break;
                case "3":
                    createTrueFalseInteractive(scanner);
                    break;
                case "4":
                    createOpenQuestionInteractive(scanner);
                    break;
                case "5":
                    listCreatedQuestions();
                    break;
                case "6":
                    runUnitTests();
                    break;
                case "0":
                    System.out.println("\n¡Gracias por utilizar el Banco de Preguntas! Hasta luego.");
                    running = false;
                    break;
                default:
                    System.out.println("\n[!] Opción no válida. Por favor ingrese un número del menú.\n");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n----------------------------- MENÚ PRINCIPAL -----------------------------");
        System.out.println(" [1] Ejecutar demostración completa (Director + 3 Builders)");
        System.out.println(" [2] Crear pregunta de Selección Múltiple (Terminal interactiva)");
        System.out.println(" [3] Crear pregunta de Verdadero / Falso (Terminal interactiva)");
        System.out.println(" [4] Crear pregunta Abierta (Terminal interactiva)");
        System.out.println(" [5] Listar todas las preguntas creadas en la sesión");
        System.out.println(" [6] Ejecutar suite de pruebas unitarias");
        System.out.println(" [0] Salir");
        System.out.println("--------------------------------------------------------------------------");
    }

    /**
     * Paso 5 del taller:
     * Demostración completa del patrón Builder utilizando el QuestionDirector y los tres Builders concretos.
     */
    public static void runDemonstration() {
        System.out.println("\n================================================================================");
        System.out.println("       DEMOSTRACIÓN DEL PATRÓN BUILDER CON QUESTIONDIRECTOR Y BUILDERS");
        System.out.println("================================================================================");

        // a. Crear un objeto QuestionDirector
        QuestionDirector director = new QuestionDirector();
        System.out.println("[+] Objeto QuestionDirector creado exitosamente.");

        // b. Crear los diferentes objetos Builders
        MultipleChoiceQuestionBuilder mcBuilder = new MultipleChoiceQuestionBuilder();
        TrueFalseQuestionBuilder tfBuilder = new TrueFalseQuestionBuilder();
        OpenQuestionBuilder openBuilder = new OpenQuestionBuilder();
        System.out.println("[+] Objeto MultipleChoiceQuestionBuilder creado.");
        System.out.println("[+] Objeto TrueFalseQuestionBuilder creado.");
        System.out.println("[+] Objeto OpenQuestionBuilder creado.");

        System.out.println("\n--- 1. CONSTRUYENDO PREGUNTA DE SELECCIÓN MÚLTIPLE VÍA DIRECTOR ---");
        // c. Utilizar la clase Director para construir preguntas
        director.setBuilder(mcBuilder);
        director.constructCompleteQuestion(
                "¿Cuál de los siguientes patrones de diseño creacionales se enfoca en construir " +
                        "un objeto complejo paso a paso, permitiendo diferentes representaciones?",
                DifficultyLevel.MEDIO,
                "Patrones Creacionales",
                "Ing. Libardo Pantoja",
                "El patrón Builder separa la construcción paso a paso de un objeto de su representación final.",
                "Erich Gamma et al. (GoF) - Design Patterns",
                "B"
        );
        // Opciones específicas
        mcBuilder.addOption("A", "Abstract Factory", false);
        mcBuilder.addOption("B", "Builder", true);
        mcBuilder.addOption("C", "Prototype", false);
        mcBuilder.addOption("D", "Singleton", false);

        Question q1 = director.getQuestion();
        questionBank.add(q1);

        // d. Mostrar en consola las preguntas construidas
        System.out.println(q1);

        System.out.println("\n--- 2. CONSTRUYENDO PREGUNTA DE VERDADERO / FALSO VÍA DIRECTOR ---");
        director.setBuilder(tfBuilder);
        director.constructCompleteQuestion(
                "¿En el patrón Builder, la clase Director conoce la clase concreta del producto que se construye?",
                DifficultyLevel.FACIL,
                "Ingeniería de Software II",
                "Ing. Julio Hurtado",
                "Falso: El Director interactúa exclusivamente a través de la abstracción Builder y desconoce el producto final.",
                "Refactoring Guru - Builder Pattern",
                "Falso"
        );
        Question q2 = director.getQuestion();
        questionBank.add(q2);
        System.out.println(q2);

        System.out.println("\n--- 3. CONSTRUYENDO PREGUNTA ABIERTA VÍA DIRECTOR ---");
        director.setBuilder(openBuilder);
        director.constructCompleteQuestion(
                "Explique detalladamente la diferencia funcional entre el patrón Builder y el patrón Abstract Factory.",
                DifficultyLevel.AVANZADO,
                "Diseño de Software",
                "Equipo Docente FIET - Unicauca",
                "El Builder se enfoca en construir un objeto complejo paso a paso, mientras que Abstract Factory produce familias de objetos relacionados.",
                "Design Patterns Elements of Reusable Object-Oriented Software",
                "Pauta: Mencionar que Builder se concentra en el proceso de pasos para un producto, y Abstract Factory en familias de productos de entrega inmediata."
        );
        Question q3 = director.getQuestion();
        questionBank.add(q3);
        System.out.println(q3);

        System.out.println("\n================================================================================");
        System.out.println(" Demostración finalizada: Se construyeron 3 preguntas válidas mediante el Director.");
        System.out.println("================================================================================\n");
    }

    private static void createMultipleChoiceInteractive(Scanner scanner) {
        System.out.println("\n=== Creación de Pregunta de Selección Múltiple ===");
        try {
            MultipleChoiceQuestionBuilder builder = new MultipleChoiceQuestionBuilder();

            System.out.print("Enunciado: ");
            String statement = scanner.nextLine().trim();
            builder.buildStatement(statement);

            DifficultyLevel diff = promptDifficulty(scanner);
            builder.buildDifficulty(diff);

            System.out.print("Categoría (ej. Algoritmos, Patrones): ");
            builder.buildCategory(scanner.nextLine().trim());

            System.out.print("Autor: ");
            builder.buildAuthor(scanner.nextLine().trim());

            System.out.print("Fuente / Referencia: ");
            builder.buildSource(scanner.nextLine().trim());

            System.out.print("Justificación de la respuesta: ");
            builder.buildJustification(scanner.nextLine().trim());

            System.out.print("¿Cuántas opciones desea agregar? (Mínimo 2): ");
            int numOptions = Integer.parseInt(scanner.nextLine().trim());
            while (numOptions < 2) {
                System.out.print("Por regla de negocio deben ser al menos 2 opciones. Ingrese nuevamente: ");
                numOptions = Integer.parseInt(scanner.nextLine().trim());
            }

            for (int i = 0; i < numOptions; i++) {
                char id = (char) ('A' + i);
                System.out.print("Opción [" + id + "]: ");
                String optText = scanner.nextLine().trim();
                System.out.print("¿Es la opción correcta? (s/n): ");
                boolean isCorrect = scanner.nextLine().trim().equalsIgnoreCase("s");
                builder.addOption(String.valueOf(id), optText, isCorrect);
            }

            Question question = builder.build();
            questionBank.add(question);
            System.out.println("\n[✓] ¡Pregunta de Selección Múltiple creada y validada exitosamente!");
            System.out.println(question);
        } catch (Exception e) {
            System.err.println("\n[✗] Error al construir la pregunta: " + e.getMessage());
        }
    }

    private static void createTrueFalseInteractive(Scanner scanner) {
        System.out.println("\n=== Creación de Pregunta de Verdadero / Falso ===");
        try {
            TrueFalseQuestionBuilder builder = new TrueFalseQuestionBuilder();

            System.out.print("Enunciado: ");
            String statement = scanner.nextLine().trim();
            builder.buildStatement(statement);

            DifficultyLevel diff = promptDifficulty(scanner);
            builder.buildDifficulty(diff);

            System.out.print("Categoría: ");
            builder.buildCategory(scanner.nextLine().trim());

            System.out.print("Autor: ");
            builder.buildAuthor(scanner.nextLine().trim());

            System.out.print("Fuente / Referencia: ");
            builder.buildSource(scanner.nextLine().trim());

            System.out.print("Justificación: ");
            builder.buildJustification(scanner.nextLine().trim());

            System.out.print("¿Respuesta correcta (V para Verdadero / F para Falso)?: ");
            String ans = scanner.nextLine().trim();
            builder.buildCorrectAnswer(ans);

            Question question = builder.build();
            questionBank.add(question);
            System.out.println("\n[✓] ¡Pregunta de Verdadero/Falso creada y validada exitosamente!");
            System.out.println(question);
        } catch (Exception e) {
            System.err.println("\n[✗] Error al construir la pregunta: " + e.getMessage());
        }
    }

    private static void createOpenQuestionInteractive(Scanner scanner) {
        System.out.println("\n=== Creación de Pregunta Abierta ===");
        try {
            OpenQuestionBuilder builder = new OpenQuestionBuilder();

            System.out.print("Enunciado: ");
            String statement = scanner.nextLine().trim();
            builder.buildStatement(statement);

            DifficultyLevel diff = promptDifficulty(scanner);
            builder.buildDifficulty(diff);

            System.out.print("Categoría: ");
            builder.buildCategory(scanner.nextLine().trim());

            System.out.print("Autor: ");
            builder.buildAuthor(scanner.nextLine().trim());

            System.out.print("Fuente / Referencia: ");
            builder.buildSource(scanner.nextLine().trim());

            System.out.print("Justificación: ");
            builder.buildJustification(scanner.nextLine().trim());

            System.out.print("Criterios de evaluación / Pauta de respuesta: ");
            String criteria = scanner.nextLine().trim();
            builder.buildCorrectAnswer(criteria);

            Question question = builder.build();
            questionBank.add(question);
            System.out.println("\n[✓] ¡Pregunta Abierta creada y validada exitosamente!");
            System.out.println(question);
        } catch (Exception e) {
            System.err.println("\n[✗] Error al construir la pregunta: " + e.getMessage());
        }
    }

    private static DifficultyLevel promptDifficulty(Scanner scanner) {
        System.out.println("Nivel de dificultad: [1] Fácil  [2] Medio  [3] Difícil");
        System.out.print("Seleccione (1-3, por defecto Medio): ");
        String line = scanner.nextLine().trim();
        switch (line) {
            case "1":
                return DifficultyLevel.FACIL;
            case "3":
                return DifficultyLevel.DIFICIL;
            case "2":
            default:
                return DifficultyLevel.MEDIO;
        }
    }

    private static void listCreatedQuestions() {
        System.out.println("\n================================================================================");
        System.out.println("             PREGUNTAS EN EL BANCO DE PREGUNTAS (" + questionBank.size() + ")");
        System.out.println("================================================================================");
        if (questionBank.isEmpty()) {
            System.out.println("Aún no se han creado preguntas en esta sesión. Ejecute la opción [1] para ver la demo.");
            return;
        }
        int index = 1;
        for (Question q : questionBank) {
            System.out.println("\n--- PREGUNTA #" + (index++) + " ---");
            System.out.println(q);
        }
    }

    public static void runUnitTests() {
        System.out.println("\n================================================================================");
        System.out.println("              EJECUCIÓN DE PRUEBAS UNITARIAS (JUNIT 4)");
        System.out.println("================================================================================");

        Class<?>[] testClasses = new Class<?>[]{
                test.java.MultipleChoiceQuestionBuilderTest.class,
                test.java.TrueFalseQuestionBuilderTest.class,
                test.java.OpenQuestionBuilderTest.class
        };

        int totalRun = 0;
        int totalFailures = 0;

        for (Class<?> testClass : testClasses) {
            System.out.println("\n[*] Ejecutando suite: " + testClass.getSimpleName() + "...");
            Result result = JUnitCore.runClasses(testClass);
            totalRun += result.getRunCount();
            totalFailures += result.getFailureCount();

            for (Failure f : result.getFailures()) {
                System.err.println("  [FALLO] " + f.getTestHeader() + ": " + f.getMessage());
            }

            if (result.wasSuccessful()) {
                System.out.println("  [✓] " + testClass.getSimpleName() + ": " + result.getRunCount() + "/" + result.getRunCount() + " pruebas pasaron exitosamente.");
            } else {
                System.err.println("  [✗] " + testClass.getSimpleName() + ": Hubo fallos (" + result.getFailureCount() + " fallas).");
            }
        }

        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("RESUMEN DE PRUEBAS: Total ejecutadas: " + totalRun + " | Fallos: " + totalFailures);
        if (totalFailures == 0) {
            System.out.println("ESTADO: ¡TODAS LAS PRUEBAS UNITARIAS PASARON EXITOSAMENTE (100%)!");
        } else {
            System.err.println("ESTADO: Se presentaron fallos en las pruebas unitarias.");
        }
        System.out.println("--------------------------------------------------------------------------------\n");
    }
}
