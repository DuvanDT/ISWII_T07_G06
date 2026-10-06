# Taller 7: Patrón de Diseño Creacional Builder
**Universidad del Cauca - FIET**  
**Laboratorio de Ingeniería del Software II (2-2026)**  
**Proyecto:** Gestión del Banco de Preguntas  

---

## 📌 Descripción de la Solución

Este proyecto implementa el patrón de diseño creacional **Builder** para la creación flexible, progresiva y validada de preguntas en el **Banco de Preguntas**, siguiendo las especificaciones del archivo `taller7.md` y la estructura de directorios actual del repositorio.

### Componentes del Patrón Builder:
1. **Producto (`Question`):**
   - Clase que representa una pregunta completa en el banco de preguntas con atributos como: `statement` (enunciado), `difficulty` (dificultad), `category` (categoría), `author` (autor), `justification` (justificación), `source` (fuente bibliográfica), `questionType` (tipo), `options` (lista de opciones), `correctAnswer` (respuesta correcta/pauta) y `score` (puntaje).

2. **Builder Abstracto (`QuestionBuilder`):**
   - Define las operaciones comunes para construir paso a paso una pregunta:
     - `reset()`
     - `buildStatement(String)`
     - `buildDifficulty(DifficultyLevel)`
     - `buildCategory(String)`
     - `buildAuthor(String)`
     - `buildJustification(String)`
     - `buildSource(String)`
     - `buildCorrectAnswer(String)`
     - `buildOptions()`
     - `build()`
   - Contiene la validación común de campos obligatorios (`validateCommon`).

3. **Builders Concretos:**
   - **`MultipleChoiceQuestionBuilder`:** Construye preguntas de selección múltiple (`MULTIPLE_CHOICE`). Valida que existan al menos 2 opciones, asigna identificadores ("A", "B", ...), y garantiza que la respuesta correcta corresponda a una opción existente.
   - **`TrueFalseQuestionBuilder`:** Construye preguntas de tipo Verdadero/Falso (`TRUE_FALSE`). Configura automáticamente las dos opciones estándar ("Verdadero" y "Falso") y valida que la respuesta correcta sea exclusivamente una de ellas.
   - **`OpenQuestionBuilder`:** Construye preguntas abiertas (`OPEN`). Asegura que no posea opciones de selección y permite fijar pautas o rúbricas de evaluación.

4. **Director (`QuestionDirector`):**
   - Orquesta la secuencia ordenada de pasos para construir preguntas completas o básicas sin acoplarse a los detalles internos de cada Builder concreto. Demuestra cómo el mismo proceso general produce diferentes tipos de preguntas según el Builder asignado.

5. **Cliente (`ApplicationMain`):**
   - Contiene el método `main()` y proporciona una interfaz completa desde la **terminal** sin necesidad de interfaces gráficas. Permite:
     - Ejecutar la demostración automática del patrón Builder (Director + 3 Builders).
     - Crear interactivamente preguntas de cualquier tipo desde la consola con validaciones en tiempo real.
     - Listar todas las preguntas construidas en la sesión con formato legible.
     - Ejecutar la suite completa de pruebas unitarias directamente desde el menú.

6. **Pruebas Unitarias (`JUnit 4`):**
   - Cobertura completa de casos requeridos:
     - Construcción correcta de preguntas válidas.
     - Validación de campos obligatorios (ej. ausencia o vacío de enunciado).
     - Validación específica por tipo (ej. mínimo 2 opciones en selección múltiple).
     - Validación de respuestas válidas (la respuesta correcta debe pertenecer a las opciones).
     - Demostración de construcción de diferentes productos mediante el mismo proceso del Director.

---

## 🚀 Compilación y Ejecución desde la Terminal

Los comandos se pueden ejecutar en PowerShell o CMD (se asume el JDK configurado):

### 1. Compilar todo el proyecto:
```powershell
javac -encoding UTF-8 -cp "src/main/java;lib/*" (Get-ChildItem -Recurse -Filter *.java src | Select-Object -ExpandProperty FullName)
```

### 2. Ejecutar la Aplicación Interactiva (Menú en Consola):
```powershell
java -cp "src/main/java;lib/*" ApplicationMain
```

### 3. Ejecutar Demostración Directa:
```powershell
java -cp "src/main/java;lib/*" ApplicationMain --demo
```

### 4. Ejecutar todas las Pruebas Unitarias:
```powershell
java -cp "src/main/java;lib/*" ApplicationMain --test
```

O individualmente por suite:
```powershell
# Pruebas de Selección Múltiple
java -cp "src/main/java;lib/*" test.java.MultipleChoiceQuestionBuilderTest

# Pruebas de Verdadero / Falso
java -cp "src/main/java;lib/*" test.java.TrueFalseQuestionBuilderTest

# Pruebas de Preguntas Abiertas y Director
java -cp "src/main/java;lib/*" test.java.OpenQuestionBuilderTest
```

---

## 📁 Estructura del Proyecto

```text
LISWII_T07_G06/
├── lib/
│   ├── junit-4.13.2.jar
│   └── hamcrest-core-1.3.jar
├── src/
│   └── main/
│       └── java/
│           ├── ApplicationMain.java
│           ├── builder/
│           │   ├── MultipleChoiceQuestionBuilder.java
│           │   ├── OpenQuestionBuilder.java
│           │   ├── QuestionBuilder.java
│           │   └── TrueFalseQuestionBuilder.java
│           ├── director/
│           │   └── QuestionDirector.java
│           ├── domain/
│           │   ├── DifficultyLevel.java
│           │   ├── Option.java
│           │   ├── OptionType.java
│           │   ├── Question.java
│           │   ├── QuestionType.java
│           │   └── Quetion.java
│           └── test/
│               └── java/
│                   ├── MultipleChoiceQuestionBuilderTest.java
│                   ├── OpenQuestionBuilderTest.java
│                   └── TrueFalseQuestionBuilderTest.java
├── Diagrama concreto para este proyecto Patron Builder.png
├── lisw2-Guia-taller07.pdf
├── taller7.md
└── README.md
```
