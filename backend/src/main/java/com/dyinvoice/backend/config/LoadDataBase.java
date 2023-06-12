package com.dyinvoice.backend.config;


import com.dyinvoice.backend.model.entity.EntitiesRoleName;
import com.dyinvoice.backend.model.entity.Role;
import com.dyinvoice.backend.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@AllArgsConstructor
@Configuration
public class LoadDataBase {

    private RoleRepository roleRepository;

    private static final Logger log = LoggerFactory.getLogger(LoadDataBase.class);

    @Bean
    CommandLineRunner initDataBase(){
        return args -> {
            log.info("Init database check ");
            log.info("Create Roles ");

            Role admin = roleRepository.findByName(EntitiesRoleName.ROLE_ADMIN);
            if(admin == null){
                admin = new Role(0, EntitiesRoleName.ROLE_ADMIN, "admin");
                roleRepository.save(admin);
            }

            Role user = roleRepository.findByName(EntitiesRoleName.ROLE_USER);
            if(user == null){
                user = new Role(0, EntitiesRoleName.ROLE_USER, "user");
                roleRepository.save(user);
            }

            Role superAdmin = roleRepository.findByName(EntitiesRoleName.ROLE_SUPER_ADMIN);
            if(superAdmin == null) {
                superAdmin = new Role(0, EntitiesRoleName.ROLE_SUPER_ADMIN, "Super Admin");
                roleRepository.save(superAdmin);
            }

            log.info("End");


        };
    }
}
