// controller/AlunoController.java
package com.academia.matricula.controller;

import com.academia.matricula.model.Aluno;
import com.academia.matricula.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/alunos")
public class AlunoController {
    
    @Autowired
    private AlunoService alunoService;
    
    @GetMapping
    public String listar(Model model, @RequestParam(required = false) String nome) {
        List<Aluno> alunos;
        
        if (nome != null && !nome.isEmpty()) {
            alunos = alunoService.buscarPorNome(nome);
            model.addAttribute("nomeBusca", nome);
        } else {
            alunos = alunoService.listarTodos();
        }
        
        model.addAttribute("alunos", alunos);
        return "aluno/list";
    }
    
    @GetMapping("/novo")
    public String novo(Model model) {
        Aluno aluno = new Aluno();
        aluno.setDataMatricula(LocalDate.now());
        aluno.setAtivo(true);
        model.addAttribute("aluno", aluno);
        return "aluno/form";
    }
    
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("aluno") Aluno aluno, 
                          BindingResult result, 
                          RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "aluno/form";
        }
    
        alunoService.salvar(aluno);
        attr.addFlashAttribute("mensagem", "Aluno salvo com sucesso!");
        return "redirect:/aluno/list";
    }
    
    
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Aluno aluno = alunoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("aluno", aluno);
        return "aluno/form";
    }
    
    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        try {
            alunoService.excluir(id);
            attr.addFlashAttribute("mensagem", "Aluno excluído com sucesso!");
        } catch (Exception e) {
            attr.addFlashAttribute("mensagemErro", "Erro ao excluir o aluno!");
        }
        return "redirect:/alunos";
    }
    
    @GetMapping("/{id}/desativar")
    public String desativar(@PathVariable Long id, RedirectAttributes attr) {
        try {
            alunoService.desativar(id);
            attr.addFlashAttribute("mensagem", "Matrícula desativada com sucesso!");
        } catch (Exception e) {
            attr.addFlashAttribute("mensagemErro", "Erro ao desativar matrícula!");
        }
        return "redirect:/alunos";
    }
}