module com.example.bibliotecafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.bibliotecafx to javafx.fxml;
    exports com.example.bibliotecafx;
}