package br.com.fkbinho.agenda.repository;

import br.com.fkbinho.agenda.models.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContatoRepository  extends JpaRepository<Contato, Long> {

    List<Contato> findByNomeContainingIgnoreCase(String nome);

    Optional<Contato> findByEmail(String email);
}
