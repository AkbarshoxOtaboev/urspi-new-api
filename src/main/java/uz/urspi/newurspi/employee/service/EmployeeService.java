package uz.urspi.newurspi.employee.service;

import uz.urspi.newurspi.employee.dto.EmployeeDTO;
import uz.urspi.newurspi.employee.response.EmployeeLocalizedResponse;
import uz.urspi.newurspi.employee.response.EmployeeResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse create(EmployeeDTO dto);

    EmployeeResponse findById(Long id);

    List<EmployeeResponse> fetchAllEmployees();

    List<EmployeeLocalizedResponse> fetchAllEmployeesByLang(Language lang);

    List<EmployeeLocalizedResponse> fetchByCenterIdByLang(Long centerId, Language lang);

    EmployeeResponse update(Long id, EmployeeDTO dto);

    void delete(Long id);

    void activeOrDisabledEmployee(Long id);
}
