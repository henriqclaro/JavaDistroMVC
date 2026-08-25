package com.template.validation;

import static com.template.util.DialogUtil.showConfirmation;

public class BaseFieldValidator implements Validator {
    private final String fieldName = "Base";
    private final String value;

    public BaseFieldValidator(String value) {
        this.value = value;
    }

    @Override
    public boolean validate(Object value) {
        if (!(this.value == null || this.value.trim().isEmpty())) {
            return false; // arrumar confirmação
        }

        return true;
    }

    @Override
    public String getErrorMessage() {
        return "O campo " + fieldName + " deve ser preenchido corretamente.";
    }

    @Override
    public Object getValue() {
        return this.value;
    }
}
