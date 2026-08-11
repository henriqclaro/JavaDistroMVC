package com.template.validation;

public class DistroValidator {
    public static boolean validateRequiredFields(String name, String pkgMng, String environment) {
        return (name.isEmpty() || pkgMng.isEmpty() || environment == null);
    }
}
