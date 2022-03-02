
module io.github.gleidsonmt.speedcut {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires scenicView;
    requires org.yaml.snakeyaml;
    requires org.jetbrains.annotations;

    requires io.github.gleidsonmt.gncontrols;
    requires io.github.gleidsonmt.gndecorator;

    requires animatefx;
    requires java.sql;

    requires com.dlsc.formsfx;
    requires fr.brouillard.oss.cssfx;

    opens io.github.gleidsonmt.speedcut to javafx.fxml;

    exports io.github.gleidsonmt.speedcut.core.app.exceptions;

    exports io.github.gleidsonmt.speedcut;
    exports io.github.gleidsonmt.speedcut.core.app.layout;
    opens io.github.gleidsonmt.speedcut.core.app.layout to javafx.fxml;
    exports io.github.gleidsonmt.speedcut.core.app;
    opens io.github.gleidsonmt.speedcut.core.app to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.controller to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.core.app.controller to javafx.fxml;
    exports io.github.gleidsonmt.speedcut.core.app.view;
    opens io.github.gleidsonmt.speedcut.core.app.view to javafx.fxml;
    exports io.github.gleidsonmt.speedcut.presenter;
    opens io.github.gleidsonmt.speedcut.presenter to javafx.fxml;

}