package io.github.gleidsonmt.speedcut.core.app.factory;

import io.github.gleidsonmt.gncontrols.GNButton;
import io.github.gleidsonmt.gncontrols.options.GNButtonType;
import io.github.gleidsonmt.speedcut.core.app.model.Entity;
import io.github.gleidsonmt.speedcut.core.app.model.Model;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.SVGPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  05/05/2022
 *
 * Needs a big refactoring
 */
@SuppressWarnings("unused")
public class Paginator<T extends Entity> extends GridPane {

    private static final int RANGE_DEFAULT = 4;


    /** Total de itens a ser paginado. **/
    private long totalItens;

//    private int itemsPerPage;

    /** Página atual sendo exibida. **/
    private int currentPage;

    /** Intervalo de páginas a ser mostrado antes e depois da página atual. **/
    private int range;

    /** Página posterior à página atual. **/
    private int nextPage;

    /** Página anterior à página atual. **/
    private int previousPage;

    /** Primeira página. **/
    private int firstPage;

    /** Última página. **/
    private int lastPage;

    /** Posição inicial do lote de registros sendo exibido na página. **/
    private int firstResult;

    /** Posição final do lote de registros sendo exibido na página. **/
    private int lastResult;

    /** Lista das páginas que ficam à esquerda da página atual. **/
    private List<Integer> leftPages;

    /** Lista das páginas que ficam à direita da página atual. **/
    private List<Integer> rightPages;

    /// FIxndo items
    private ToggleGroup     toggleGroup = new ToggleGroup();
    private HBox            pagContent = new HBox();

    private FilteredList<T> fullList ;
    private TableView<T>    tableView ;

    private final GNButton btnLeft = new GNButton("<");
    private final GNButton btnRight = new GNButton(">");

    private final Hyperlink btnFirst = new Hyperlink("Primeiro");
    private final Hyperlink btnLast = new Hyperlink("Último");

    private int currentIndex = 0;

    /** Total de itens a serem exibidos por página. **/
    private final IntegerProperty itemsPerPage = new SimpleIntegerProperty(10);

    private Comparator<T> comparator = (o1, o2) -> 0;

    public int getLimit() {
        return itemsPerPage.get();
    }

    public IntegerProperty limitProperty() {
        return itemsPerPage;
    }

