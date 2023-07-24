package forex.rest;

import forex.enums.CURRENCY_CODE;
import forex.exceptions.CurrencyNotFoundInDBException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api")
public class ForexController {

    @Autowired
    private ForexService forexService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The trading cutoff time for the requested date",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid currency given or requested date was in the past",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Currency not found in database",
                    content = @Content)})
    @Validated
    @Operation(summary = "Acquire the trading cutoff time for a given date for a pair of currencies. ")
    @RequestMapping(value = "/cutoffTime", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Response> getCutoffTime(@RequestParam CURRENCY_CODE toCurrency, @RequestParam CURRENCY_CODE fromCurrency, @RequestParam @Parameter(required = true,description = "Date of currency conversion. Required format yyyy-MM-DD") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfConversion) throws CurrencyNotFoundInDBException {

        LocalDate today = LocalDate.now();
        LocalTime cutoffTime;

        // Early termination check, can't trade in the past
        if (dateOfConversion.isBefore(today)) {
            return new ResponseEntity("Not possible to trade in the past", HttpStatus.BAD_REQUEST);
        }

        // Early termination check, can't trade same currency
        if (toCurrency.equals(fromCurrency)) {
            return new ResponseEntity("Not possible to trade same currency", HttpStatus.BAD_REQUEST);
        }

        cutoffTime = forexService.getCutoffTime(toCurrency, fromCurrency, dateOfConversion, today);

        return new ResponseEntity<>(new Response(cutoffTime), HttpStatus.OK);
    }
}
