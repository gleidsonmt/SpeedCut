/*
 *    Copyright (C) Gleidson Neves da Silveira
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.gleidsonmt.speedcut.core.app.util;

import animatefx.animation.*;
import io.github.gleidsonmt.gncontrols.GNFloatingButton;
import io.github.gleidsonmt.gncontrols.GNMonetaryField;
import io.github.gleidsonmt.gncontrols.GNRadioButton;
import io.github.gleidsonmt.gncontrols.GNTextBox;
import io.github.gleidsonmt.gncontrols.options.FieldType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/03/2022
 */
public class DiscountPopup extends PopOver  {

    private final GNFloatingButton cancel      = new GNFloatingButton();
    private final GNFloatingButton confirm     = new GNFloatingButton();
    private final GNTextBox field = new GNTextBox();

    private VBox box         = new VBox();
    private TextField textField   = new TextField();

    private Label discount;
    private Label total;
    private Label amount;
    private Label discountTotal;

    private final ToggleGroup group       = new ToggleGroup();
    private final ToggleGroup typeGroup   = new ToggleGroup();

    private Button add = null;
    private Spinner<Integer> unit = null;
    private EventHandler<ActionEvent> saveAction;

    public DiscountPopup(Label discountTotal, Label discount, Label total, Label amount, Button add) {
        this.discount = discount;
        this.discountTotal = discountTotal;
        this.total = total;
        this.amount = amount;
        this.add = add;

        this.unit = unit;
        this.getRoot().getStylesheets().add(DiscountPopup.class.getResource("/io.github.gleidsonmt.speedcut.core.app/theme/css/poplight.css").toExternalForm());

        this.setArrowLocation(ArrowLocation.LEFT_BOTTOM);
//        this.setArrowIndent(10);
//        this.setArrowSize(10);
        this.setCloseButtonEnabled(false);
        this.setHeaderAlwaysVisible(false);

        this.setHeaderAlwaysVisible(false);
        this.setContentNode(box);

        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);

        GridPane radios = new GridPane();
        radios.setAlignment(Pos.CENTER);
//        radios.setSpacing(5);
        GNRadioButton percentage = new GNRadioButton("Porcentagem");
        GNRadioButton value = new GNRadioButton("Dinheiro");
        percentage.setToggleGroup(group);
        value.setToggleGroup(group);
//        radios.getChildren().addAll(percentage, value);

        HBox types = new HBox();
        types.setAlignment(Pos.CENTER);
        GNRadioButton ac = new GNRadioButton("Acréscimo");
        GNRadioButton dec = new GNRadioButton("Decrescimo");
        ac.setToggleGroup(typeGroup);
        dec.setToggleGroup(typeGroup);

    //        types.getChildren().addAll(ac, dec);

        radios.add(percentage,0,0);
        radios.add(value, 1, 0);

        radios.add(ac, 0, 1);
        radios.add(dec, 1,1);

        GridPane.setHalignment(ac, HPos.LEFT);
        GridPane.setHalignment(dec, HPos.RIGHT);
        GridPane.setHalignment(percentage, HPos.LEFT);
//        GridPane.setHalignment(value, HPos.RIGHT);

        radios.setHgap(10);
        radios.setVgap(10);

        VBox content = new VBox();
        content.setSpacing(5);
        content.setAlignment(Pos.CENTER);
        Label info = new Label("Desconto: (R$) "); // continauar aqui
        info.setStyle("-fx-text-fill : -text-color");

        field.setPrefHeight(40);
        content.getChildren().addAll(info, field);
//        field.getStyleClass().add("field-outlined");

//        field.setEditor(new GNMonetaryField());
//        add.setStyle("-fx-font-weight : bold; ");


        GNMonetaryField monetary = new GNMonetaryField();
        monetary.setPrefHeight(40);

        field.getEditor().setAlignment(Pos.CENTER);
        monetary.setAlignment(Pos.CENTER);

        percentage.setSelected(true);
        info.setText("Desconto: (%) ");
        dec.setSelected(true);

        typeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (content.getChildren().contains(monetary)) {
                monetary.requestFocus();
            } else field.requestFocus();
        });

        monetary.setMaxWidth(150);
        field.setMaxWidth(150);

        percentage.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                info.setText("Desconto: (%) ");
                content.getChildren().remove(monetary);
                content.getChildren().add(field);
                field.requestFocus();
            } else {
                info.setText("Desconto: (R$) ");
                content.getChildren().remove(field);
                content.getChildren().add(monetary);
                monetary.positionCaret(monetary.getLength());
                monetary.requestFocus();
            }
        });

