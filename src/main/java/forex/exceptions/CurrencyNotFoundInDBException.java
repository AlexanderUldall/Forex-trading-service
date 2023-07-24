package forex.exceptions;

import forex.enums.CURRENCY_CODE;

public class CurrencyNotFoundInDBException extends Exception {
    public CurrencyNotFoundInDBException(CURRENCY_CODE CURRENCYCOODE) {
        super("Currency " + CURRENCYCOODE + " was not found");
    }
}