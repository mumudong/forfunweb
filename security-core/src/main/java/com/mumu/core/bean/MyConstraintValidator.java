package com.mumu.core.bean;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Administrator on 2018/7/25.
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object>{
    @Override
    public void initialize(MyConstraint myConstraint) {
        System.out.println("my validator init ------------------->");
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(o + " is valid? ------------------->");
        return true;
    }
}
