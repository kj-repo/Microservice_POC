package com.pk.assignment.constants;

public class PublisherConstant {
    public static final String NUMBER_MASK = ".(?=.{4,}$)";
    public static final String EMAIL_MASK = "(?<=.{3}).(?=.*@)";
    public static final String DATE_MASK = "(?!=^)[0-9](?=.{4,}$)";
    public static final String ERROR_STATUS = "ERROR"; 
    public static final String SUCCESS_STATUS = "SUCCESS"; 


    private PublisherConstant() {
        throw new IllegalStateException("Utility class");
      }
}
