package uz.pdp.task1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Company;
import uz.pdp.task1.payload.CompanyDTO;
import uz.pdp.task1.payload.Result;
import uz.pdp.task1.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    /**
     * this method return all companies
     *
     * @return Company
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Company> companyList = companyService.get();
        return ResponseEntity.ok(companyList);
    }


    /**
     * this method returns one company by id
     *
     * @param id Integer
     * @return Company
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Company company = companyService.getById(id);
        return ResponseEntity.ok(company);

    }


    /**
     * this method adds new company
     *
     * @param companyDTO json object comes
     * @return Result
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody CompanyDTO companyDTO) {
        Result result = companyService.add(companyDTO);
        return ResponseEntity.status(result.getSuccess() ? 201 : 409).body(result);
    }


    /**
     * this methods edits company by id
     *
     * @param id         Integer
     * @param companyDTO json object comes
     * @return Result
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody CompanyDTO companyDTO) {
        Result result = companyService.edit(id, companyDTO);
        return ResponseEntity.status(result.getSuccess() ? 202 : 409).body(result);
    }


    /**
     * this method deletes company by id
     *
     * @param id Integer
     * @return Result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Result result = companyService.delete(id);
        return ResponseEntity.status(result.getSuccess() ? 200 : 409).body(result);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
