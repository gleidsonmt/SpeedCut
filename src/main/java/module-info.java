
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

    requires controlsfx;


    exports io.github.gleidsonmt.speedcut.core.app.exceptions;

    exports io.github.gleidsonmt.speedcut;
    opens io.github.gleidsonmt.speedcut to javafx.fxml;

    opens io.github.gleidsonmt.speedcut.core.app.layout to javafx.fxml;

    opens io.github.gleidsonmt.speedcut.core.app.controller to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.controller to javafx.fxml;

    opens io.github.gleidsonmt.speedcut.core.app.view to org.yaml.snakeyaml;
    opens io.github.gleidsonmt.speedcut.core.app.factory to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.core.app.layout.options to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.core.app.layout.util to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.core.app.layout.containers to javafx.fxml;

}