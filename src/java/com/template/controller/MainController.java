package com.template.controller;

import com.template.model.dao.DistroDAO;
import com.template.model.dto.DistroDTO;
import com.template.util.FormUtil;
import com.template.validation.DistroValidator;
import com.template.validation.IDistroValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Optional;

import static com.template.util.DialogUtil.*;

public class MainController {
    private final IDistroValidator distroValidator;

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

    public MainController(IDistroValidator distroValidator) {
        this.distroValidator = distroValidator;
    }

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

        comboEnvironment.getEditor().textProperty().addListener((observable, oldValue, newValue) -> checkForm());

        comboEnvironment.valueProperty().addListener((observable, oldValue, newValue) -> checkForm());

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
        FormUtil.clearForm(tblDistro, comboEnvironment, txtName, txtBase, txtPkgMng);
    }

    @FXML
    private void checkForm() {
        if (FormUtil.checkFields(txtName.getText(), txtPkgMng.getText(), comboEnvironment.getEditor().getText())) {
            btnRegister.setDisable(false);
        } else {
            btnRegister.setDisable(true);
        }

        if (tblDistro.getSelectionModel().getSelectedItem() == null) {
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        } else {
            btnRegister.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
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
        checkForm();
    }

    @FXML
    private void btnRegisterAction(ActionEvent event) {
        DistroDTO objNewDistro = new DistroDTO();

        String name = txtName.getText();
        String base = txtBase.getText();
        String pkgMng = txtPkgMng.getText();
        String environment = comboEnvironment.getValue().toString();

        if (!distroValidator.validateDistro(name, base, pkgMng, environment)) {
            return;
        }

        if (base == null || base.trim().isEmpty()) {
            base = "independent";
        }

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
        String base = txtBase.getText();
        String pkgMng = txtPkgMng.getText();
        String environment = comboEnvironment.getValue().toString();

        if (!distroValidator.validateDistro(name, base, pkgMng, environment)) {
            return;
        }

        if (base == null || base.trim().isEmpty()) {
            base = "independent";
        }

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
        boolean dialogResult = showConfirmation("Deseja realmente deletar esta Distro?");

        if (!dialogResult) {
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
