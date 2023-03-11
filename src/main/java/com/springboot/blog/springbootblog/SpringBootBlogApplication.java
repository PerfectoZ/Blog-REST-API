package com.springboot.blog.springbootblog;

import com.springboot.blog.springbootblog.Entity.Role;
import com.springboot.blog.springbootblog.Entity.User;
import com.springboot.blog.springbootblog.Repository.RoleRepository;
import com.springboot.blog.springbootblog.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SpringBootBlogApplication implements CommandLineRunner {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringBootBlogApplication.class, args);
	}

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		try {
			Role adminRole = new Role();
			adminRole.setName("ROLE_ADMIN");
			roleRepository.save(adminRole);
		}
		catch(Exception e) {

		}
		try {
			Role userRole = new Role();
			userRole.setName("ROLE_USER");
			roleRepository.save(userRole);
		}
		catch(Exception e) {

		}
		try {
			User admin = new User();
			admin.setEmail("admin@gmail.com");
			admin.setName("admin");
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("admin"));
			Set<Role> roles = new HashSet<>();
			Role userRole = roleRepository.findRoleByName("ROLE_ADMIN").get();
			userRepository.save(admin);
		}
		catch(Exception e) {

		}
	}
}
