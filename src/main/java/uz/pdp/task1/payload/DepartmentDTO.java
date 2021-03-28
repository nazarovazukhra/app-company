package uz.pdp.task1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    @NotNull(message = "name must not be null")
    private String  name;

    @NotNull(message = "companyId must not be null")
    private Integer companyId;
}
