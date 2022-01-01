module com.example.functionplotter {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.functionplotter to javafx.fxml;
    exports com.example.functionplotter;
}