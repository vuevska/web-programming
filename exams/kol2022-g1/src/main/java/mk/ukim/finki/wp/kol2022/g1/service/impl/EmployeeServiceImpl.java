package mk.ukim.finki.wp.kol2022.g1.service.impl;

import mk.ukim.finki.wp.kol2022.g1.model.Employee;
import mk.ukim.finki.wp.kol2022.g1.model.EmployeeType;
import mk.ukim.finki.wp.kol2022.g1.model.Skill;
import mk.ukim.finki.wp.kol2022.g1.model.exceptions.InvalidEmployeeIdException;
import mk.ukim.finki.wp.kol2022.g1.model.exceptions.InvalidSkillIdException;
import mk.ukim.finki.wp.kol2022.g1.repository.EmployeeRepository;
import mk.ukim.finki.wp.kol2022.g1.repository.SkillRepository;
import mk.ukim.finki.wp.kol2022.g1.service.EmployeeService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final SkillRepository skillRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, SkillRepository skillRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.skillRepository = skillRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Employee> listAll() {
        return this.employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return this.employeeRepository.findById(id).orElseThrow(InvalidEmployeeIdException::new);
    }

    @Override
    public Employee create(String name, String email, String password, EmployeeType type, List<Long> skillId, LocalDate employmentDate) {
        List<Skill> skills = this.skillRepository.findAllById(skillId);
        String encryptedPassword = this.passwordEncoder.encode(password);
        Employee employee = new Employee(name, email, encryptedPassword, type, skills, employmentDate);
        this.employeeRepository.save(employee);
        return employee;
    }

    @Override
    public Employee update(Long id, String name, String email, String password, EmployeeType type, List<Long> skillId, LocalDate employmentDate) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(InvalidEmployeeIdException::new);
        employee.setName(name);
        employee.setEmail(email);
        employee.setPassword(password);
        employee.setType(type);
        List<Skill> skills = this.skillRepository.findAllById(skillId);
        employee.setSkills(skills);
        employee.setEmploymentDate(employmentDate);
        this.employeeRepository.save(employee);
        return employee;
    }

    @Override
    public Employee delete(Long id) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(InvalidEmployeeIdException::new);
        this.employeeRepository.delete(employee);
        return employee;
    }

    @Override
    public List<Employee> filter(Long skillId, Integer yearsOfService) {
        List<Employee> employees;
        if(skillId != null && yearsOfService != null) {
            employees = this.employeeRepository.findBySkillsContainingAndEmploymentDateBefore(
                    this.skillRepository.findById(skillId).orElseThrow(InvalidSkillIdException::new),
                    LocalDate.now().minusYears(yearsOfService)
            );
        } else if(skillId != null) {
            employees = this.employeeRepository
                    .findBySkillsContaining(
                            this.skillRepository
                            .findById(skillId)
                            .orElseThrow(InvalidSkillIdException::new)
                    );
        } else if(yearsOfService != null) {
            employees = this.employeeRepository
                    .findByEmploymentDateBefore(LocalDate.now().minusYears(yearsOfService));
        } else {
            employees = this.employeeRepository.findAll();
        }
        return employees;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = this.employeeRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(
                employee.getEmail(),
                employee.getPassword(),
                Stream.of(new SimpleGrantedAuthority("ROLE_" + employee.getType().toString())).collect(Collectors.toList())
        );
    }
}
