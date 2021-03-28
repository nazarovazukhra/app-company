package uz.pdp.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.task1.entity.Department;
import uz.pdp.task1.entity.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByNameAndDepartment(String name, Department department);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
}
