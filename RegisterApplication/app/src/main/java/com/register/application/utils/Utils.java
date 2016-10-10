package com.register.application.utils;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ederson.js on 28/03/2016.
 */
public class Utils {

    public static final String END_POINT_CLIENTS = "clients";
    public static final String END_POINT_EMPLOYEES = "employees";
    public static final String END_POINT_PROVIDERS = "providers";
    public static final String END_POINT_PRODUCTS = "products";
    public static final String END_POINT_BILLSTOPAY = "billsToPay";
    public static final String END_POINT_BILLSTORECEIVE = "billsToReceive";

//    public static final String BASE_URL_BY_DEVICE = "http://192.168.1.101:8080/beautyfit/v1/";
    public static final String BASE_URL_BY_DEVICE = "http://105.103.43.55:8080/beautyfit/v1/";
    public static final String BASE_URL_BY_AVD = "http://10.0.2.2:8080/beautyfit/v1/";

    public static final String JSON = ".json";

    public static final String LIST_TAG = "list_tag";
    public static final String DETAIS_TAG = "details_tag";

    public static final String ID = "VALUE";

    public static final char MALE = 'M';
    public static final char FEMALE = 'F';

    public static final String CLIENTS = "CLIENTS";
    public static final String EMPLOYEES = "EMPLOYEES";
    public static final String PROVIDERS = "PROVIDERS";
    public static final String PRODUCTS = "PRODUCTS";
    public static final String BILLSTOPAY = "BILLSTOPAY";
    public static final String BILLSTORECEIVE = "BILLSTORECEIVE";

    public static final int UNEVEN_GRID_PENALTY_MULTIPLIER = 10;


    //Strings to bind with intent will be used to send data to other activity
    public static final String KEY_PROVIDERS_ID = "key_providers_id";
    public static final String KEY_PROVIDERS_NAME = "key_providers_name";
    public static final String KEY_PROVIDERS_CPF_CNPJ = "key_providers_cpf_cnpj";
    public static final String KEY_PROVIDERS_IE_RG = "key_providers_ie_rg";
    public static final String KEY_PROVIDERS_REGISTER_DATE = "key_providers_register_date";
    public static final String KEY_PROVIDERS_PHONE = "key_providers_phone";
    public static final String KEY_PROVIDERS_EMAIL = "key_providers_email";

    public static LocalDate converterDate(String dateField){

        LocalDate localDate;

        try {

            localDate = LocalDate.parse(dateField, DateTimeFormat.forPattern("YYYY-MM-dd HH:mm"));

        }

        catch (IllegalArgumentException e) {

            localDate = LocalDate.parse(dateField, DateTimeFormat.forPattern("YYYY-MM-dd"));
        }

        return localDate;

    }

    public static Date converterStringToDate(String dateField) throws ParseException {

        Date date = null;

        if ((!dateField.equals(""))){
//          DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            try {

                date = (Date)formatter.parse(dateField);


            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            date = new Date();
        }


        return date;
    }

}
