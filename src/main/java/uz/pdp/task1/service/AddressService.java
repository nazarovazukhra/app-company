package uz.pdp.task1.service;

import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.payload.AddressDTO;
import uz.pdp.task1.payload.Result;
import uz.pdp.task1.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    /**
     * this method gives all address
     *
     * @return Address
     */
    public List<Address> get() {
        return addressRepository.findAll();
    }


    /**
     * this method gives one address by id
     *
     * @param id Integer
     * @return Address
     */
    public Address getById(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElse(null);
    }


    /**
     * this method adds new address
     *
     * @param addressDTO
     * @return Result
     */
    public Result add(AddressDTO addressDTO) {
        Address newAddress = new Address();
        newAddress.setHomeNumber(addressDTO.getHomeNumber());
        newAddress.setStreet(addressDTO.getStreet());

        addressRepository.save(newAddress);
        return new Result("Address added", true);
    }


    /**
     * this method edits address by id
     *
     * @param id         Integer
     * @param addressDTO
     * @return Result
     */
    public Result edit(Integer id, AddressDTO addressDTO) {

        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent())
            return new Result("Such address not found", false);

        Address editingAddress = optionalAddress.get();
        editingAddress.setHomeNumber(addressDTO.getHomeNumber());
        editingAddress.setStreet(addressDTO.getStreet());
        addressRepository.save(editingAddress);
        return new Result("Address edited", true);
    }


    /**
     * this method delete address by id
     *
     * @param id Integer
     * @return Result
     */
    public Result delete(Integer id) {
        boolean exists = addressRepository.existsById(id);
        if (!exists)
            return new Result("Such address not found", false);
        addressRepository.deleteById(id);
        return new Result("Address deleted", true);
    }
}
