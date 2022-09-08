
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

//    requires com.dlsc.formsfx;
    requires fr.brouillard.oss.cssfx;

    requires controlsfx;
    requires fxsvgimage;

    requires thumbnailator;

    requires javafx.swing;

    exports io.github.gleidsonmt.speedcut.core.app.exceptions;


    opens io.github.gleidsonmt.speedcut.core.app.model to javafx.base;

    opens io.github.gleidsonmt.speedcut.core.app.layout to javafx.fxml;

    opens io.github.gleidsonmt.speedcut.core.app.controller to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.controller to javafx.fxml;

    opens io.github.gleidsonmt.speedcut.controller.sales to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.controller.client to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.controller.form to javafx.fxml;

    opens io.github.gleidsonmt.speedcut.core.app.view to org.yaml.snakeyaml;
    opens io.github.gleidsonmt.speedcut.core.app.factory to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.core.app.layout.options to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.core.app.layout.util to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.core.app.layout.containers to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.core.app.factory.column to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.core.app.factory.row to javafx.fxml;
    exports io.github.gleidsonmt.speedcut.core.app;
    opens io.github.gleidsonmt.speedcut.core.app to javafx.fxml;
    exports io.github.gleidsonmt.speedcut;
    opens io.github.gleidsonmt.speedcut to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.controller.sales.main to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.controller.sales.index to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.controller.sales.side_views to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.controller.sales.main.componets to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.controller.sales.payment_layout to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.controller.sales.aside to javafx.fxml;
    opens io.github.gleidsonmt.speedcut.controller.login to javafx.fxml;
    exports io.github.gleidsonmt.speedcut.core.app.view.intefaces;
    opens io.github.gleidsonmt.speedcut.core.app.view.intefaces to javafx.fxml, org.yaml.snakeyaml;
    exports io.github.gleidsonmt.speedcut.core.app.view.core;
    opens io.github.gleidsonmt.speedcut.core.app.view.core to javafx.fxml, org.yaml.snakeyaml;




}