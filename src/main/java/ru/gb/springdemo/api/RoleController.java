package ru.gb.springdemo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.model.User;
import ru.gb.springdemo.service.RoleService;
import ru.gb.springdemo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Tag(name= "Roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // получить роль по id
    @Operation(summary = "get role by ID", description = "Поиск роли по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(roleService.getRoleById(id));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //получить список всех ролей
    @Operation(summary = "get all roles", description = "Поиск всех ролей в системе")
    @GetMapping
    public ResponseEntity<List<Role>> getAllUsers() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    // создание роли
    @Operation(summary = "create role", description = "Добавить роль в общий список системы")
    @PostMapping
    public ResponseEntity<Role> addUser(@RequestBody Role role){
        return new ResponseEntity<>(roleService.addRole(role), HttpStatus.CREATED);
    }
}
