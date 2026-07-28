package rogerr.com.controleponto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rogerr.com.controleponto.entities.Gestor;

import java.util.UUID;

@Repository
public interface GestorRepository extends JpaRepository<Gestor, UUID> {
    @Query("select ge from Gestor ge where ge.email = :pEmail and ge.senha = :pSenha")
    Gestor findByEmailAndSenha(@Param("pEmail") String email, @Param("pSenha") String senha);
}
