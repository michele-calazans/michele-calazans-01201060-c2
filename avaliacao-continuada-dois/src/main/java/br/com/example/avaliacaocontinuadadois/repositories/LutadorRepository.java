package br.com.example.avaliacaocontinuadadois.repositories;

import br.com.example.avaliacaocontinuadadois.dominio.Lutador;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LutadorRepository extends JpaRepository<Lutador, Integer> {

    List<Lutador> findByOrderByForcaGolpeDesc();
}
