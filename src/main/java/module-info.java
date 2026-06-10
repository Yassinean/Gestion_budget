module com.ba.budgetapp {

    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    requires org.controlsfx.controls;
    requires org.bouncycastle.provider;

    opens com.ba.budgetapp to javafx.fxml;
    opens com.ba.budgetapp.controllers to javafx.fxml;

    exports com.ba.budgetapp;
    exports com.ba.budgetapp.controllers;
    exports com.ba.budgetapp.models.entities;
    opens com.ba.budgetapp.models.entities to javafx.base;
}