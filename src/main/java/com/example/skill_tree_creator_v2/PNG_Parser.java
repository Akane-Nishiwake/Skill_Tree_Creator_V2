package com.example.skill_tree_creator_v2;

import javafx.concurrent.Worker;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.web.WebView;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * PNG_Parser - Handles conversion of WebView content to PNG images
 */
public class PNG_Parser
{
    private final WebView webView;

    /**
     * Constructor
     *
     * @param webView WebView instance to capture as PNG
     */
    public PNG_Parser(WebView webView)
    {
        this.webView = webView;
    }

    /**
     * Save WebView content as PNG
     *
     * @param fileName Name of the output PNG file (without extension)
     */
    public void saveAsPng(String fileName)
    {
        if (webView.getEngine().getLoadWorker().getState() != Worker.State.SUCCEEDED)
        {
            webView.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) ->
                                                                            {
                                                                                if (newState == Worker.State.SUCCEEDED)
                                                                                {
                                                                                    waitForMermaidAndSnapshot(fileName);
                                                                                }
                                                                            });
        }
        else
        {
            waitForMermaidAndSnapshot(fileName);
        }
    }

    /**
     * Wait for Mermaid diagram to render and take snapshot
     *
     * @param fileName Name of the output PNG file
     */
    private void waitForMermaidAndSnapshot(String fileName)
    {
        webView.getEngine().executeScript("new Promise(resolve => {" + "    const checkSvg = () => {" +
                                          "        const svg = document.querySelector('svg');" + "        if (svg) {" +
                                          "            resolve(true);" + "        } else {" +
                                          "            setTimeout(checkSvg, 100);" + "        }" + "    };" +
                                          "    checkSvg();" + "});");

        // Add small delay to ensure the Promise has started
        javafx.application.Platform.runLater(() ->
                                             {
                                                 try
                                                 {
                                                     Thread.sleep(2000);
                                                     takeSnapshotAndSave(fileName);
                                                 }
                                                 catch (InterruptedException e)
                                                 {
                                                     e.printStackTrace();
                                                 }
                                             });
    }

    /**
     * Take snapshot of WebView and save as PNG
     *
     * @param fileName Name of the output PNG file
     */
    private void takeSnapshotAndSave(String fileName)
    {
        try
        {
            Path outputDir = Paths.get(System.getProperty("user.dir"), "Output_PNG");
            outputDir.toFile().mkdirs();

            Path filePath = outputDir.resolve(fileName + ".png");
            WritableImage snapshot = webView.snapshot(null, null);
            File outputFile = filePath.toFile();

            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", outputFile);
            System.out.println("PNG saved to: " + outputFile.getAbsolutePath());
        }
        catch (IOException e)
        {
            System.err.println("Failed to save PNG: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
