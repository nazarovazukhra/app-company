package uz.pdp.task1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Department;
import uz.pdp.task1.payload.DepartmentDTO;
import uz.pdp.task1.payload.Result;
import uz.pdp.task1.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    /**
     * this method returns all departments
     *
     * @return Department
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Department> departmentList = departmentService.get();
        return ResponseEntity.ok(departmentList);
    }


    /**
     * this method returns one deparment by id
     *
     * @param id Integer
     * @return Department
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Department department = departmentService.getById(id);
        return ResponseEntity.ok(department);
    }


    /**
     * this method adds new department
     *
     * @param departmentDTO json object comes
     * @return Result
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody DepartmentDTO departmentDTO) {
        Result result = departmentService.add(departmentDTO);
        return ResponseEntity.status(result.getSuccess() ? 201 : 409).body(result);
    }


    /**
     * this method edits department by id
     *
     * @param id            Integer
     * @param departmentDTO json object comes
     * @return Result
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody DepartmentDTO departmentDTO) {
        Result result = departmentService.edit(id, departmentDTO);
        return ResponseEntity.status(result.getSuccess() ? 202 : 409).body(result);
    }


    /**
     * this method deletes department by given id
     *
     * @param id Integer
     * @return Result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Result result = departmentService.delete(id);
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
