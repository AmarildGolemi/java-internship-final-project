package com.lhind.application.utility.model.flightdto;

import com.lhind.application.utility.validation.datevalidation.FutureDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@Data
@ApiModel(value = "Flight Filter")
public class FlightFilterDto {

    @NotBlank(message = "Departing city is mandatory")
    @Size(min = 3, max = 20, message = "Departing city should be between 3 and 20 characters")
    @ApiModelProperty(notes = "Departing city of the flight", example = "rome", required = true)
    private String from;

    @NotBlank(message = "Arrival city is mandatory")
    @Size(min = 3, max = 20, message = "Arrival city should be between 3 and 20 characters")
    @ApiModelProperty(notes = "Arrival city of the flight", example = "berlin", required = true)
    private String to;

    @FutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @ApiModelProperty(notes = "Departing date of the flight", example = "2021-12-25", required = true)
    private Date departureDate;

}
