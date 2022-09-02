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

package io.github.gleidsonmt.speedcut.controller.sales.main.componets;

import io.github.gleidsonmt.gncontrols.GNMonetaryField;
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.controller.PaymentType;
import io.github.gleidsonmt.speedcut.controller.sales.payment_layout.PaymentContent;
import io.github.gleidsonmt.speedcut.controller.sales.main.SalesController;
import io.github.gleidsonmt.speedcut.controller.sales.payment_layout.PixCell;
import io.github.gleidsonmt.speedcut.core.app.animations.Animations;
import io.github.gleidsonmt.speedcut.core.app.model.*;
import io.github.gleidsonmt.speedcut.core.app.util.MoneyUtil;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  11/04/2022
 */
public class PaymentBox extends VBox implements Context {

    private final GNMonetaryField monetaryField     = new GNMonetaryField();
    private final GridPane        content           = new GridPane();
    private final ToggleGroup     group             = new ToggleGroup();
    private final ToggleGroup     cardGroup         = new ToggleGroup();
    private final ToggleGroup     pixesGroup        = new ToggleGroup();

    private final ToggleButton btnPix;
    private final ToggleButton btnMoney;
    private final ToggleButton btnCard;
    private final ToggleButton btnTerm;

    private final PaymentContent boxPay = new PaymentContent(
            PaymentType.CARD, createIcon(Graphics.CARD), "Selecione o cartão."
    );

    private int cols = 0;

