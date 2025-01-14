package uao.edu.proyectoBS.citasMedicas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uao.edu.proyectoBS.citasMedicas.Entidades.Paciente;
import uao.edu.proyectoBS.citasMedicas.Repository.PacienteRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public Paciente createPaciente(@RequestBody Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @GetMapping
    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Paciente> getPacienteById(@PathVariable("id") String id) {
        return pacienteRepository.findById(id);
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updatePaciente(@PathVariable String id, @RequestBody Paciente paciente) {
        if (!pacienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        paciente.setId(id);
        Paciente updatedPaciente = pacienteRepository.save(paciente);
        return ResponseEntity.ok(updatedPaciente);
    }

    @DeleteMapping("/{id}")
    public void deletePaciente(@PathVariable("id") String id) {
        pacienteRepository.deleteById(id);
    }
}
