package de.hsrm.mi.web.bratenbank.bratboerse.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GuteAdresseValidator.class)
@NotNull
@Size(min=1, max=80)
public @interface GuteAdresse {
    String message() default "{guteadresse}";
    Class<? extends Payload>[] payload() default{};
    Class<?>[] groups() default{};
}