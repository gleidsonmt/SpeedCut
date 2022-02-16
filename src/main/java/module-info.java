
module io.github.gleidsonmt.speedcut {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires scenicView;
    requires io.github.gleidsonmt.gndecorator;

    opens io.github.gleidsonmt.speedcut to javafx.fxml;
    exports io.github.gleidsonmt.speedcut;
    exports io.github.gleidsonmt.speedcut.core.app.layout;
    opens io.github.gleidsonmt.speedcut.core.app.layout to javafx.fxml;
    exports io.github.gleidsonmt.speedcut.core.app;
    opens io.github.gleidsonmt.speedcut.core.app to javafx.fxml;

}