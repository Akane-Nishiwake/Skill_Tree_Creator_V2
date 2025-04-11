package com.example.skill_tree_creator_v2;

 import org.apache.pdfbox.pdmodel.PDDocument;
 import org.apache.pdfbox.pdmodel.PDPage;
 import org.apache.pdfbox.pdmodel.PDPageContentStream;
 import org.apache.pdfbox.pdmodel.common.PDRectangle;
 import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

 import java.io.File;
 import java.io.IOException;

 /**
  * PDF_Parser - Handles conversion of PNG images to PDF documents
  */
 public class PDF_Parser {

     /**
      * Convert PNG file to PDF
      *
      * @param pngFileName Name of the input PNG file (without extension)
      * @throws IOException if file operations fail
      */
     public void convertToPdf(String pngFileName) throws IOException {
         // Get absolute paths for directories
         File baseDir = new File(System.getProperty("user.dir"));
         File pngDir = new File(baseDir, "Output_PNG");
         File pdfDir = new File(baseDir, "Output_PDF");
         pdfDir.mkdirs();

         // Create input/output file objects
         File pngFile = new File(pngDir, pngFileName + ".png");
         File pdfFile = new File(pdfDir, pngFileName + ".pdf");

         if (!pngFile.exists()) {
             throw new IOException("PNG file not found: " + pngFile.getAbsolutePath());
         }

         // Create PDF document
         try (PDDocument document = new PDDocument()) {
             // Create page with A4 size
             PDPage page = new PDPage(PDRectangle.A4);
             document.addPage(page);

             // Load PNG image
             PDImageXObject image = PDImageXObject.createFromFileByContent(pngFile, document);

             // Calculate scaling to fit A4 page
             float pageWidth = page.getMediaBox().getWidth();
             float pageHeight = page.getMediaBox().getHeight();
             float imageWidth = image.getWidth();
             float imageHeight = image.getHeight();

             // Calculate scale factor to fit image within page
             float scale = Math.min(pageWidth / imageWidth, pageHeight / imageHeight) * 0.9f;
             float scaledWidth = imageWidth * scale;
             float scaledHeight = imageHeight * scale;

             // Center image on page
             float x = (pageWidth - scaledWidth) / 2;
             float y = (pageHeight - scaledHeight) / 2;

             // Create content stream and draw image
             try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                 contentStream.drawImage(image, x, y, scaledWidth, scaledHeight);
             }

             // Save PDF with absolute path
             document.save(pdfFile);
             System.out.println("PDF saved to: " + pdfFile.getAbsolutePath());
         }
     }
 }