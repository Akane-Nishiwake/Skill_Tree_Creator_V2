# Skill Tree Creator v2

Skill Tree Creator v2 is a Java-based application for generating skill tree diagrams using the Mermaid.js library. It allows users to render skill trees as HTML, convert them to PNG images, and further export them as PDF documents.

## Features

- **Mermaid.js Integration**: Generate skill tree diagrams in Mermaid.js format.
- **HTML Rendering**: Render Mermaid diagrams in a WebView.
- **PNG Export**: Save rendered diagrams as PNG images.
- **PDF Export**: Convert PNG images to PDF documents.

## Technologies Used

- **Java**: Core programming language.
- **JavaFX**: For rendering Mermaid diagrams in a WebView.
- **Mermaid.js**: For creating skill tree diagrams.
- **Apache PDFBox**: For handling PDF generation.
- **Maven**: Build and dependency management.

## Project Structure

- `MermaidRender.java`: Handles the generation of Mermaid.js diagram definitions and HTML rendering.
- `PNG_Parser.java`: Captures WebView content and saves it as PNG images.
- `PDF_Parser.java`: Converts PNG images to PDF documents.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Setup and Usage

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd skill_tree_creator_v2
   ```
2. Build the project using Maven:
   ```bash
    mvn clean install
    ```
3. Run the application:
    ```bash
    mvn javafx:run
   ```
4. Follow the on-screen instructions to create and export skill trees.

## Ouput Directories
- PNG Files: Saved in the Output_PNG directory.
- PDF Files: Saved in the Output_PDF directory.

