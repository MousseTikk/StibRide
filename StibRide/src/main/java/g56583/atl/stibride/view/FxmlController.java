package g56583.atl.stibride.view;

import g56583.atl.stibride.model.dto.FavoriteDto;
import g56583.atl.stibride.model.dto.StationDto;
import g56583.atl.stibride.presenter.Presenter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.SearchableComboBox;

import java.util.List;

public class FxmlController {
    @FXML
    private TableView<StationDto> table;

    @FXML
    private TableColumn<StationDto, String> lineCol;

    @FXML
    private TableColumn<StationDto, String> stationCol;

    @FXML
    private Label nbStation;

    @FXML
    private Label statusSearch;

    @FXML
    private Label chgLine;

    @FXML
    private Button search;

    @FXML
    private Button favorite;

    @FXML
    private Menu menuFavorite;

    @FXML
    private MenuItem quit;

    @FXML
    private SearchableComboBox<StationDto> searchOrigine;

    @FXML
    private SearchableComboBox<StationDto> searchDestination;
    private ObservableList<StationDto> pathStations;
    private Presenter presenter;

    @FXML
    private void search(ActionEvent event) {
        presenter.search(searchOrigine.getValue(), searchDestination.getValue());
    }

    @FXML
    private void favorite(ActionEvent event) {
        presenter.showFavoriteStage(searchOrigine.getValue(), searchDestination.getValue(), "");
    }

    public Label getStatusSearch() {
        return statusSearch;
    }

    public Label getNbStation() {
        return nbStation;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void setPathStations(ObservableList<StationDto> newPath) {
        pathStations.setAll(newPath);
    }

    public void setChangeStation(List<String> changeStation) {
        var changements = new StringBuilder("Changement de ligne : ");
        if (changeStation.isEmpty()) {
            changements.append("aucun");
            chgLine.setText(changements.toString());
            return;
        }

        changeStation.forEach(station -> {
            changements.append(station).append(", ");
        });
        chgLine.setText(changements.substring(0, changements.length() - 2));
    }

    public void setMenuFavorite(ObservableSet<FavoriteDto> allFavorites) {
        menuFavorite.getItems().clear();
        if (allFavorites.isEmpty()) {
            MenuItem menuItem = new MenuItem("Aucun trajet pour l'instant");
            menuFavorite.getItems().add(menuItem);
            menuItem.setDisable(true);
        } else {
            allFavorites.forEach(v -> {
                var item = new MenuItem(v.getKey());
                item.setOnAction(e -> presenter.showFavoriteStage(v.getSource(), v.getDestination(), v.getKey()));
                menuFavorite.getItems().add(item);
            });
        }
    }

    public void initialize(ObservableSet<StationDto> allStations, ObservableSet<FavoriteDto> allFavorites) {
        pathStations = FXCollections.observableArrayList();
        stationCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lineCol.setCellValueFactory(new PropertyValueFactory<>("linesToString"));
        table.setItems(pathStations);

        searchOrigine.setItems(FXCollections.observableArrayList(allStations));
        searchDestination.setItems(FXCollections.observableArrayList(allStations));
        searchOrigine.getSelectionModel().selectFirst();
        searchDestination.getSelectionModel().selectLast();

        setMenuFavorite(allFavorites);

        quit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });
    }
}