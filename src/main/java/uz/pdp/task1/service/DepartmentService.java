package uz.pdp.task1.service;

import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Company;
import uz.pdp.task1.entity.Department;
import uz.pdp.task1.payload.DepartmentDTO;
import uz.pdp.task1.payload.Result;
import uz.pdp.task1.repository.CompanyRepository;
import uz.pdp.task1.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    final DepartmentRepository departmentRepository;
    final CompanyRepository companyRepository;

    public DepartmentService(DepartmentRepository departmentRepository, CompanyRepository companyRepository) {
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
    }


    /**
     * this method returns all departments
     *
     * @return Department
     */
    public List<Department> get() {
        return departmentRepository.findAll();
    }


    /**
     * this method returns one deparment by id
     *
     * @param id Integer
     * @return Department
     */
    public Department getById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }


    /**
     * this method adds new department
     *
     * @param departmentDTO json object comes
     * @return Result
     */
    public Result add(DepartmentDTO departmentDTO) {

        boolean existsDepartmentByCompany_id = departmentRepository.existsDepartmentByCompany_Id(departmentDTO.getCompanyId());
        if (existsDepartmentByCompany_id)
            return new Result("Such department already exists in this company", false);

        Optional<Company> optionalCompany = companyRepository.findById(departmentDTO.getCompanyId());
        if (!optionalCompany.isPresent())
            return new Result("Such company not found", false);

        Company company = optionalCompany.get();

        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setCompany(company);
        departmentRepository.save(department);
        return new Result("Department added", true);

    }


    public Result edit(Integer id, DepartmentDTO departmentDTO) {

        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return new Result("Such department not found", false);

        Optional<Company> optionalCompany = companyRepository.findById(departmentDTO.getCompanyId());
        if (!optionalCompany.isPresent())
            return new Result("Such company not found", false);

        Company company = optionalCompany.get();

        Department department = optionalDepartment.get();
        department.setName(departmentDTO.getName());
        department.setCompany(company);
        departmentRepository.save(department);
        return new Result("Department edited", true);
    }


    /**
     * this method deletes department by given id
     *
     * @param id Integer
     * @return Result
     */
    public Result delete(Integer id) {
        boolean exists = departmentRepository.existsById(id);
        if (!exists)
            return new Result("Such department not found", false);
        departmentRepository.deleteById(id);
        return new Result("Department deleted", true);
    }
}
