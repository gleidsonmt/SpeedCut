
module io.github.gleidsonmt.speedcut {
    requires javafx.controls;
    requires javafx.fxml;


    opens io.github.gleidsonmt.speedcut to javafx.fxml;
    exports io.github.gleidsonmt.speedcut;
}