package be.mormont.gametimer.ui;

import be.mormont.gametimer.data.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Date: 10-01-19
 * By  : Mormont Romain
 */
public class CreateTimersFormController implements Initializable {
    @FXML public Label formTitleLabel;
    @FXML public Label timerTypeLabel;
    @FXML public Label timerDurationLabel;
    @FXML public Spinner<Integer> spinnerDurationHour;
    @FXML public Spinner<Integer> spinnerDurationMin;
    @FXML public Spinner<Integer> spinnerDurationSec;
    @FXML public ComboBox<TimerType> timerTypeCombo;
    @FXML public Label playerNameLabel;
    @FXML public Label playerColorLabel;
    @FXML public ColorPicker playerColorPicker;
    @FXML public Label playerPanelTitle;
    @FXML public TextField playerNameField;
    @FXML public Button deletePlayerButton;
    @FXML public Button newPlayerButton;
    @FXML public ListView<Player> playerListView;
    @FXML public Button createButton;

    private ObservableList<Player> players;
    private CreatePlayersHandler handler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        formTitleLabel.setText("Create a timer");
        timerTypeLabel.setText("Timer type");
        timerTypeCombo.setItems(FXCollections.observableArrayList(TimerType.values()));
        timerTypeCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            boolean disable = newValue == TimerType.COUNT_UP;
            spinnerDurationHour.setDisable(disable);
            spinnerDurationMin.setDisable(disable);
            spinnerDurationSec.setDisable(disable);
        });
        timerTypeCombo.setValue(TimerType.COUNT_UP);
        timerDurationLabel.setText("Duration");
        spinnerDurationHour.setValueFactory(spinnerFactory(0, 9999, 1));
        spinnerDurationMin.setValueFactory(spinnerFactory(0, 59, 0));
        spinnerDurationSec.setValueFactory(spinnerFactory(0, 59, 0));

        playerPanelTitle.setText("Player");
        playerNameLabel.setText("Name");
        playerColorLabel.setText("Color");

        // list view
        players = FXCollections.observableArrayList();
        playerListView.setItems(players);
        playerListView.selectionModelProperty().getValue().setSelectionMode(SelectionMode.SINGLE);

        playerListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            deletePlayerButton.setDisable(newValue == null);
            if (oldValue != null) {
                oldValue.playerNameProperty().unbindBidirectional(playerNameField.textProperty());
                oldValue.colorProperty().unbindBidirectional(playerColorPicker.valueProperty());
            }
            if (newValue != null) {
                playerNameField.setText(newValue.getPlayerName());
                newValue.playerNameProperty().bindBidirectional(playerNameField.textProperty());
                playerColorPicker.setValue(newValue.getColor());
                newValue.colorProperty().bindBidirectional(playerColorPicker.valueProperty());
            }
        });
        playerListView.setCellFactory(param -> new PlayerListViewCell());

        // buttons
        deletePlayerButton.setText("Delete");
        deletePlayerButton.setDisable(true);
        deletePlayerButton.setOnMouseClicked(event -> {
            int selected = playerListView.getSelectionModel().getSelectedIndex();
            if (selected == -1) {
                return;
            }
            playerListView.getItems().remove(selected);
        });

        newPlayerButton.setText("New");
        newPlayerButton.setOnMouseClicked(event -> {
            int size = players.size();
            players.add(new Player("player" + (size + 1)));
            playerListView.getSelectionModel().select(size);
        });

        createButton.setText("Create");
        createButton.setOnMouseClicked(event -> {
            handler.handle(new ArrayList<>(players));
            FXMLModalHelper.closeModal(createButton.getParent());
        });
    }

    public void setHandler(CreatePlayersHandler handler) {
        this.handler = handler;
    }

    private SpinnerValueFactory.IntegerSpinnerValueFactory spinnerFactory(int min, int max, int initial) {
        return new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initial);
    }

    public class PlayerListViewCell extends ListCell<Player>
    {
        @Override
        protected void updateItem(Player item, boolean empty) {
            super.updateItem(item, empty);
            if(item == null || empty) {
                setGraphic(null);
                return;
            }
            Pair<Parent, PlayerListViewCellController> cell = FXMLBuilder.build("/be/mormont/gametimer/ui/player-list-view-cell.fxml");
            cell.getValue().setPlayer(item);
            setGraphic(cell.getKey());
        }
    }

    public enum TimerType {
        COUNT_UP("count up"), COUNT_DOWN("count down");

        private String label;

        TimerType(String label) {
            this.label = label;
        }

        public String toString() {
            return label;
        }
    }

    public interface CreatePlayersHandler {
        void handle(List<Player> players);
    }

}
