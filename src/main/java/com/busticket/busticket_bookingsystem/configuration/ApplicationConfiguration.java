package com.busticket.busticket_bookingsystem.configuration;

import com.busticket.busticket_bookingsystem.entity.identity.Role;
import com.busticket.busticket_bookingsystem.entity.identity.User;
import com.busticket.busticket_bookingsystem.enums.RoleEnum;
import com.busticket.busticket_bookingsystem.repository.RoleRepository;
import com.busticket.busticket_bookingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfiguration {

    private final PasswordEncoder passwordEncoder;  // Đã có bean PasswordEncoder

    private final RoleRepository roleRepository;

    @Bean
    ApplicationRunner runner(UserRepository userRepository) {
        return args -> {
            // Tạo role mặc định (chỉ tạo nếu chưa có)
            Role adminRole = roleRepository.findByName(RoleEnum.ADMIN.name())
                    .orElseGet(() -> roleRepository.save(
                            Role.builder()
                                    .name(RoleEnum.ADMIN.name())
                                    .description("admin Role")
                                    .build()
                    ));

            Role customerRole = roleRepository.findByName(RoleEnum.CUSTOMER.name())
                    .orElseGet(() -> roleRepository.save(
                            Role.builder()
                                    .name(RoleEnum.CUSTOMER.name())
                                    .description("customer Role")
                                    .build()
                    ));

            Role operatorRole = roleRepository.findByName(RoleEnum.OPERATOR.name())
                    .orElseGet(() -> roleRepository.save(
                            Role.builder()
                                    .name(RoleEnum.OPERATOR.name())
                                    .description("operator Role")
                                    .build()
                    ));

            // Tạo user admin mặc định
            if (userRepository.findByUserName("admin").isEmpty()) {
                User admin = User.builder()
                        .userName("admin")
                        .password(passwordEncoder.encode("admin"))
                        .firstName("admin")
                        .lastName("")
                        .role(adminRole)
                        .isActive(true)
                        .build();
                userRepository.save(admin);
                log.info("✅ Admin user created with default username/password: admin/admin");
            }

            // Tạo user customer mặc định
            if (userRepository.findByUserName("customer1").isEmpty()) {
                User customer = User.builder()
                        .userName("customer1")
                        .password(passwordEncoder.encode("customer1"))
                        .role(customerRole)
                        .firstName("customer")
                        .lastName("1")
                        .isActive(true)
                        .build();
                userRepository.save(customer);
                log.info("✅ Customer user created with default username/password: customer1/customer1");
            }

            // Tạo user operator mặc định
            if (userRepository.findByUserName("operator1").isEmpty()) {
                User operator = User.builder()
                        .userName("operator1")
                        .password(passwordEncoder.encode("operator1"))
                        .role(operatorRole)
                        .firstName("operator")
                        .lastName("1")
                        .isActive(true)
                        .build();
                userRepository.save(operator);
                log.info("✅ Operator user created with default username/password: operator1/operator1");
            }
        };
    }
}
