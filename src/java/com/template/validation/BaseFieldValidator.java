package com.template.validation;

public class BaseFieldValidator implements Validator {
    private final String fieldName = "baseField";
    private final String value;

    public BaseFieldValidator(String value) {
        this.value = value;
    }

    @Override
    public boolean validate(Object value) {
        return !(this.value == null || this.value.trim().isEmpty());
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
