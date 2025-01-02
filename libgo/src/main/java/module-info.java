module com.libgo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.libgo to javafx.fxml, javafx.base, javafx.controls;
    exports com.libgo;
}
