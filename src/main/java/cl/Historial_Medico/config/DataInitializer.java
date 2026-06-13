package cl.Historial_Medico.config;

import cl.Historial_Medico.model.HistorialMedico;
import cl.Historial_Medico.model.HistorialMedicoId;
import cl.Historial_Medico.model.Role;
import cl.Historial_Medico.model.User;
import cl.Historial_Medico.repository.HistorialRepository;
import cl.Historial_Medico.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final HistorialRepository historialRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void run(String... args) throws Exception {

        // Crear usuario si no existe
        if (userRepository.findByUsername("Maty").isEmpty()) {
            User user = new User();
            user.setUsername("Maty");
            user.setPassword(passwordEncoder.encode("1234"));
            user.setRole(Role.ADMIN);
            userRepository.save(user);
            log.info(">>> DataInitializer: Usuario Maty creado correctamente.");
        }

        if (historialRepository.count() > 0) {
            log.info(">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial.");
            return;
        }

        log.info(">>> DataInitializer: BD vacía detectada, insertando datos de prueba...");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        historialRepository.save(new HistorialMedico(new HistorialMedicoId(1, "22.359.190-6"), sdf.parse("2024-01-15"), "Hipertensión arterial leve"));
        historialRepository.save(new HistorialMedico(new HistorialMedicoId(2, "18.765.432-1"), sdf.parse("2024-02-20"), "Diabetes tipo 2"));
        historialRepository.save(new HistorialMedico(new HistorialMedicoId(3, "11.111.111-1"), sdf.parse("2024-03-10"), "Fractura de muñeca derecha"));

        log.info(">>> DataInitializer: {} historiales insertados correctamente.",
                historialRepository.count());
    }
}
