package uz.pdp.task1.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.payload.AddressDTO;
import uz.pdp.task1.payload.Result;
import uz.pdp.task1.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    /**
     * this method gives all address
     *
     * @return Address
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Address> addressList = addressService.get();
        return ResponseEntity.ok(addressList);
    }


    /**
     * this method gives one address by id
     *
     * @param id Integer
     * @return Address
     */
    @GetMapping("/{id}")
    public HttpEntity<Address> getById(@PathVariable Integer id) {
        Address addressServiceById = addressService.getById(id);
        return new HttpEntity<>(addressServiceById);
    }


    /**
     * this method adds new address
     *
     * @param addressDTO
     * @return Result
     */
    @PostMapping
    public ResponseEntity<Result> add(@Valid @RequestBody AddressDTO addressDTO) {
        Result result = addressService.add(addressDTO);
        return ResponseEntity.ok(result);
    }


    /**
     * this method edits address by id
     *
     * @param id      Integer
     * @param addressDTO
     * @return Result
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> editById(@PathVariable Integer id, @Valid @RequestBody AddressDTO addressDTO) {
        Result result = addressService.edit(id, addressDTO);
        return ResponseEntity.status(result.getSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(result);
    }


    /**
     * this method delete address by id
     *
     * @param id Integer
     * @return Result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        Result result = addressService.delete(id);
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
