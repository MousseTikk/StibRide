package g56583.atl.stibride.view;

import g56583.atl.stibride.model.Model;
import g56583.atl.stibride.model.dto.StationDto;
import g56583.atl.stibride.presenter.Presenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.controlsfx.control.SearchableComboBox;

public class FxmlControllerFavorite {

    @FXML
    private SearchableComboBox<StationDto> searchOrigine;

    @FXML
    private SearchableComboBox<StationDto> searchDestination;

    @FXML
    private TextField name;

    @FXML
    private Button add;

    @FXML
    private Button delete;

    @FXML
    private Button search;

    @FXML
    private Text msg;
    private Presenter presenter;

    @FXML
    private void search(ActionEvent event) {
        presenter.search(searchOrigine.getValue(), searchDestination.getValue());
    }

    @FXML
    private void add(ActionEvent event) {
        presenter.addFavorite(searchOrigine.getValue(), searchDestination.getValue(), name.getText());
    }

    @FXML
    private void delete(ActionEvent event) {
        presenter.deleteFavorite(searchOrigine.getValue(), searchDestination.getValue(), name.getText());
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setMsgFavorite(String message) {
        if (message == null) {
            this.msg.setText("");
            return;
        }
        String fMsg;
        switch (message) {
            case Model.FAVORITE_ADD_FAIL_MAX:
                fMsg = "Vous avez atteint votre limite de trajet, merci d'en supprimer.";
                break;
            case Model.FAVORITE_REPO_ERROR:
                fMsg = "Une erreur est survenue, merci de vérifier votre saisie.";
                break;
            case Model.FAVORITE_DELETE_FAIL:
                fMsg = "Impossible de supprimer ce trajet.";
                break;
            case Model.INVALID_DATA:
                fMsg = "Les données indiquées sont invalides. Merci de réessayer.";
                break;
            case Model.FAVORITE_UPDATE_NO_CHANGES:
                fMsg = "Il n'y a pas eu de changement à mettre à jour.";
                break;
            default:
                fMsg = "";
        }
        this.msg.setText(fMsg);
    }

    public void setComboBox(StationDto source, StationDto destination) {
        searchOrigine.getSelectionModel().select(source);
        searchDestination.getSelectionModel().select(destination);
    }

    public void initialize(ObservableSet<StationDto> allStations) {
        searchOrigine.setItems(FXCollections.observableArrayList(allStations));
        searchDestination.setItems(FXCollections.observableArrayList(allStations));
        searchOrigine.getSelectionModel().selectFirst();
        searchDestination.getSelectionModel().selectLast();
    }
}
