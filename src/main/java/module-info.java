module com.clase {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql; //necesario para la conexión con MySQL

    opens com.clase to javafx.fxml, com.google.gson;
    //opens com.clase.data to com.google.gson;
    exports com.clase;
}
