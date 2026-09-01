package com.template.validation;

public interface IDistroValidator {
    public boolean validateDistro(String name, String base, String pkgMng, String environment);
}
