package uao.edu.proyectoBS.citasMedicas.Entidades;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "cita")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Cita {
    @Id
    private String id;
    private String fecha;
    private Paciente paciente;
    private Medico medico;
}
