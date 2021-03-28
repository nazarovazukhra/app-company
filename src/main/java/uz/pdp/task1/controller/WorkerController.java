package uz.pdp.task1.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Worker;
import uz.pdp.task1.payload.Result;
import uz.pdp.task1.payload.WorkerDTO;
import uz.pdp.task1.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {

    final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }


    /**
     * this method gives all workers
     *
     * @return Workers
     */
    @GetMapping
    public HttpEntity<List<Worker>> getAll() {
        List<Worker> workerList = workerService.get();
        return new HttpEntity<>(workerList);
    }


    /**
     * this method gives one worker by id
     *
     * @param id Integer
     * @return Worker
     */
    @GetMapping("/{id}")
    public ResponseEntity<Worker> getById(@PathVariable Integer id) {
        Worker worker = workerService.getById(id);
        return ResponseEntity.ok(worker);
    }


    /**
     * this method adds new worker
     *
     * @param workerDTO json object comes
     * @return Result
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody WorkerDTO workerDTO) {
        Result result = workerService.add(workerDTO);
        return ResponseEntity.status(result.getSuccess() ? 201 : 409).body(result);
    }


    /**
     * this method edits worker by given id
     *
     * @param id        Integer
     * @param workerDTO json object comes
     * @return Result
     */
    @PutMapping("/{id}")
    public ResponseEntity<Result> edit(@PathVariable Integer id, @Valid @RequestBody WorkerDTO workerDTO) {
        Result result = workerService.edit(id, workerDTO);
        return ResponseEntity.status(result.getSuccess() ? 201 : 409).body(result);
    }


    /**
     * this method deletes one worker by id
     *
     * @param id Integer
     * @return Result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> delete(@PathVariable Integer id) {
        Result result = workerService.delete(id);
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
