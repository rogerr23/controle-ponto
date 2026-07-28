package rogerr.com.controleponto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rogerr.com.controleponto.entities.Historico;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, UUID> {

    @Query("select hi from Historico hi where hi.funcionario.id = :funcionario_id")
    List<Historico> findByHistoricoPorIdDeFuncionario(UUID funcionario_id);

    @Query("select hi from Historico hi where hi.funcionario.id = :funcionario_id and FUNCTION('DATE', hi.dataHoraOperacao) = CURRENT_DATE")
    List<Historico> findByHistoricoPorIdDeFuncionarioHoje(UUID funcionario_id);
}
