package de.hsrm.mi.web.bratenbank.validation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GuteAdresseValidator implements ConstraintValidator<GuteAdresse, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Pattern.matches("^[\\p{L} ]+ \\d+?\\w*, \\d{5} [\\p{L} ]+$", value);
    }
    
}
