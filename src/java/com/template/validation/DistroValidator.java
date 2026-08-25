package com.template.validation;

import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

import static com.template.util.DialogUtil.showError;

public class DistroValidator {
    public static boolean validateDistro(String name, String base, String pkgMng, String environment) {
        List<Validator<String>> validators = new ArrayList<>();

        validators.add(new RequiredFieldValidator("Name", name));
        validators.add(new RequiredFieldValidator("PackageManager", pkgMng));
        validators.add(new RequiredFieldValidator("Environment", environment));

        validators.add(new BaseFieldValidator(base));

        for (Validator<String> validator : validators) {
            if (!validator.validate(validator.getValue())) {
                showError(validator.getErrorMessage());
                return false;
            }
        }

        return true;
    }


//    public static boolean validateRequiredFields(TableView<?> tableView, String... values) {
//        if (tableView.getSelectionModel().getSelectedItem() != null) {
//            return true;
//        }
//
//        for (String value : values) {
//            if (value == null || value.trim().isEmpty()) {
//                return true;
//            }
//        }
//
//        return false;
//    }
}