package com.template;

import com.template.controller.MainController;
import com.template.validation.DistroValidator;
import com.template.validation.IDistroValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        IDistroValidator distroValidator = new DistroValidator();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("main.fxml"));

        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == MainController.class) {
                return new MainController(distroValidator);
            }
            try {
                return controllerClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Scene scene = new Scene(loader.load(), 750, 550);

        stage.setTitle("DistroMVC");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
