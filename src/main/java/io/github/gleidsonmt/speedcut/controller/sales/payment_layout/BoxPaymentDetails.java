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

package io.github.gleidsonmt.speedcut.controller.sales.payment_layout;

import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.core.app.model.Amount;
import io.github.gleidsonmt.speedcut.core.app.model.Card;
import io.github.gleidsonmt.speedcut.core.app.model.Transaction;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.math.BigDecimal;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/08/2022
 */
public class BoxPaymentDetails extends ScrollPane {

    private final VBox  body = new VBox();
    private final VBox  content = new VBox();
    private final Label lblTitle = new Label();
    private final Label total = new Label();

    private VBox boxCards;
    private VBox boxMoney;
    private VBox boxPix;
    private VBox boxTerm;

    private final ObjectProperty<BigDecimal> finalAmount = new SimpleObjectProperty<>();

    public BoxPaymentDetails() {
        VBox.setVgrow(this, Priority.ALWAYS);
        VBox.setMargin(this, new Insets(10,0,0,0));

        setFitToHeight(true);
        setFitToWidth(true);

        body.setSpacing(5);

        configLayout();
    }

    private void configLayout() {
        lblTitle.setText("Métodos de Pagamento");
        lblTitle.getStyleClass().add("h4");
        lblTitle.setStyle("-fx-font-weight : bold;");

        total.setText("TOtla");

        content.getChildren().add(lblTitle);

        setContent(content);

        VBox.setMargin(lblTitle, new Insets(0,0,5,0));

        body.setAlignment(Pos.TOP_CENTER);
        content.getChildren().add(body);

//        content.getChildren().add(total);

    }

    public void updateContent(Transaction transaction) {

        body.getChildren().clear();

        boolean hasCard = false;
        boolean hasMoney = false;
        boolean hasPix = false;
        boolean hasTerm = false;

        finalAmount.set(BigDecimal.ZERO);

        for (Amount amount : transaction.getAmounts()) {

            finalAmount.set(finalAmount.get().add(amount.getValue()));

            switch (amount.getPaymentType()) {
                case CARD -> {
                    if (!hasCard) {
                        boxCards = createCotainerDefault();
                        boxCards.setSpacing(5D);
                        boxCards.getChildren().add(0, createTitle("Cartões", Graphics.CARD));
                        body.getChildren().add(boxCards);
                    } else {
                        boxCards.getChildren().add(new Separator());
                    }
                    boxCards.getChildren().add(new CardGridCell(amount, (Card) amount.getItem()));
                    hasCard = true;
                }
                case MONEY -> {
                    if (!hasMoney) {
                        boxMoney = createCotainerDefault();
                        boxMoney.setSpacing(10D);
                        body.getChildren().add(boxMoney);
                    } else {
                        boxMoney.getChildren().add(new Separator());
                    }

                    boxMoney.getChildren().add(new MoneyCell(amount));
                    hasMoney = true;
                }

                case PIX -> {
                    if (!hasPix) {
                        boxPix = createCotainerDefault();
                        boxPix.setSpacing(5);
                        boxPix.getChildren().add(0, createTitle("Pix", Graphics.PIX));
                        body.getChildren().add(boxPix);
                    } else {
                        boxPix.getChildren().add(new Separator());
                    }
                    boxPix.getChildren().add(new PixCell(amount));
                    hasPix = true;
                }

                case TERM -> {
                    if (!hasTerm) {
                        boxTerm = createCotainerDefault();
                        boxTerm.setSpacing(5);
                        boxTerm.getChildren().add(0, createTitle("Conta Gerada", Graphics.TERM));
                        body.getChildren().add(boxTerm);
                    } else {
                        boxTerm.getChildren().add(new Separator());
                    }
                    boxTerm.getChildren().add(new TermCell(amount));
                    hasTerm = true;
                }

            }

        }

    }

    private VBox createCotainerDefault() {
        VBox vBox = new VBox();

        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.getStyleClass().add("border");
        vBox.setPadding(new Insets(20));

        return vBox;
    }

