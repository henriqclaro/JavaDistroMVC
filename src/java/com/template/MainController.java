package com.template;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Optional;

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
    private Button btnClear;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtBase;

    @FXML
    private TextField txtPkgMng;

    @FXML
    private ComboBox<String> comboEnvironment;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBase.setCellValueFactory(new PropertyValueFactory<>("base"));
        colPackageManager.setCellValueFactory(new PropertyValueFactory<>("packageManager"));
        colEnvironment.setCellValueFactory(new PropertyValueFactory<>("environment"));

        comboEnvironment.getEditor().setStyle("-fx-prompt-text-fill: #808080");

        ObservableList<String> options = FXCollections.observableArrayList(
                "GNOME", "KDE Plasma", "Xfce", "Cinnamon", "MATE", "LXQt", "LXDE", "Budgie", "Deepin", "Pantheon",
                "COSMIC", "Enlightenment", "UKUI", "Trinity", "Lumina", "Sugar", "PIXEL", "Cutefish"
        );

        comboEnvironment.setItems(options);

        checkForm();
        loadDistro();
        clearForm();
    }

    private void loadDistro() {
        DistroDAO objDistroDAO = new DistroDAO();
        ArrayList<DistroDTO> distroList = objDistroDAO.selectDistro();
        tblDistro.setItems(FXCollections.observableArrayList(distroList));
    }

    private void clearForm() {
        txtName.clear();
        txtBase.clear();
        txtPkgMng.clear();
        comboEnvironment.setValue(null);
        tblDistro.getSelectionModel().clearSelection();

        btnClear.setDisable(true);
    }

    private boolean isBaseOk() {
        if (txtBase.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confimação");
            alert.setHeaderText(null);
            alert.setContentText("O campo de Base está vazio. A Distro será cadastrada como 'independent'.");

            ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeOk) {
                txtBase.setText("independent");
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    @FXML
    private void checkForm() {
        btnClear.setDisable(false);

        if (tblDistro.getSelectionModel().getSelectedItem() == null) {
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        } else {
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }

        if (txtName.getText().isEmpty() || txtPkgMng.getText().isEmpty() || comboEnvironment.getValue() == null) {
            btnRegister.setDisable(true);
        } else {
            btnRegister.setDisable(false);
        }
    }

    @FXML
    private void loadField() {
        DistroDTO objDistroDTO = tblDistro.getSelectionModel().getSelectedItem();

        if (objDistroDTO != null) {
            txtName.setText(objDistroDTO.getName());
            txtBase.setText(objDistroDTO.getBase());
            txtPkgMng.setText(objDistroDTO.getPackageManager());
            comboEnvironment.setValue(objDistroDTO.getEnvironment());
        }

        checkForm();
    }

    @FXML
    private void btnClearAction(ActionEvent event) {
        clearForm();
    }

    @FXML
    private void btnRegisterAction(ActionEvent event) {
        DistroDTO objNewDistro = new DistroDTO();

        String name = txtName.getText();
        String pkgMng = txtPkgMng.getText();
        String environment = comboEnvironment.getValue().toString();

        if (!isBaseOk()) {
            return;
        }

        String base = txtBase.getText();

        objNewDistro.setName(name);
        objNewDistro.setBase(base);
        objNewDistro.setPackageManager(pkgMng);
        objNewDistro.setEnvironment(environment);

        DistroDAO objDistroDAO = new DistroDAO();
        objDistroDAO.registerDistro(objNewDistro);

        loadDistro();
        clearForm();
        checkForm();
    }

    @FXML
    private void btnUpdateAction(ActionEvent event) {
        DistroDTO objUpdatedDistro = new DistroDTO();

        String name = txtName.getText();
        String pkgMng = txtPkgMng.getText();
        String environment = comboEnvironment.getValue().toString();

        if (!isBaseOk()) {
            return;
        }

        String base = txtBase.getText();

        objUpdatedDistro.setId(tblDistro.getSelectionModel().getSelectedItem().getId());
        objUpdatedDistro.setName(name);
        objUpdatedDistro.setBase(base);
        objUpdatedDistro.setPackageManager(pkgMng);
        objUpdatedDistro.setEnvironment(environment);

        DistroDAO objDistroDAO = new DistroDAO();
        objDistroDAO.updateDistro(objUpdatedDistro);

        loadDistro();
        clearForm();
        checkForm();
    }

    @FXML
    private void btnDeleteAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText(null);
        alert.setContentText("Deseja realmente deletar esta Distro?");

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() != buttonTypeOk) {
            return;
        }

        DistroDTO objDeletedDistro = new DistroDTO();

        objDeletedDistro.setId(tblDistro.getSelectionModel().getSelectedItem().getId());

        DistroDAO objDistroDAO = new DistroDAO();
        objDistroDAO.deleteDistro(objDeletedDistro);

        loadDistro();
        clearForm();
        checkForm();
    }
}
