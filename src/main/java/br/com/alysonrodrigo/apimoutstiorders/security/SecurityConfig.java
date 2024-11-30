package br.com.alysonrodrigo.apimoutstiorders.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${user.client.username:client}")
    private String clientUsername;

    @Value("${user.client.password:client123}")
    private String clientPassword;

    @Value("${user.manager.username:manager}")
    private String managerUsername;

    @Value("${user.manager.password:manager123}")
    private String managerPassword;

    /**
     * Configura o filtro de segurança e habilita HTTP Basic.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF para simplificação em testes
                .authorizeHttpRequests(authorize ->
                        authorize
                                .anyRequest().authenticated() // Exige autenticação para todas as requisições
                )
                .httpBasic(httpBasic -> {}); // Habilita HTTP Basic Authentication

        return http.build();
    }

    /**
     * Configura um usuário em memória.
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails clientUser = User.builder()
                .username(clientUsername) // Nome de usuário do CLIENT
                .password(passwordEncoder.encode(clientPassword)) // Senha do CLIENT
                .roles("CLIENT") // Papel/role CLIENT
                .build();

        UserDetails managerUser = User.builder()
                .username(managerUsername) // Nome de usuário do MANAGER
                .password(passwordEncoder.encode(managerPassword)) // Senha do MANAGER
                .roles("MANAGER") // Papel/role MANAGER
                .build();

        return new InMemoryUserDetailsManager(clientUser, managerUser);
    }

    /**
     * Configura o encoder de senhas (BCrypt recomendado).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
