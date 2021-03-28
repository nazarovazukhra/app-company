package uz.pdp.task1.service;

import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.entity.Department;
import uz.pdp.task1.entity.Worker;
import uz.pdp.task1.payload.Result;
import uz.pdp.task1.payload.WorkerDTO;
import uz.pdp.task1.repository.AddressRepository;
import uz.pdp.task1.repository.DepartmentRepository;
import uz.pdp.task1.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    final WorkerRepository workerRepository;
    final DepartmentRepository departmentRepository;
    final AddressRepository addressRepository;

    public WorkerService(WorkerRepository workerRepository, DepartmentRepository departmentRepository, AddressRepository addressRepository) {
        this.workerRepository = workerRepository;
        this.departmentRepository = departmentRepository;
        this.addressRepository = addressRepository;

    }


    /**
     * this method gives all workers
     *
     * @return Workers
     */
    public List<Worker> get() {
        return workerRepository.findAll();
    }


    /**
     * this method gives one worker by id
     *
     * @param id Integer
     * @return Worker
     */
    public Worker getById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }

    public Result add(WorkerDTO workerDTO) {

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDTO.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new Result("Such department not found", false);
        Department department = optionalDepartment.get();

        boolean existsByNameAndDepartment = workerRepository.existsByNameAndDepartment(workerDTO.getName(), department);
        if (existsByNameAndDepartment)
            return new Result("Such worker already exists in this department", false);

        boolean exists = workerRepository.existsByPhoneNumber(workerDTO.getPhoneNumber());
        if (exists)
            return new Result("Such phoneNumber already exists in another worker", false);


        Worker worker = new Worker();
        worker.setName(workerDTO.getName());
        worker.setPhoneNumber(workerDTO.getPhoneNumber());

        Address address = new Address();
        address.setHomeNumber(workerDTO.getHomeNumber());
        address.setStreet(workerDTO.getStreet());
        Address savedAddress = addressRepository.save(address);

        worker.setAddress(savedAddress);
        worker.setDepartment(department);
        workerRepository.save(worker);
        return new Result("Worker added", true);

    }


    /**
     * this method edits worker by given id
     *
     * @param id        Integer
     * @param workerDTO json object comes
     * @return Result
     */
    public Result edit(Integer id, WorkerDTO workerDTO) {

        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return new Result("Such worker not found", false);

        Worker worker = optionalWorker.get();

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDTO.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new Result("Such department not found", false);

        boolean exists = workerRepository.existsByPhoneNumberAndIdNot(workerDTO.getPhoneNumber(), worker.getId());
        if (exists)
            return new Result("Such phoneNumber already exists in another worker", false);

        worker.setName(workerDTO.getName());
        worker.setPhoneNumber(workerDTO.getPhoneNumber());
        worker.setDepartment(optionalDepartment.get());

        Address address = worker.getAddress();
        address.setHomeNumber(workerDTO.getHomeNumber());
        address.setStreet(workerDTO.getStreet());
        Address save = addressRepository.save(address);

        worker.setAddress(save);
        workerRepository.save(worker);
        return new Result("Worker edited", true);
    }


    /**
     * this method deletes one worker by id
     *
     * @param id Integer
     * @return Result
     */
    public Result delete(Integer id) {
        boolean exists = workerRepository.existsById(id);
        if (!exists)
            return new Result("Such worker not found", false);
        workerRepository.deleteById(id);
        return new Result("Worker deleted", true);
    }

}