    public PaymentBox() {

        getChildren().add(content);
        VBox.setVgrow(content, Priority.ALWAYS);

        boxPay.getStyleClass().add("border");

        createBoxContent();

        btnPix = createButton(2, 0, HPos.RIGHT, "Pix", PaymentType.PIX);
        btnMoney = createButton(2, 1,  HPos.RIGHT, "Dinheiro", PaymentType.MONEY);
        btnCard = createButton(0,0,  HPos.LEFT, "Selecione o cartão.", PaymentType.CARD);
        btnTerm = createButton(0,1,  HPos.LEFT, "Nota a Prazo", PaymentType.TERM);

        Animations.onHoverButton(btnPix);
        Animations.onHoverButton(btnMoney);
        Animations.onHoverButton(btnCard);
        Animations.onHoverButton(btnTerm);

        content.setPadding(new Insets(5));
        content.getStyleClass().addAll("border", "border-t-1");

        btnCard.setGraphic(createIcon(Graphics.CARD));
        btnTerm.setGraphic(createIcon(Graphics.TERM));

        content.getChildren().add(monetaryField);
        monetaryField.setAlignment(Pos.CENTER);
        monetaryField.setStyle("-fx-font-size : 14pt; -fx-border-radius : 0;");

        monetaryField.setMaxHeight(110);
        monetaryField.setPrefHeight(110);

        GridPane.setConstraints(
                monetaryField,
                1,0,1,2,
                HPos.CENTER,
                VPos.CENTER,
                Priority.ALWAYS, Priority.ALWAYS
        );

        btnPix.setGraphic(createIcon(Graphics.PIX));
        btnMoney.setGraphic(new IconContainer(Icons.MONETARY));

        content.setHgap(5);
        this.setSpacing(5D);

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!= null) {
//                SalesController salesController = (SalesController) context.getRoutes().getView("sales").getController();
//
//                if (newValue.getUserData() != null && salesController != null && salesController.getSaleSelected() != null) {
//
//                    PaymentType paymentType = (PaymentType) newValue.getUserData();
//
//                    if (!getChildren().contains(boxPay)) {
//                        getChildren().add(boxPay);
//                    }
//
//                    if (paymentType.equals(PaymentType.CARD) ) {
//                        updateContent(Graphics.CARD, "Selecionar Cartões", paymentType, salesController.getSaleSelected().getClient());
//
//                    } else if (paymentType.equals(PaymentType.PIX)) {
//                        updateContent(Graphics.PIX, "Selecionar Pix", paymentType, salesController.getSaleSelected().getClient());
//                    } else {
//                        getChildren().remove(boxPay);
//                    }
//                }
            }
        });

        group.selectToggle(btnMoney);
    }

    public void flush(@NotNull Sale sale) {

        btnCard.setDisable(!sale.getClient().isPayWithCard());
        btnTerm.setDisable(!sale.getClient().isPayWithTerm());
        btnPix.setDisable(!sale.getClient().isPayWithPix());

        group.selectToggle(btnMoney);

    }

    private @NotNull ToggleButton createButton(int col, int row, HPos alignment, String text, PaymentType payCont) {

        ToggleButton button = new ToggleButton(text);
        button.setPrefWidth(80);
        button.setMinHeight(50);

        button.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (button.isSelected())
                event.consume();
        });

        button.setUserData(payCont);

        group.getToggles().add(button);

        button.setMaxWidth(Double.MAX_VALUE);
        button.getStyleClass().add("btn-payments");
        button.setStyle("-fx-background-radius : 3;");
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        content.getChildren().add(button);

        GridPane.setConstraints(
                button,
                col, row, 1,1,
                alignment,
                VPos.CENTER,
                Priority.SOMETIMES, Priority.SOMETIMES
        );

        cols++;
        if (cols > 2) {
            cols = 0;
        }

        return button;
    }

    private @NotNull Group createIcon(@NotNull Graphics icon) {

        Group group = new Group();

        SVGPath _content = new SVGPath();
        _content.setContent(icon.content);
        _content.getStyleClass().add("icon");

        _content.setScaleX(0.03);
        _content.setScaleY(0.03);
        group.getChildren().add(0, _content);

        return group;
    }

    public BigDecimal getValue() {
        return MoneyUtil.get(monetaryField.getText());
    }

    public Card getCard() {
        return (Card) cardGroup.getSelectedToggle().getUserData();
    }

    public Pix getPix() {
        return (Pix) pixesGroup.getSelectedToggle().getUserData();
    }

    public PaymentType getPaymentType() {
        Object userData = group.getSelectedToggle().getUserData();
        if (userData == null) {
            return PaymentType.MONEY;
        }
        return ((PaymentType ) userData);
    }

    public void setValue(String value) {
        monetaryField.setText(value);
        monetaryField.requestFocus();
        monetaryField.selectAll();
    }

    private void updateContent(Graphics icon, String text, PaymentType paymentType, @NotNull Client client) {

        cardGroup.getToggles().clear();
        boxPay.getChildren().remove(2, boxPay.getChildren().size());


        boxPay.setText(text);
        boxPay.setIcon(createIcon(icon));

        for (PaymentMethod paymentMethod : client.getPaymentMethods() ) {

            switch (paymentType) {

                case CARD -> {
                    if (paymentMethod instanceof Cards) {

                        for (Card card : ((Cards) paymentMethod).getCards()) {

                            RadioButton radio = createRadioCard(card.getName(), card.getCvc());
                            radio.setUserData(card);
                            cardGroup.getToggles().add(radio);
                            boxPay.getChildren().add(radio);
                            cardGroup.selectToggle(radio);

                        }

                    }
                }
                case PIX -> {
                    if (paymentMethod instanceof Pixes) {

                        for (Pix pix : ((Pixes) paymentMethod).getPixes()) {

                            RadioButton radio = createRadioPix(pix.getKey());
                            radio.setUserData(pix);
                            pixesGroup.getToggles().add(radio);
                            boxPay.getChildren().add(radio);
                            pixesGroup.selectToggle(radio);

                        }
                    }
                }


            }


        }
    }

    private @NotNull RadioButton createRadioPix(String text) {

        RadioButton radioButton = new RadioButton(text);
        radioButton.setStyle("-fx-border-radius : 10px; -fx-font-weight : bold;");
        radioButton.setMinHeight(30);
        radioButton.setPrefWidth(Double.MAX_VALUE);
        radioButton.setPadding(new Insets(0));
        radioButton.setStyle("-fx-border-radius : 10px;");

        SVGPath icon = new SVGPath();
        icon.setStyle("-fx-fill : -text-color;");

        if (text.contains("@")) {
            icon.setContent(PixCell.Graphics.MAIL.getContent());
        } else if (text.contains(".")) {
            icon.setContent(PixCell.Graphics.CPF.getContent());
        } else icon.setContent(PixCell.Graphics.PHONE.getContent());

        radioButton.setGraphic(icon);

        return radioButton;
    }

    private @NotNull RadioButton createRadioCard(String text, int cvc) {

        RadioButton radioButton = new RadioButton(text);
        radioButton.setMinHeight(40);
        radioButton.setPrefWidth(Double.MAX_VALUE);
        radioButton.setPrefHeight(40);
        radioButton.setPadding(new Insets(0));
        radioButton.setStyle("-fx-border-radius : 10px; -fx-font-weight : bold;");

        HBox iconBox = new HBox();
        iconBox.setPadding(new Insets(0,10,0,10));
        StackPane iconContent = new StackPane();

        Label lblCvc = new Label(String.valueOf(cvc));
        lblCvc.setPadding(new Insets(5));
        lblCvc.setPrefSize(50,30);
        lblCvc.setAlignment(Pos.BOTTOM_RIGHT);
        lblCvc.setStyle("-fx-background-color : -info;" +
                "-fx-background-radius : 5;" +
                "-fx-font-size : 8;" +
                "-fx-text-fill : white;");

        Label lblPoint = new Label(".. ..");
        lblPoint.setPadding(new Insets(0,5,0,5));
        lblPoint.setPrefSize(50,20);
        lblPoint.setStyle("-fx-text-fill : white;");

        iconContent.getChildren().setAll(lblCvc, lblPoint);
        iconBox.getChildren().setAll(iconContent);
        radioButton.setGraphic(iconBox);

        return radioButton;
    }

    private void createBoxContent () {



    }

    public void setValue(BigDecimal newValue) {
        monetaryField.setText(MoneyUtil.format(newValue));
        monetaryField.requestFocus();
    }

    private enum Graphics {

        CARD("M527.9 32H48.1C21.5 32 0 53.5 0 80v352c0 26.5 21.5 48 48.1 48h479.8c26.6 0 48.1-21.5 48.1-48V80c0-26.5-21.5-48-48.1-48zM54.1 80h467.8c3.3 0 6 2.7 6 6v42H48.1V86c0-3.3 2.7-6 6-6zm467.8 352H54.1c-3.3 0-6-2.7-6-6V256h479.8v170c0 3.3-2.7 6-6 6zM192 332v40c0 6.6-5.4 12-12 12h-72c-6.6 0-12-5.4-12-12v-40c0-6.6 5.4-12 12-12h72c6.6 0 12 5.4 12 12zm192 0v40c0 6.6-5.4 12-12 12H236c-6.6 0-12-5.4-12-12v-40c0-6.6 5.4-12 12-12h136c6.6 0 12 5.4 12 12z"),
        TERM("M630.6 364.9l-90.3-90.2c-12-12-28.3-18.7-45.3-18.7h-79.3c-17.7 0-32 14.3-32 32v79.2c0 17 6.7 33.2 18.7 45.2l90.3 90.2c12.5 12.5 32.8 12.5 45.3 0l92.5-92.5c12.6-12.5 12.6-32.7.1-45.2zm-182.8-21c-13.3 0-24-10.7-24-24s10.7-24 24-24 24 10.7 24 24c0 13.2-10.7 24-24 24zm-223.8-88c70.7 0 128-57.3 128-128C352 57.3 294.7 0 224 0S96 57.3 96 128c0 70.6 57.3 127.9 128 127.9zm127.8 111.2V294c-12.2-3.6-24.9-6.2-38.2-6.2h-16.7c-22.2 10.2-46.9 16-72.9 16s-50.6-5.8-72.9-16h-16.7C60.2 287.9 0 348.1 0 422.3v41.6c0 26.5 21.5 48 48 48h352c15.5 0 29.1-7.5 37.9-18.9l-58-58c-18.1-18.1-28.1-42.2-28.1-67.9z"),
        CHEQUE("M0 448c0 17.67 14.33 32 32 32h576c17.67 0 32-14.33 32-32V128H0v320zm448-208c0-8.84 7.16-16 16-16h96c8.84 0 16 7.16 16 16v32c0 8.84-7.16 16-16 16h-96c-8.84 0-16-7.16-16-16v-32zm0 120c0-4.42 3.58-8 8-8h112c4.42 0 8 3.58 8 8v16c0 4.42-3.58 8-8 8H456c-4.42 0-8-3.58-8-8v-16zM64 264c0-4.42 3.58-8 8-8h304c4.42 0 8 3.58 8 8v16c0 4.42-3.58 8-8 8H72c-4.42 0-8-3.58-8-8v-16zm0 96c0-4.42 3.58-8 8-8h176c4.42 0 8 3.58 8 8v16c0 4.42-3.58 8-8 8H72c-4.42 0-8-3.58-8-8v-16zM624 32H16C7.16 32 0 39.16 0 48v48h640V48c0-8.84-7.16-16-16-16z"),
        PIX("M112.57 391.19c20.056 0 38.928-7.808 53.12-22l76.693-76.692c5.385-5.404 14.765-5.384 20.15 0l76.989 76.989c14.191 14.172 33.045 21.98 53.12 21.98h15.098l-97.138 97.139c-30.326 30.344-79.505 30.344-109.85 0l-97.415-97.416h9.232zm280.068-271.294c-20.056 0-38.929 7.809-53.12 22l-76.97 76.99c-5.551 5.53-14.6 5.568-20.15-.02l-76.711-76.693c-14.192-14.191-33.046-21.999-53.12-21.999h-9.234l97.416-97.416c30.344-30.344 79.523-30.344 109.867 0l97.138 97.138h-15.116z M22.758 200.753l58.024-58.024h31.787c13.84 0 27.384 5.605 37.172 15.394l76.694 76.693c7.178 7.179 16.596 10.768 26.033 10.768 9.417 0 18.854-3.59 26.014-10.75l76.989-76.99c9.787-9.787 23.331-15.393 37.171-15.393h37.654l58.3 58.302c30.343 30.344 30.343 79.523 0 109.867l-58.3 58.303H392.64c-13.84 0-27.384-5.605-37.171-15.394l-76.97-76.99c-13.914-13.894-38.172-13.894-52.066.02l-76.694 76.674c-9.788 9.788-23.332 15.413-37.172 15.413H80.782L22.758 310.62c-30.344-30.345-30.344-79.524 0-109.868");

        private final String content;

        Graphics(String content) {
            this.content = content;
        }

        public String getContent() {
            return this.content;
        }

        public static Icons get(String name) {
            Icons icon = null;
            for (Icons _icon : Icons.values()) {
                if (_icon.name().equals(name.toUpperCase())) {
                    icon = _icon;
                }
            }
            return icon;
        }

        @Contract(pure = true)
        public @NotNull String toString() {
            return super.name();
        }
    }
}