    private Label createTitle(String title, Graphics icon) {
        Label lblTitle = new Label(title);
        VBox.setMargin(lblTitle, new Insets(0,0,10,0));
        lblTitle.setGraphic(createIcon(icon));
        lblTitle.setStyle("-fx-text-fill : -text-color;");
        return lblTitle;
    }

    private Node createIcon(Graphics icon) {
        return createIcon(icon, true);
    }

    private Node createIcon(Graphics icon, boolean resize) {

        SVGPath _content = new SVGPath();
        _content.setContent(icon.content);
        _content.getStyleClass().add("icon");

        if (resize) {
            Group group = new Group();
            _content.setScaleX(0.03);
            _content.setScaleY(0.03);
            group.getChildren().add(0, _content);
            return group;
        } else return _content;

    }

    private enum Graphics {

        CARD("M527.9 32H48.1C21.5 32 0 53.5 0 80v352c0 26.5 21.5 48 48.1 48h479.8c26.6 0 48.1-21.5 48.1-48V80c0-26.5-21.5-48-48.1-48zM54.1 80h467.8c3.3 0 6 2.7 6 6v42H48.1V86c0-3.3 2.7-6 6-6zm467.8 352H54.1c-3.3 0-6-2.7-6-6V256h479.8v170c0 3.3-2.7 6-6 6zM192 332v40c0 6.6-5.4 12-12 12h-72c-6.6 0-12-5.4-12-12v-40c0-6.6 5.4-12 12-12h72c6.6 0 12 5.4 12 12zm192 0v40c0 6.6-5.4 12-12 12H236c-6.6 0-12-5.4-12-12v-40c0-6.6 5.4-12 12-12h136c6.6 0 12 5.4 12 12z"),
        TERM("M630.6 364.9l-90.3-90.2c-12-12-28.3-18.7-45.3-18.7h-79.3c-17.7 0-32 14.3-32 32v79.2c0 17 6.7 33.2 18.7 45.2l90.3 90.2c12.5 12.5 32.8 12.5 45.3 0l92.5-92.5c12.6-12.5 12.6-32.7.1-45.2zm-182.8-21c-13.3 0-24-10.7-24-24s10.7-24 24-24 24 10.7 24 24c0 13.2-10.7 24-24 24zm-223.8-88c70.7 0 128-57.3 128-128C352 57.3 294.7 0 224 0S96 57.3 96 128c0 70.6 57.3 127.9 128 127.9zm127.8 111.2V294c-12.2-3.6-24.9-6.2-38.2-6.2h-16.7c-22.2 10.2-46.9 16-72.9 16s-50.6-5.8-72.9-16h-16.7C60.2 287.9 0 348.1 0 422.3v41.6c0 26.5 21.5 48 48 48h352c15.5 0 29.1-7.5 37.9-18.9l-58-58c-18.1-18.1-28.1-42.2-28.1-67.9z"),
        MONEY("M14 13q-1.25 0-2.125-.875T11 10q0-1.25.875-2.125T14 7q1.25 0 2.125.875T17 10q0 1.25-.875 2.125T14 13Zm-7 3q-.825 0-1.412-.588Q5 14.825 5 14V6q0-.825.588-1.412Q6.175 4 7 4h14q.825 0 1.413.588Q23 5.175 23 6v8q0 .825-.587 1.412Q21.825 16 21 16Zm2-2h10q0-.825.587-1.413Q20.175 12 21 12V8q-.825 0-1.413-.588Q19 6.825 19 6H9q0 .825-.588 1.412Q7.825 8 7 8v4q.825 0 1.412.587Q9 13.175 9 14Zm11 6H3q-.825 0-1.412-.587Q1 18.825 1 18V7h2v11h17ZM7 14V6v8Z"),
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

        public String toString() {
            return super.name();
        }
    }


    public BigDecimal getFinalAmount() {
        return finalAmount.get();
    }

    public ObjectProperty<BigDecimal> finalAmountProperty() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount.set(finalAmount);
    }
}
