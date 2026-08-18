package com.template.validation;

import javafx.scene.control.TableView;

public class DistroValidator {
    public static boolean validateRequiredFields(TableView<?> tableView, String... values) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            return true;
        }

        for (String value : values) {
            if (value == null || value.trim().isEmpty()) {
                return true;
            }
        }

        return false;
    }
}