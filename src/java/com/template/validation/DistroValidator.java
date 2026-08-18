package com.template.validation;

public class DistroValidator {
    public static boolean validateRequiredFields(String name, String pkgMng, String environment) {
        return (name == null || name.trim().isEmpty() ||
                pkgMng == null || pkgMng.trim().isEmpty() ||
                environment == null || environment.trim().isEmpty());
    }
}