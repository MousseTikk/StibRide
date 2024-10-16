package g56583.atl.stibride.presenter;

import g56583.atl.stibride.model.Model;
import g56583.atl.stibride.model.dto.StationDto;
import g56583.atl.stibride.model.repository.RepositoryException;
import g56583.atl.stibride.observer.Observable;
import g56583.atl.stibride.observer.Observer;
import g56583.atl.stibride.view.View;

import java.io.IOException;

public class Presenter implements Observer {
    private final Model model;
    private final View view;
    public Presenter(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    public void initialize() {
        view.initialize(model.getAllStations(), model.getAllFavorites());
    }
    public void search(StationDto source, StationDto destination) {
        model.setMsgFavorite(null);
        view.closeFavorite();
        model.search(source, destination);
    }
    public void showFavoriteStage(StationDto source, StationDto destination, String name) {
        model.setMsgFavorite(null);
        try {
            view.showFavorite(source, destination, name);
        } catch (IOException e) {
            throw new IllegalArgumentException("Impossible to load the FXML Controller for popup.");
        }
    }
    public void deleteFavorite(StationDto source, StationDto destination, String name) {
        model.setMsgFavorite(null);
        try {
            model.deleteFavorite(source, destination, name);
        } catch (RepositoryException e) {
            model.setMsgFavorite(Model.FAVORITE_REPO_ERROR);
            model.notifyObservers();
            return;
        }
        if (model.getMsgFavorite() == null) {
            view.closeFavorite();
        }
    }
    public void addFavorite(StationDto source, StationDto destination, String name) {
        model.setMsgFavorite(null);
        try {
            model.addFavorite(source, destination, name);
        } catch (RepositoryException e) {
            model.setMsgFavorite(Model.FAVORITE_REPO_ERROR);
            model.notifyObservers();
            return;
        }
        if (model.getMsgFavorite() == null) {
            view.closeFavorite();
        }
    }

    @Override
    public void update(Observable observable, Object arg) {
        view.setMenuFavorite(model.getAllFavorites());
        view.setPathStations(model.getPathStations());
        view.setNbStation(model.getPathStations().size());
        view.setStatusSearch(model.isDoneSearch());
        view.setMsgFavorite(model.getMsgFavorite());
        view.setChangeStation(model.getChangeStation());


    }
}
