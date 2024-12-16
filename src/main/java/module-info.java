module de.check.checkers {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.check.checkers to javafx.fxml;
    exports de.check.checkers;
}