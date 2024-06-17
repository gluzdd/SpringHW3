package ru.gb.springdemo;

import org.hibernate.Hibernate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.gb.springdemo.model.User;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

	}
}
/*
1. Для ресурсов, возвращающих HTML-страницы, реализовать авторизацию через login-форму.
Остальные /api ресурсы, возвращающие JSON, закрывать не нужно!
2.1* Реализовать таблицы User(id, name, password) и Role(id, name), связанные многие ко многим
2.2* Реализовать UserDetailsService, который предоставляет данные из БД (таблицы User и Role)
3.3* Ресурсы выдачей (issue) доступны обладателям роли admin
3.4* Ресурсы читателей (reader) доступны всем обладателям роли reader
3.5* Ресурсы книг (books) доступны всем авторизованным пользователям
 */
