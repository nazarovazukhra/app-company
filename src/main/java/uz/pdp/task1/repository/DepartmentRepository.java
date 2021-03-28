package uz.pdp.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.task1.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer> {

    boolean existsDepartmentByCompany_Id(Integer company_id);
}
