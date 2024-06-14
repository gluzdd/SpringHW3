package ru.gb.springdemo.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.model.User;
import ru.gb.springdemo.repository.RoleRepository;
import ru.gb.springdemo.repository.UserRepository;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // получить роль по id
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    //получить список всех пользователей
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // создание Роли
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    // удаление Роли
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
