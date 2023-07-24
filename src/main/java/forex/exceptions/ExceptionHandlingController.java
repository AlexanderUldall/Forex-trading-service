package forex.exceptions;

import forex.enums.CURRENCY_CODE;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex) {
        return "Required parameter: '" + ex.getParameterName() + "' is missing";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        if (ex.getRequiredType().isAssignableFrom(LocalDate.class)) {
            return "Field '" + ex.getName() + "' has required format yyyy-MM-DD";
        } else if (CURRENCY_CODE.class.equals(ex.getRequiredType())) {
            return "Unsupported or invalid currency format: " + ex.getValue();
        }
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CurrencyNotFoundInDBException.class)
    public String handleCurrencyNotFoundExceptions(CurrencyNotFoundInDBException ex) {
        return ex.getMessage();
    }
}
