package rogerr.com.controleponto.dtos;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AutenticarFuncionarioResponse {

    private UUID id;
    private String nome;
    private String email;
}
