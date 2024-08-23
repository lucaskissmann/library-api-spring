package com.library.api.helpers.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.repositories.AuthorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class CPFValidator
    implements
        ConstraintValidator<CPF, String> {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context )
    {
        if( !StringUtils.hasLength( value ))
            return true;

        return validateCpf(value, context);
    }

    public boolean validateCpf( String value, ConstraintValidatorContext context )
    {
        org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator cpfValidator = new org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator();
        cpfValidator.initialize( null );

        return cpfValidator.isValid( value, context );
    }
}
