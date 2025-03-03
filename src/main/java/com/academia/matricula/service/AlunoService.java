// service/AlunoService.java
package com.academia.matricula.service;

import com.academia.matricula.model.Aluno;
import com.academia.matricula.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {
    
    @Autowired
    private AlunoRepository alunoRepository;
    
    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }
    
    public List<Aluno> listarAtivos() {
        return alunoRepository.findByAtivoTrue();
    }
    
    public List<Aluno> buscarPorNome(String nome) {
        return alunoRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }
    
    public Aluno salvar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }
    
    public void excluir(Long id) {
        alunoRepository.deleteById(id);
    }
    
    public void desativar(Long id) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(id);
        if (alunoOpt.isPresent()) {
            Aluno aluno = alunoOpt.get();
            aluno.setAtivo(false);
            alunoRepository.save(aluno);
        }
    }
}