package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class MainController {
    @FXML
    private TableView<DistroDTO> tblDistro;

    @FXML
    private Button btnRegister;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtBase;

    @FXML
    private TextField txtPM;

    @FXML
    private TextField txtEnvironment;

    @FXML
    private void initialize() {
        System.out.println("FXML loaded successfully!");
    }

    @FXML
    private void loadDistro() {
        DistroDAO objDistroDAO = new DistroDAO();
        ArrayList<DistroDTO> distrosList = objDistroDAO.selectDistro();
        tblDistro.setItems(FXCollections.observableArrayList(distrosList));
    }

    @FXML
    private void btnRegisterAction(ActionEvent event) {
        DistroDTO newDistro = new DistroDTO();

        String name = txtName.getText();
        String base = txtBase.getText();
        String pm = txtPM.getText();
        String environment = txtEnvironment.getText();

        newDistro.setName(name);
        newDistro.setBase(base);
        newDistro.setPackageManager(pm);
        newDistro.setEnvironment(environment);

        DistroDAO objDistroDAO = new DistroDAO();
        objDistroDAO.registerDistro(newDistro);
    }
}
