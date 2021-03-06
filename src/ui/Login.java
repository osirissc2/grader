package ui;

import core.Home;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.Utils;

import java.util.Optional;

public class Login extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {

        Home h = new Home();
        final PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter Password Here");

        Dialog dialog = new Dialog();
        dialog.setTitle("Authentication");
        dialog.setHeaderText("Welcome to GradeSafe");

        ButtonType enterGradingPortal = new ButtonType("Enter Grading Portal", ButtonBar.ButtonData.APPLY.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(enterGradingPortal, ButtonType.CANCEL);

        final Button cancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.addEventFilter(ActionEvent.ACTION, event ->
                System.exit(0)
        );



        GridPane grid = Utils.buildGridPane();


        grid.add(new Label("Password: "), 0, 0);
        grid.add(passwordInput, 1, 0);

        Hyperlink importantInfo = new Hyperlink();
        importantInfo.setText("First Time Logging in?");
        importantInfo.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Welcome");
                alert.setHeaderText("First Time Logging In");
                alert.setContentText("If this is your first time logging in, " +
                                     "your initial password will be kept");
                alert.showAndWait();
            }
        });
        grid.add(importantInfo, 0, 1);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();


        if (result.isPresent()){
            Actions action = new Actions();
            String passwordText = passwordInput.getText();
            if (passwordText.contains(" ")){
                action.triggerAlert("Error Message",
                        "Authentication Error",
                        "Password cannot have spaces");
            } else if(h.login(passwordText)){
                action.goToDashBoard(stage);
            } else {
                action.triggerAlert("Error Message",
                        "Authentication Error",
                        "Invalid Password! Please try again");
            }
        }

    }
}