    public void setLimit(int limit) {
        this.itemsPerPage.set(limit);
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * Construtor padrão, com as configurações padrão.
     */
    public Paginator(FilteredList<T> fullList, TableView<T> tableView) {

        this.currentPage = 1;
        this.range = RANGE_DEFAULT;

        this.fullList = fullList;
        this.tableView = tableView;

        btnRight.setButtonType(GNButtonType.ROUNDED);
        btnLeft.setButtonType(GNButtonType.ROUNDED);

        btnRight.setMaxSize(30,30);
        btnLeft.setMaxSize(30,30);

        btnLeft.getStyleClass().addAll("page-indi");
        btnRight.getStyleClass().addAll("page-indi");


        btnRight.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        btnLeft.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        SVGPath arrowRight = new SVGPath();
        arrowRight.setContent("M15.2 43.9 12.4 41.05 29.55 23.9 12.4 6.75 15.2 3.9 35.2 23.9Z");
        btnRight.setGraphic(new Group(arrowRight));

        SVGPath arrowLeft = new SVGPath();
        arrowLeft.setContent("M20 44 0 24 20 4 22.8 6.85 5.65 24 22.8 41.15Z");
        btnLeft.setGraphic(new Group(arrowLeft));

        setAlignment(Pos.CENTER);
        getStyleClass().add("paginator");
        setMinHeight(40);

    }

    /**
     * Inicializa o paginador com o total de itens real e a URL para troca de página.
     * @param totalItens total de itens
     */
    public void init(Long totalItens) {
        if (totalItens == null) this.totalItens = 0L;
        else this.totalItens = totalItens;

        limitProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.intValue() != 0) {
                refresh();

                if (newValue.intValue() > oldValue.intValue()) {
                    setItems(currentIndex, itemsPerPage.get());
                }
//                recreateToNext();
            }
        });

    }

    /**
     * Inicializa o paginador com o total de itens real e a URL para troca de página.
     * @param totalItens total de itens real.
     * @param itemsPerPage total de itens a serem exibidos em cada página.
     */
    public void init(Long totalItens, int itemsPerPage) {
        init(totalItens);
//        assertArgument(itemsPerPage > 0, "O total de itens por página deve ser maior que zero.");
        this.itemsPerPage .set(itemsPerPage);
    }

    public void refresh() {
//        setItems(0, 5);


        init((long) fullList.size());
        setItems(0, itemsPerPage.get());

        toggleGroup.getToggles().clear();
        pagContent.getChildren().clear();

        int total = fullList.size();
        int index = (int) Math.ceil(total/ (float) itemsPerPage.get()) +1;

        int size = Math.min(index,6);

        if (total == 0) return;
        if (index < 3) return;

        pagContent.getChildren().addAll(btnFirst, btnLeft);

        if (index > 6) {
            btnLast.setDisable(false);
            btnRight.setDisable(false);
        }

        for (int i = 1; i < size; i++) {
            pagContent.getChildren().add(createPage(i));
        }


        pagContent.getChildren().addAll(btnRight, btnLast);

        toggleGroup.selectToggle(
            toggleGroup.getToggles().get(0)
        );


//        tableView.getSelectionModel().selectFirst();
//
    }

    Label legend = new Label("Apresentando de 01 ate 10 de 158 registros.");


    @Deprecated
    private void updateLegend() {

        int inital = 0;
        int pageSize = tableView.getItems().size();
        int total = fullList.size();

        legend.setText(
                "Mostrando de " + inital + " até " + pageSize + " de " + total + "registros"
        );
    }

    private void altLegend(int init, int end, int total) {
        Platform.runLater(() -> {
            legend.setText(init + " - " + end + " de " + total + " entradas.");
        });
    }

    /**
     * Calcula e carrega todas as informações necessárias para a exibição da paginação.
     */
    public void load() {

        nextPage = calculateNextPage();
        previousPage = calculatePreviousPage();
        lastPage = calculateLastPage();
        firstResult = calculateFirstResult();
        lastResult = calculateLastResult();
        leftPages = calculateLeftPages();
        rightPages = calculateRightPages();


        getChildren().add(pagContent);
        getChildren().add(legend);

        GridPane.setConstraints(pagContent, 1,0,1,1, HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(legend, 0,0,1,1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

        pagContent.setAlignment(Pos.CENTER_RIGHT);

        refresh();

        btnLast.setOnAction(event -> {
            recreateToLast(calculateLastPage());
            disabelRightButtons(true);
            disabelLeftButtons(false);
        });

        btnFirst.setOnAction(event -> {
            recreateToFirst(calculateFirstResult(), 5);
            disabelRightButtons(false);
            disabelLeftButtons(true);
        });

        disabelLeftButtons(true);
        
        btnLeft.setOnAction(e -> {

            int d = toggleGroup.getToggles()
                    .indexOf(toggleGroup.getSelectedToggle());

            if (d == 0) {
                recreateToPrevious(currentIndex);
            } else {
                currentIndex--;
                toggleGroup.selectToggle(
                        toggleGroup.getToggles().get(currentIndex)
                );
            }
        });

        btnRight.setOnAction(e -> {

            int d = toggleGroup.getToggles()
                    .indexOf(toggleGroup.getSelectedToggle()) ;

            currentIndex++;

            if (currentIndex > 4) {

                recreateToNext();
//                currentPage = 1;

            } else {
//                currentIndex++;
                toggleGroup.selectToggle(
                        toggleGroup.getToggles().get(currentIndex)
                );
            }


        });

//        pagContent.getChildren().addAll(btnRight, btnLast);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentPage = Integer.parseInt(((ToggleButton) newValue).getText());

                int total = fullList.size();
                int index = (int) Math.ceil(total/5f) ;

                currentIndex = toggleGroup.getToggles().indexOf(newValue);

                rightPages = getRightPages();
                leftPages = getLeftPages();
                setItems(currentPage - 1, itemsPerPage.get());


                if (currentPage == index) {
                    btnLast.setDisable(true);
                    btnRight.setDisable(true);

                    btnFirst.setDisable(false);
                    btnLeft.setDisable(false);
                } else if (currentPage == 1) {
                    btnFirst.setDisable(true);
                    btnLeft.setDisable(true);
                    btnLast.setDisable(false);
                    btnRight.setDisable(false);
                } else {
                    btnLast.setDisable(false);
                    btnRight.setDisable(false);
                    btnLeft.setDisable(false);
                    btnFirst.setDisable(false);
                }

//                if (currentIndex == 0 && currentPage == (endList)) {
//                    btnFirst.setDisable(true);
//                    btnLeft.setDisable(true);
//                } else if (currentPage == 1) {
//                    disabelLeftButtons(true);
//                } else {
//                    disabelRightButtons(false);
//                    disabelLeftButtons(false);
//                }

            }
        });

        fullList.predicateProperty().addListener((observable, oldValue, newValue) -> refresh());


        fullList.getSource().addListener((ListChangeListener<T>) c -> {
            if (c.next()) {

                totalItens = fullList.size();

                if (c.wasAdded()) {

                    if (tableView.getItems().size() == 5) {
//                        lastPage = calculateLastPage();
//                        range = 5;
                        recreateToLast(calculateLastPage());
                        recreateToFirst(0, limitProperty().get());

//                        tableView.getItems().stream().sorted();

                        disabelRightButtons(true);

                    } else {
                        tableView.getItems().addAll(c.getAddedSubList());
//                        disabelRightButtons(false);
                    }

                } else if (c.wasRemoved()) {

                    System.out.println("hasNextPage() = " + hasNextPage());

                    if (hasNextPage()) {
                        tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());

                        totalItens = fullList.size();
                        T it = getSkipItem(getNextPage());

                        if (it != null)
                            tableView.getItems().add(it);

                        if (toggleGroup.getToggles().size() > calculateLastPage()) {
                            pagContent.getChildren().remove(toggleGroup.getToggles().size()-1);
                            toggleGroup.getToggles().remove(toggleGroup.getToggles().size()-1);
                        }

                        if (tableView.getItems().isEmpty()) {
                            if (!fullList.isEmpty())
                                toggleGroup.selectToggle(
                                        toggleGroup.getToggles().get(currentPage-2)
                                );

//                            tableView.getSelectionModel().selectLast();
                        }
                    } else {
                        tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
                        if (tableView.getItems().isEmpty()) {


                            toggleGroup.selectToggle(
                                    toggleGroup.getToggles().get(currentPage-2)
                            );

                            pagContent.getChildren().remove(toggleGroup.getToggles().size()-1);
                            toggleGroup.getToggles().remove(toggleGroup.getToggles().size()-1);

                            tableView.getSelectionModel().selectLast();

                        }
                    }

                }
            }
        });

