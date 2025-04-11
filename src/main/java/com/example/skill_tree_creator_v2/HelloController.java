package com.example.skill_tree_creator_v2;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * HelloController - Main controller for the application's UI
 * Handles user interactions and manages the conversion workflow
 */
public class HelloController
{
    @FXML
    private ListView<String> inputListView;
    @FXML
    private ListView<String> outputListView;
    @FXML
    private WebView previewWebView;

    /**
     * Handle the "Add Files" button click
     * Opens a file chooser dialog for JSON files
     */
    @FXML
    protected void handleAddFiles()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        List<File> files = fileChooser.showOpenMultipleDialog(inputListView.getScene().getWindow());

        if (files != null)
        {
            files.forEach(file -> inputListView.getItems().add(file.getPath()));
        }
    }

@FXML
protected void handlePreview() {
    String selectedInput = inputListView.getSelectionModel().getSelectedItem();
    if (selectedInput != null) {
        try {
            handlePreviewForFile(selectedInput);
        } catch (Exception e) {
            previewWebView.getEngine().loadContent("Error loading preview: " + e.getMessage());
        }
    }
}

@FXML
protected void handleConvert() {
    for (String file : inputListView.getItems()) {
        try {
            handleConvertForFile(file);
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

/**
 * Helper method for preview only - no file creation
 */
private void handlePreviewForFile(String file) throws IOException {
    JSON_Parser jsonParser = new JSON_Parser(file);
    MermaidRender renderer = new MermaidRender(jsonParser.readSkillTree(file));
    String htmlContent = renderer.diagramRender();
    previewWebView.getEngine().loadContent(htmlContent);
}

/**
 * Helper method for convert with file creation
 */
private void handleConvertForFile(String file) throws IOException {
    // Parse JSON and get file name
    JSON_Parser jsonParser = new JSON_Parser(file);
    String fileName = new File(file).getName().replaceFirst("[.][^.]+$", "");

    // Create parsers
    PNG_Parser pngParser = new PNG_Parser(previewWebView);
    PDF_Parser pdfParser = new PDF_Parser();

    // Generate Mermaid diagram
    MermaidRender renderer = new MermaidRender(jsonParser.readSkillTree(file));
    String htmlContent = renderer.diagramRender();
    previewWebView.getEngine().loadContent(htmlContent);

    // Wait for WebView to load
    previewWebView.getEngine().getLoadWorker().stateProperty().addListener(
        (obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // Wait for Mermaid to render
                Object result = previewWebView.getEngine().executeScript(
                    "new Promise(resolve => {" +
                    "    const checkSvg = () => {" +
                    "        if (document.querySelector('svg')) {" +
                    "            resolve(true);" +
                    "        } else {" +
                    "            setTimeout(checkSvg, 100);" +
                    "        }" +
                    "    };" +
                    "    checkSvg();" +
                    "});"
                );

                Platform.runLater(() -> {
                    try {
                        // Create output directories
                        new File("Output_PNG").mkdirs();
                        new File("Output_PDF").mkdirs();

                        // Save PNG
                        pngParser.saveAsPng(fileName);

                        // Convert to PDF
                        pdfParser.convertToPdf(fileName);

                        // Add PDF file path to output list
                        String pdfPath = "Output_PDF" + File.separator + fileName + ".pdf";
                        outputListView.getItems().add(pdfPath);
                    } catch (IOException e) {
                        System.err.println("Error in conversion: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
        }
    );
}

}