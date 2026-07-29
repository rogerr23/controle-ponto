package rogerr.com.controleponto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rogerr.com.controleponto.entities.Funcionario;

import java.util.UUID;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
    boolean existsByEmailIgnoreCase(String email);

    @Query("select fu from Funcionario fu where fu.email = :pEmail and fu.senha = :pSenha")
    Funcionario findByEmailAndSenha(@Param("pEmail") String email, @Param("pSenha") String senha);
}
