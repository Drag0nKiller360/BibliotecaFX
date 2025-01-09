module com.example.bibliotecafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bibliotecafx to javafx.fxml;
    exports com.example.bibliotecafx;
}