package g56583.atl.stibride;

import g56583.atl.stibride.model.Model;
import g56583.atl.stibride.model.config.ConfigManager;
import g56583.atl.stibride.presenter.Presenter;
import g56583.atl.stibride.view.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ConfigManager.getInstance().load();
        Model model = new Model();
        View view = new View(stage);
        Presenter presenter = new Presenter(model, view);
        presenter.initialize();
        view.addPresenter(presenter);
        model.addObserver(presenter);
    }
}
