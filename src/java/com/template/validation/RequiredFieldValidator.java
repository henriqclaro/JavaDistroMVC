package com.template.validation;

public class RequiredFieldValidator implements Validator {
    private final String fieldName;
    private final String value;

    public RequiredFieldValidator(String fieldName, String value) {
        this.fieldName = fieldName;
        this.value = value;
    }

    @Override
    public boolean validate(Object value) {
        return this.value != null && !this.value.trim().isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return "O campo " + fieldName + " deve ser preenchido.";
    }

    @Override
    public Object getValue() {
        return value;
    }
}
