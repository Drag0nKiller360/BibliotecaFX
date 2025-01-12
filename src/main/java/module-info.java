module com.example.bibliotecafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    // Exporta pacotes para o módulo javafx.fxml
    opens com.example.bibliotecafx.controllers to javafx.fxml;
    exports com.example.bibliotecafx;
    opens com.example.bibliotecafx.controllers.usuarios.alunos to javafx.fxml;
    opens com.example.bibliotecafx.controllers.livros to javafx.fxml;
    opens com.example.bibliotecafx.controllers.usuarios.visitantes to javafx.fxml;
}