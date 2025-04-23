# RAGEXAMPLE: Spring AI RAG Demo

This project demonstrates a Retrieval-Augmented Generation (RAG) pattern using Spring Boot, Spring AI, Ollama, and PGVector. It allows querying information from a PDF document (`resume.pdf`) via an interactive Spring Shell interface.

## Prerequisites

Before running this application, ensure you have the following installed:

*   **Java Development Kit (JDK):** Version 17 or later.
*   **Apache Maven:** To build and run the project.
*   **Docker and Docker Compose:** To run the required PostgreSQL database with the PGVector extension.
*   **Ollama:** Running locally with a suitable model pulled (e.g., `ollama pull llama3`). The application expects Ollama to be accessible at its default address (`http://localhost:11434`).

## Setup and Running

1.  **Clone the Repository:**
    ```bash
    git clone <repository-url>
    cd ragExample
    ```

2.  **Configure Ollama (if necessary):**
    *   Ensure Ollama is running.
    *   If your Ollama instance is running on a different host or port, update the Spring AI Ollama properties in `src/main/resources/application.yaml`.

3.  **Start Backend Services:**
    *   The project uses Docker Compose (`compose.yaml`) to manage the PostgreSQL database with PGVector. Start it using:
        ```bash
        docker compose up -d
        ```
    *   This will start a PostgreSQL container. The application is configured to connect to it automatically.

4.  **Build and Run the Application:**
    *   Use the Maven wrapper to build and run the Spring Boot application:
        ```bash
        ./mvnw spring-boot:run
        ```
    *   On Windows, use:
        ```bash
        ./mvnw.cmd spring-boot:run
        ```

5.  **Wait for Initialization:**
    *   On the first run, the application will check if the vector store is empty.
    *   If it is, the `ReferenceDocsLoader` will read `src/main/resources/docs/resume.pdf`, split it, generate embeddings, and store them in the PGVector database. This might take a moment. Check the application logs for messages like "Loading Spring Boot Reference PDF into Vector Store" and "Application is ready".

## Usage

Once the application is running and initialized, you can interact with it via the Spring Shell interface that appears in your terminal:

*   **Ask a Question:** Use the `q` command followed by your question in quotes. The application will retrieve relevant information from the `resume.pdf` and use it to generate an answer with Ollama.
    ```shell
    shell:> q "What are the key skills mentioned in the resume?"
    ```
    *(Replace the question with your query about the resume content)*

*   **Default Question:** If you just type `q` and press Enter, it will use the default question: "Tell about Venkateswaran Thandapani".
    ```shell
    shell:> q
    ```

*   **Exit:** Type `exit` to quit the Spring Shell and stop the application.

## Key Technologies

*   Spring Boot 3.4.4
*   Spring AI 1.0.0-M7 (Ollama, PGVector, PDF Reader)
*   Java 17
*   Maven
*   Docker / Docker Compose
*   PostgreSQL with PGVector
*   Ollama
*   Spring Shell

## Project Structure Highlights

*   `pom.xml`: Project dependencies.
*   `compose.yaml`: Docker Compose configuration for PostgreSQL/PGVector.
*   `src/main/resources/application.yaml`: Application configuration (database, Ollama connection).
*   `src/main/resources/docs/resume.pdf`: The source document for RAG.
*   `src/main/resources/prompts/prompt.st`: Prompt template for querying the LLM.
*   `src/main/java/com/venkat/ragExample/rag/ReferenceDocsLoader.java`: Loads and vectorizes the PDF.
*   `src/main/java/com/venkat/ragExample/rag/SpringAssistantCommand.java`: Implements the RAG logic via Spring Shell.
