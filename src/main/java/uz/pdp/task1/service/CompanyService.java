package uz.pdp.task1.service;

import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.entity.Company;
import uz.pdp.task1.payload.CompanyDTO;
import uz.pdp.task1.payload.Result;
import uz.pdp.task1.repository.AddressRepository;
import uz.pdp.task1.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    final CompanyRepository companyRepository;
    final AddressRepository addressRepository;

    public CompanyService(CompanyRepository companyRepository, AddressRepository addressRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
    }


    /**
     * this method return all companies
     *
     * @return Company
     */
    public List<Company> get() {
        return companyRepository.findAll();
    }


    /**
     * this method returns one company by id
     *
     * @param id Integer
     * @return Company
     */
    public Company getById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }


    /**
     * this method adds new company
     *
     * @param companyDTO json object comes
     * @return Result
     */
    public Result add(CompanyDTO companyDTO) {

        boolean exists = companyRepository.existsCompanyByCorpNameAndAddress_HomeNumberAndAddress_Street(companyDTO.getCorpName(),
                companyDTO.getHomeNumber(), companyDTO.getStreet());
        if (exists)
            return new Result("Such company with this address exists", false);

        Company company = new Company();
        company.setCorpName(companyDTO.getCorpName());
        company.setDirectorName(companyDTO.getDirectorName());

        Address address = new Address();
        address.setHomeNumber(companyDTO.getHomeNumber());
        address.setStreet(companyDTO.getStreet());
        Address savedAddress = addressRepository.save(address);

        company.setAddress(savedAddress);
        companyRepository.save(company);
        return new Result("Company added", true);
    }


    /**
     * this methods edits company by id
     *
     * @param id         Integer
     * @param companyDTO json object comes
     * @return Result
     */
    public Result edit(Integer id, CompanyDTO companyDTO) {

        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return new Result("Such company not found", false);
        Company company = optionalCompany.get();
        company.setCorpName(companyDTO.getCorpName());
        company.setDirectorName(companyDTO.getDirectorName());

        Address address = company.getAddress();
        address.setHomeNumber(companyDTO.getHomeNumber());
        address.setStreet(companyDTO.getStreet());
        Address save = addressRepository.save(address);

        company.setAddress(save);
        companyRepository.save(company);
        return new Result("Company edited", true);

    }


    /**
     * this method deletes company by id
     *
     * @param id Integer
     * @return Result
     */
    public Result delete(Integer id) {

        boolean exists = companyRepository.existsById(id);
        if (!exists)
            return new Result("Such company not found", false);

        companyRepository.deleteById(id);
        return new Result("Company deleted", true);
    }
}
