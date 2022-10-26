module com.gesto.gesto {
    requires javafx.controls;
    requires javafx.fxml;
    requires GestoAPI;
    requires com.gluonhq.attach.util;
    requires com.gluonhq.charm.glisten;
    requires java.sql;



    opens com.gesto to javafx.fxml;
    exports com.gesto;
}