module com.example.bibliotecafx {
    requires javafx.controls;
    requires javafx.fxml;

    // Exporta pacotes para o módulo javafx.fxml
    opens com.example.bibliotecafx.controllers to javafx.fxml;
    exports com.example.bibliotecafx;
}