//            Mask.noSymbols(field, ",");
//            Mask.decimal(field);
//            Mask.maxField(field, 2);
//
//            Mask.maxField(monetary, 10);
//
//            monetary.valueProperty().addListener((observable, oldValue, newValue) -> {
//                BigDecimal val = MonetaryUtil.monetary(mon.getText());
//
//                if(newValue  != null) {
//                    if (newValue.doubleValue() >= val.doubleValue()) {
//                        monetary.getStyleClass().removeAll("surprise-ok");
//                        monetary.getStyleClass().addAll("surprise-error");
//                    } else {
//                        monetary.getStyleClass().removeAll("surprise-error");
//                        monetary.getStyleClass().addAll("surprise-ok");
//                    }
//                }
//            });
//
        HBox actions = new HBox();
        actions.setSpacing(10);
        actions.setAlignment(Pos.CENTER);

        SVGPath iconCancel = new SVGPath();
        iconCancel.setContent("M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zM4 12c0-4.42 3.58-8 8-8 1.85 0 3.55.63 4.9 1.69L5.69 16.9C4.63 15.55 4 13.85 4 12zm8 8c-1.85 0-3.55-.63-4.9-1.69L18.31 7.1C19.37 8.45 20 10.15 20 12c0 4.42-3.58 8-8 8z");
        iconCancel.setFill(Color.WHITE);
        cancel.setPrefSize(40, 40);
        confirm.setPrefSize(40, 40);

        cancel.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        cancel.setGraphic(iconCancel);

        field.setCount(2);

        confirm.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        SVGPath iconConfirm = new SVGPath();
        iconConfirm.setFill(Color.WHITE);
        iconConfirm.setContent("M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z");
        confirm.setGraphic(iconConfirm);

        confirm.getStyleClass().addAll("round");
        cancel.getStyleClass().addAll("round", "btn-grapefruit");

        actions.getChildren().addAll(confirm, cancel);

        confirm.setOnAction(event -> {
            StringBuilder builder = new StringBuilder();
//
            if(ac.isSelected()){
                builder.append("+");
                discountTotal.setText("Acréscimo Total:");
                discount.setStyle("-fx-font-weight : bold; -fx-text-fill : -mint; ");
            } else {
                builder.append("-");
                discountTotal.setText("Decréscimo Total:");
                discount.setStyle("-fx-font-weight : bold; -fx-text-fill : -grapefruit; ");
            }

            if(percentage.isSelected() && field.getEditor().getLength() > 0){
                builder.append(field.getText()).append("%");
                monetary.setText("");
            } else if(value.isSelected() && monetary.getLength() > 0) {
                field.setText("");
                System.out.println(MoneyUtil.get(monetary.getText()));
                builder.append(
                        monetary.getLength() < 1 ? "" : MoneyUtil.format(MoneyUtil.get(monetary.getText()))
                );
            } else {
                builder.delete(0, builder.length());
            }

            add.setText(builder.toString());
//                this.hide();

            if(percentage.isSelected()){
                if (field.getEditor().getLength() > 0){
                    discount.setText(field.getText() + "%");
                    amount.setText(MoneyUtil.format(calculateValue()));
                }
            } else {
                if(monetary.getLength() > 0) {
                    discount.setText(MoneyUtil.format(MoneyUtil.get(add.getText())));
                    amount.setText(MoneyUtil.format(calculateValue()));
                }
            }
        });

        Label title = new Label("Informar desconto");
        title.getStyleClass().addAll("h4");

        box.getChildren().addAll(title, radios, types, content, actions);

        cancel.setOnAction(event -> this.close());

        this.box.setPrefSize(300, 300);
    }

    private BigDecimal calculateValue() {
        // Getting values from view
        BigDecimal val=  MoneyUtil.get(total.getText());
        BigDecimal percent = MoneyUtil.get(add.getText());
        BigDecimal act = MoneyUtil.get(add.getText());

        BigDecimal result = null;

            if(add.getText().contains("+")) {
                if (add.getText().contains("%")) {
                    percent = percent.divide(new BigDecimal(100), MathContext.DECIMAL32);
                    result = val.add(val.multiply(percent)).setScale(2, RoundingMode.HALF_UP);
                } else {
                    result = act.add(val);
                }
            } else if(add.getText().contains("-")){
                if (add.getText().contains("%")){
                    percent = percent.divide(new BigDecimal(100), MathContext.DECIMAL32);
                    result = val.subtract(val.multiply(percent)).setScale(2, RoundingMode.HALF_UP);

                } else {
                    result = val.subtract(act);
                }
            }
            return result;
    }

    public void showAndWait(Node node) {

        Bounds bounds = node.localToScreen(node.getBoundsInLocal());
        this.show(node, bounds.getMinX() - 25, bounds.getMinY() -  46);
        field.requestFocus();
        confirm.setDefaultButton(true);
        cancel.setCancelButton(true);
        Node skinNode = this.getSkin().getNode();
//        this.show(node);
        Pulse animation = new Pulse(skinNode);
        animation.setSpeed(1.5);
        animation.play();

    }

    public void close() {
        confirm.setDefaultButton(false);
        cancel.setCancelButton(false);

        Node skinNode = this.getSkin().getNode();
        Pulse animation = new Pulse(skinNode);
        animation.setSpeed(1.5);
        animation.play();
        animation.getTimeline().setOnFinished(e -> {
            this.hide();
        });
    }
}
