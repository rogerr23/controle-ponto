package rogerr.com.controleponto.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "historico")
public class Historico {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Column(name = "operacao", length = 100, nullable = false)
    private Operacao operacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_hora_operacao", nullable = false)
    private Date dataHoraOperacao;

    @Column(name = "latitude", length = 100, nullable = false)
    private String latitude;

    @Column(name = "longitude", length = 100, nullable = false)
    private String longitude;

}
