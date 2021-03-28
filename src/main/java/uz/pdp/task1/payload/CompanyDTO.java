package uz.pdp.task1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {

    @NotNull(message = "corpName must not be null")
    private String corpName;

    @NotNull(message = "directorName must not be null")
    private String directorName;

    @NotNull(message = "homeNumber must not be null")
    private String  homeNumber;

    @NotNull(message = "street must not be null")
    private String street;
}
