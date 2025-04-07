module de.check.checkers {
    requires javafx.controls;
    requires javafx.web;
    requires javafx.fxml;
    requires jdk.jsobject;
    requires java.desktop;
    requires java.sql;

    exports de.check.checkers;
    exports de.check.checkers.ui;
    exports de.check.checkers.utils;
    exports de.check.checkers.structures;
}