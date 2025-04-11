module com.example.skill_tree_creator_v2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.google.gson;
    requires java.desktop;
    requires javafx.swing;

    opens com.example.skill_tree_creator_v2 to javafx.fxml, com.google.gson;
    exports com.example.skill_tree_creator_v2;
}