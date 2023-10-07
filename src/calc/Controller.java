package calc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
    private CalculatorState calculatorState;

    @FXML
    private Controller controller;

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        debug.setText("by @sVIKs & JavaFX " + javafxVersion + " & Java " + javaVersion + " on MacOS");

        calculatorState = new CalculatorState();
        String[] callc = calculatorState.enterCmd("zero", "0");
        updateScreen(callc[0]);
        updateHistory(callc[1]);
    }

    public void updateScreen(String expression) {
        screen.setText(expression);
    }

    public void updateHistory(String expression) {
        history.setText(expression);
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        String[] callc = calculatorState.enterCmd(clickedButton.getId(), clickedButton.getText());
        updateScreen(callc[0]);
        updateHistory(callc[1]);
    }


    @FXML
    private Label screen;
    @FXML
    private Label debug;
    @FXML
    private Label history;
    @FXML
    private Button one;
    @FXML
    private Button two;
    @FXML
    private Button three;
    @FXML
    private Button four;
    @FXML
    private Button five;
    @FXML
    private Button six;
    @FXML
    private Button seven;
    @FXML
    private Button eight;
    @FXML
    private Button nine;
    @FXML
    private Button zero;
    @FXML
    private Button equals;
    @FXML
    private Button reset;
    @FXML
    private Button point;
    @FXML
    private Button inversion;
    @FXML
    private Button multiply;
    @FXML
    private Button minus;
    @FXML
    private Button plus;
    @FXML
    private Button delete;
    @FXML
    private Button divide;
}