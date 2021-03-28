package uz.pdp.task1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerDTO {

    @NotNull(message = "name must not be null")
    private String name;

    @NotNull(message = "phoneNumber must not be null")
    private String phoneNumber;

    @NotNull(message = "homeNumber must not be null")
    private String homeNumber;

    @NotNull(message = "street must not be null")
    private String street;

    @NotNull(message = "departmentId must not be null")
    private Integer departmentId;
}
