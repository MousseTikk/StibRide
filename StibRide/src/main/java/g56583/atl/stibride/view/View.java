package g56583.atl.stibride.view;

import g56583.atl.stibride.model.dto.FavoriteDto;
import g56583.atl.stibride.model.dto.StationDto;
import g56583.atl.stibride.presenter.Presenter;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class View {
    private final FxmlController ctrl;
    private FxmlControllerFavorite favoriteCtrl;
    private Stage favoriteStage;

    public View(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stib.fxml"));
        FxmlController controller = new FxmlController();
        loader.setController(controller);
        Pane root = loader.load();

        ctrl = controller;

        Scene scene = new Scene(root);

        stage.setResizable(false);
        stage.setTitle("HE2B ESI - Projet STIB");
        stage.setScene(scene);

        stage.show();
        makeFavoriteStage(stage);
    }
    private void makeFavoriteStage(Stage stage) throws IOException {
        favoriteStage = new Stage();
        favoriteStage.initModality(Modality.APPLICATION_MODAL);
        favoriteStage.initOwner(stage);
        FXMLLoader loaderFavorite = new FXMLLoader(getClass().getResource("/fxml/favorite.fxml"));
        FxmlControllerFavorite controllerFavorite = new FxmlControllerFavorite();
        loaderFavorite.setController(controllerFavorite);

        Pane rootFavorite = loaderFavorite.load();
        Scene sceneFavorite = new Scene(rootFavorite);
        favoriteCtrl = controllerFavorite;

        favoriteStage.setResizable(false);
        favoriteStage.setTitle("HE2B ESI - Gestion favoris");
        favoriteStage.setScene(sceneFavorite);
    }

    public void showFavorite(StationDto source, StationDto destination, String name) throws IOException {
        favoriteStage.show();
        favoriteCtrl.setComboBox(source, destination);
        favoriteCtrl.setName(name);
    }

    public void addPresenter(Presenter presenter) {
        ctrl.setPresenter(presenter);
        favoriteCtrl.setPresenter(presenter);
    }

    public void initialize(ObservableSet<StationDto> allStations, ObservableSet<FavoriteDto> allFavorites) {
        ctrl.initialize(allStations, allFavorites);
        favoriteCtrl.initialize(allStations);
    }

    public void closeFavorite() {
        favoriteStage.close();
    }

    public void setMsgFavorite(String msg) {
        favoriteCtrl.setMsgFavorite(msg);
    }

    public void setMenuFavorite(ObservableSet<FavoriteDto> allFavorites) {
        ctrl.setMenuFavorite(allFavorites);
    }

    public void setStatusSearch(boolean isEnd) {
        ctrl.getStatusSearch().setText(isEnd ? "Recherche terminée" : "Une erreur est survenue, merci de réessayer.");
    }

    public void setChangeStation(List<String> listStation) {
        ctrl.setChangeStation(listStation);
    }

    public void setNbStation(int nb) {
        ctrl.getNbStation().setText("Nombre de station : " + nb);
    }

    public void setPathStations(ObservableList<StationDto> newList) {
        ctrl.setPathStations(newList);
    }
}