//        fullList.getSource().sort(comparator);
//        refresh();

    }

    public T getSkipItem (int index) { // Pega o primeiro item lista anterior paginada

        setNextPage(calculateNextPage());

        List<T> subList = fullList.
                stream()
                .skip(((long) currentPage * getLimit()) -1)  // Equivalent to SQL's offset
                .limit(1).toList();

        return subList.get(0);

    }


    private void setItems( int index, int limit ) {

        ObservableList<T> data;

        fullList.getSource().sort( comparator );

        List<T> subList = fullList.
                stream()
                .skip((long) index * limit)  // Equivalent to SQL's offset
                .limit(limit).toList();

        data = FXCollections.observableArrayList(subList);

        tableView.setItems(data);
        tableView.getSelectionModel().selectFirst();

        int entries = getLimit();

        altLegend(
                index == 0 ? data.isEmpty() ? 0 : index + 1
                        : ((index + 1) * entries) - (entries - 1),
                index == 0 ? data.size() : (entries * currentIndex) + data.size(),
                fullList.size()
        );
    }


    private ToggleButton createPage(int value) {

        ToggleButton toggleButton = new ToggleButton(String.valueOf(value));
        toggleGroup.getToggles().addAll(toggleButton);

        toggleButton.getStyleClass().addAll("paginator-button");

        toggleButton.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            if (toggleButton.isSelected()) e.consume();
        });

        return toggleButton;
    }

    private void recreateToPrevious(int x) {

        String _index = ((ToggleButton) toggleGroup.getSelectedToggle()).getText();
        int index = Integer.parseInt(_index);

        int page = (int) Math.floor(index/5f);
        int max = (page * 5) ;
        int min = (max - 5) ;

        int size = Math.min((max - index), 4) ;


        if (min + 1 <= 0) return;

        pagContent.getChildren().clear();
        toggleGroup.getToggles().clear();

        pagContent.getChildren().addAll(btnFirst, btnLeft);

        for (int i = min+1; i < max+1; i++) {
            pagContent.getChildren().addAll(createPage(i));

        }

        pagContent.getChildren().addAll(btnRight, btnLast);
//
        toggleGroup.selectToggle(
                toggleGroup.getToggles().get(
                        4
                )
        );
    }

    private void recreateToLast(int init) {

        pagContent.getChildren().clear();
        toggleGroup.getToggles().clear();

        pagContent.getChildren().addAll(btnFirst, btnLeft);

        int total = fullList.size();
        int index = (int) Math.ceil(total/5f);

        int indice = (int) Math.ceil(index/5f);
        int max = (indice * 5) ;
        int min = (max - 5) ;
        int size = (index - min);

        for (int i = min+1; i < (index+1); i++) {
            pagContent.getChildren().addAll(createPage(i));
        }

        pagContent.getChildren().addAll(btnRight, btnLast);


        toggleGroup.selectToggle(
                toggleGroup.getToggles().get(
                        size-1
                )
        );
    }

    private void recreateToNext() {

        String _index = ((ToggleButton) toggleGroup.getSelectedToggle()).getText();
        int index = Integer.parseInt(_index) +1;

        pagContent.getChildren().clear();
        toggleGroup.getToggles().clear();

        pagContent.getChildren().addAll(btnFirst, btnLeft);


        int total = fullList.size();
        int fullIndex = (int) Math.ceil(total/5f);

//        int last = calculateLast();
        int page = (int) Math.ceil(index/5f);
        int max = (page * 5) ;
        int min = (max - 5) ;

        int size = Math.min((max - index), 4) ;

        int _size = 0;


        if ((fullIndex - index) < 4) { // nao existem mais listas
            int x = (max - fullIndex) ;

            _size = fullIndex+1;

            disabelRightButtons(true);
            for (int i = min+1; i < _size; i++) { // 12 < 2
                pagContent.getChildren().addAll(createPage(i));
            }

            if (max+5 > fullIndex) {
                disabelRightButtons(true);
            }
//            disabelRightButtons(true);
        } else { // existem mais listas
            _size = (index+size) +1;
            for (int i = min+1; i < _size; i++) { // 12 < 2
                pagContent.getChildren().addAll(createPage(i));
            }

            int endIndex = Integer.parseInt( ((ToggleButton)toggleGroup.getToggles().get(
                    toggleGroup.getToggles().size()-1
            )).getText());

            if (fullIndex == endIndex) {
//                disabelRightButtons(true);
//                disabelLeftButtons(false);
            }
        }
        pagContent.getChildren().addAll(btnRight, btnLast);

//        currentIndex = 0;
//
        toggleGroup.selectToggle(
                toggleGroup.getToggles().get(
                       0
                )
        );
    }

    private void recreateToFirst(int init, int end) {

        pagContent.getChildren().clear();
        toggleGroup.getToggles().clear();

        pagContent.getChildren().addAll(btnFirst, btnLeft);

        int indexTotal = (int) Math.ceil(fullList.size()/5f);

        int size = Math.min(indexTotal, 5);

        for (int i = 1; i <= size; i++) {
            pagContent.getChildren().addAll(createPage(i));
        }

        pagContent.getChildren().addAll(btnRight, btnLast);

        if (toggleGroup.getToggles().size() > 0)
            toggleGroup.selectToggle(
                    toggleGroup.getToggles().get(0)
            );
    }

    private void disabelRightButtons(boolean value) {
        btnRight.setDisable(value);
        btnLast.setDisable(value);
    }

    private void disabelLeftButtons(boolean value) {
        btnLeft.setDisable(value);
        btnFirst.setDisable(value);
    }


    /**
     * Verifica se existe uma página anterior.
     * @return <code>true</code> se tem página anterior, <code>false</code> caso contrário.
     */
    public boolean hasPreviousPage() {
        return currentPage > firstPage;
    }

    /**
     * Verifica se existe uma página posterior.
     * @return <code>true</code> se tem página posterior, <code>false</code> caso contrário.
     */
    public boolean hasNextPage() {
        return currentPage < lastPage ;
    }

    /**
     * Verifica se existem páginas à esquerda ocultas. Isto é, verifica se ainda existem mais páginas
     * à esquerda fora do intervalo definido para exibição.
     * @return <code>true</code> se existem páginas à esquerda ocultas, <code>false</code> caso contrário.
     */
    public boolean hasHiddenLeftPages() {
        return currentPage > (range + 1);
    }

    /**
     * Verifica se existem páginas à direita ocultas. Isto é, verifica se ainda existem mais páginas
     * à direita fora do intervalo definido para exibição.
     * @return <code>true</code> se existem páginas à direita ocultas, <code>false</code> caso contrário.
     */
    public boolean hasHiddenRightPages() {
        return currentPage < (lastPage - range);
    }

    /**
     * Calcula a próxima página.
     * @return a próxima página.
     */
    private int calculateNextPage() {
        return currentPage + 1;
    }

    /**
     * Calcula a página anterior.
     * @return a página anterior.
     */
    private int calculatePreviousPage() {
        return currentPage - 1;
    }

    /**
     * Calcula a última página.
     * @return calcula a última página.
     */
    private int calculateLastPage() {
        if (totalItens == 0L) return 0;
        int residue = (int) totalItens % itemsPerPage.get();
        int result = (int) totalItens / itemsPerPage.get();
        if (residue == 0L) return result;
        return result + 1;
    }

    /**
     * Calcula o primeiro registro do lote a ser exibido.
     * @return o primeiro resultado.
     */
    private int calculateFirstResult() {
        if (currentPage == firstPage) return currentPage;
        return (previousPage * itemsPerPage.get()) + 1;
    }

    /**
     * Calcula o último registro do lote a ser exibido.
     * @return o último resultado.
     */
    private int calculateLastResult() {
        if (totalItens == 0L) return 0;
        if (currentPage == firstPage) return itemsPerPage.get();
        if (currentPage == lastPage) return (int) totalItens;
        return firstResult + itemsPerPage.get() - 1;
    }

    private int calculateLast() {
        if (fullList.size() == 0L) return 0;
        int residue = fullList.size() % itemsPerPage.get();
        int result = fullList.size() / itemsPerPage.get();
        if (residue == 0L) return result;
        return result + 1;
    }

    /**
     * Calcula quais serão as páginas à esquerda da página atual.
     * @return uma lista de páginas.
     */
    private List<Integer> calculateLeftPages() {
        if (currentPage == firstPage) return new ArrayList<>();

        List<Integer> pages = new ArrayList<Integer>();
        int pageNumber = currentPage;
        for (int i = 0; i < range; i++) {
            pageNumber--;
            if (pageNumber <= 0) break;
            pages.add(pageNumber);
        }

        Collections.reverse(pages);
        return pages;
    }

    /**
     * Calcula quais serão as páginas à direita da página atual.
     * @return uma lista de páginas.
     */
    private List<Integer> calculateRightPages() {
        if (currentPage == lastPage) return new ArrayList<>();

        List<Integer> pages = new ArrayList<>();
        int pageNumber = currentPage;
        for (int i = 0; i < range; i++) {
            pageNumber++;
            if (pageNumber > lastPage) break;
            pages.add(pageNumber);
        }
        return pages;
    }

    public int getFirstResultAsIndex() {
        return firstResult - 1;
    }

    public long getTotalItens() {
        return totalItens;
    }

    public void setTotalItens(long totalItens) {
        this.totalItens = totalItens;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setLastResult(int lastResult) {
        this.lastResult = lastResult;
    }

    public int getLastResult() {
        return lastResult;
    }

    public void setLeftPages(List<Integer> leftPages) {
        this.leftPages = leftPages;
    }

    public List<Integer> getLeftPages() {
        return leftPages;
    }

    public void setRightPages(List<Integer> rightPages) {
        this.rightPages = rightPages;
    }

    public List<Integer> getRightPages() {
        return rightPages;
    }

}
