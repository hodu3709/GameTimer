package be.mormont.gametimer.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
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
//        spinnerDurationHour.valueProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == -1) {
//                spinnerDurationHour.increment();
//                spinnerDurationMin.decrement(60);
//            }
//        });
//        spinnerDurationMin.valueProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == 60) {
//                spinnerDurationHour.increment();
//                spinnerDurationMin.decrement(60);
//            } else if (newValue == -1) {
//
//            }
//        });
//        spinnerDurationSec.valueProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == 60) {
//                spinnerDurationMin.increment();
//                spinnerDurationSec.decrement(60);
//            }
//        });
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

    private SpinnerValueFactory.IntegerSpinnerValueFactory spinnerFactory(int min, int max, int initial) {
        return new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initial);
    }
}
