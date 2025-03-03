
// repository/AlunoRepository.java
package com.academia.matricula.repository;

import com.academia.matricula.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByAtivoTrue();
    List<Aluno> findByNomeContainingIgnoreCase(String nome);
}