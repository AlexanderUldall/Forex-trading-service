package forex.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class Response {

    @NotNull
    @Schema(example = "23:59:59.999999999", description = "Format  HH:mm:ss.SSSSSSSSS", implementation = String.class)
    private LocalTime cutoffTime;
}
