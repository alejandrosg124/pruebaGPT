package uao.edu.proyectoBS.citasMedicas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uao.edu.proyectoBS.citasMedicas.Entidades.Cita;
import uao.edu.proyectoBS.citasMedicas.Entidades.Medico;
import uao.edu.proyectoBS.citasMedicas.Entidades.Paciente;
import uao.edu.proyectoBS.citasMedicas.Repository.CitaRepository;
import uao.edu.proyectoBS.citasMedicas.Repository.MedicoRepository;
import uao.edu.proyectoBS.citasMedicas.Repository.PacienteRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Cita")
public class CitaController {

    private final CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public CitaController(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> getCitaById(@PathVariable("id") String id) {
        Optional<Cita> cita = citaRepository.findById(id);
        return cita.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }


    
    @PostMapping
    public ResponseEntity<Cita> createCita(@RequestBody Cita cita) {
        try {
            Paciente paciente = pacienteRepository.findById(cita.getPaciente().getId()).orElse(null);
            Medico medico = medicoRepository.findById(cita.getMedico().getId()).orElse(null);
            
            if (paciente == null || medico == null) {
                return ResponseEntity.badRequest().body(null); 
            }
    
            cita.setPaciente(paciente);
            cita.setMedico(medico);
    
            Cita nuevaCita = citaRepository.save(cita);
            return ResponseEntity.ok(nuevaCita);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable String id) {
        if (!citaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        citaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> updateCita(@PathVariable String id, @RequestBody Cita cita) {
        if (!citaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cita.setId(id);
        Cita updatedCita = citaRepository.save(cita);
        return ResponseEntity.ok(updatedCita);
    }
}
