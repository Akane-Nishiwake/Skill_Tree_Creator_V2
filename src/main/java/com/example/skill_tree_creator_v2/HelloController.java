package com.example.skill_tree_creator_v2;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.List;

public class HelloController {
    @FXML
    private ListView<String> inputListView;
    @FXML
    private ListView<String> outputListView;
    @FXML
    private TextArea previewArea;

    @FXML
    protected void handleAddFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );
        List<File> files = fileChooser.showOpenMultipleDialog(inputListView.getScene().getWindow());

        if (files != null) {
            files.forEach(file -> inputListView.getItems().add(file.getPath()));
        }
    }

    @FXML
    protected void handleConvert() {
        // Implement conversion logic here
        inputListView.getItems().forEach(path -> {
            try {
                JSON_Parser parser = new JSON_Parser(path);
                outputListView.getItems().add(parser.getOutputFileName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private WebView previewWebView; // Replace TextArea with WebView in FXML and here

    @FXML
    protected void handlePreview() {
        String selectedInput = inputListView.getSelectionModel().getSelectedItem();
        if (selectedInput != null) {
            try {
                JSON_Parser parser = new JSON_Parser(selectedInput);
                MermaidRender renderer = new MermaidRender(parser.readSkillTree(selectedInput));
                String htmlContent = renderer.diagramRender();
                previewWebView.getEngine().loadContent(htmlContent);
                //WebView webView = previewWebView;
                PNG_Parser pngParser = new PNG_Parser(previewWebView);
                pngParser.saveAsPng("preview");
            } catch (Exception e) {
                previewWebView.getEngine().loadContent("Error loading preview: " + e.getMessage());
            }
        }
    }
}