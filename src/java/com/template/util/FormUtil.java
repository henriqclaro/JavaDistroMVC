package com.template.util;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FormUtil {
    public static void clearForm(TableView<?> tableView, ComboBox comboField, TextField... textFields) {
        for (TextField textField : textFields) {
            if (textField != null) {
                textField.clear();
            }
        }

        if (comboField != null) {
            comboField.setValue(null);
        }

        if (tableView != null) {
            tableView.getSelectionModel().clearSelection();
        }
    }
}
