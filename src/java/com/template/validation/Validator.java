package com.template.validation;

public interface Validator<T> {
    boolean validate(T value);

    String getErrorMessage();

    T getValue();
}
