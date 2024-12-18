module de.check.checkers {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens de.check.checkers to javafx.fxml;
    exports de.check.checkers;
    exports de.check.checkers.ui;
    opens de.check.checkers.ui to javafx.fxml;
    exports de.check.checkers.utils;
    opens de.check.checkers.utils to javafx.fxml;
    exports de.check.checkers.structures;
    opens de.check.checkers.structures to javafx.fxml;
}