package rogerr.com.controleponto.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoResponse {

    private UUID id;
    private UUID funcionario_id;
    private String operacao;
    private String latitude;
    private String longitude;
    private Date dataHoraOperacao;
}
