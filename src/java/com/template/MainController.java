package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class MainController {
    @FXML
    private TableView<DistroDTO> tblDistro;

    @FXML
    private TableColumn<DistroDTO, Integer> colId;

    @FXML
    private TableColumn<DistroDTO, String> colName;

    @FXML
    private TableColumn<DistroDTO, String> colBase;

    @FXML
    private TableColumn<DistroDTO, String> colPackageManager;

    @FXML
    private TableColumn<DistroDTO, String> colEnvironment;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtBase;

    @FXML
    private TextField txtPkgMng;

    @FXML
    private TextField txtEnvironment;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBase.setCellValueFactory(new PropertyValueFactory<>("base"));
        colPackageManager.setCellValueFactory(new PropertyValueFactory<>("packageManager"));
        colEnvironment.setCellValueFactory(new PropertyValueFactory<>("environment"));
        System.out.println("FXML loaded successfully!");

        loadDistro();
    }

    @FXML
    private void loadDistro() {
        DistroDAO objDistroDAO = new DistroDAO();
        ArrayList<DistroDTO> distrosList = objDistroDAO.selectDistro();
        tblDistro.setItems(FXCollections.observableArrayList(distrosList));
    }

    @FXML
    private void loadField() {
        DistroDTO objDistroDTO = tblDistro.getSelectionModel().getSelectedItem();

        if (objDistroDTO != null) {
            txtName.setText(objDistroDTO.getName());
            txtBase.setText(objDistroDTO.getBase());
            txtPkgMng.setText(objDistroDTO.getPackageManager());
            txtEnvironment.setText(objDistroDTO.getEnvironment());
        }
    }

    @FXML
    private void btnClearAction(ActionEvent event) {
        txtName.clear();
        txtBase.clear();
        txtPkgMng.clear();
        txtEnvironment.clear();
    }

    @FXML
    private void btnRegisterAction(ActionEvent event) {
        DistroDTO objNewDistro = new DistroDTO();

        String name = txtName.getText();
        String base = txtBase.getText();
        String pkgMng = txtPkgMng.getText();
        String environment = txtEnvironment.getText();

        objNewDistro.setName(name);
        objNewDistro.setBase(base);
        objNewDistro.setPackageManager(pkgMng);
        objNewDistro.setEnvironment(environment);

        DistroDAO objDistroDAO = new DistroDAO();
        objDistroDAO.registerDistro(objNewDistro);

        loadDistro();
    }

    @FXML
    private void btnUpdateAction(ActionEvent event) {
        DistroDTO objUpdatedDistro = new DistroDTO();

        String name = txtName.getText();
        String base = txtBase.getText();
        String pkgMng = txtPkgMng.getText();
        String environment = txtEnvironment.getText();

        objUpdatedDistro.setId(tblDistro.getSelectionModel().getSelectedItem().getId());
        objUpdatedDistro.setName(name);
        objUpdatedDistro.setBase(base);
        objUpdatedDistro.setPackageManager(pkgMng);
        objUpdatedDistro.setEnvironment(environment);

        DistroDAO objDistroDAO = new DistroDAO();
        objDistroDAO.updateDistro(objUpdatedDistro);

        loadDistro();
    }

    @FXML
    private void btnDeleteAction(ActionEvent event) {
        DistroDTO objDeletedDistro = new DistroDTO();

        objDeletedDistro.setId(tblDistro.getSelectionModel().getSelectedItem().getId());

        DistroDAO objDistroDAO = new DistroDAO();
        objDistroDAO.deleteDistro(objDeletedDistro);

        loadDistro();
    }
}
