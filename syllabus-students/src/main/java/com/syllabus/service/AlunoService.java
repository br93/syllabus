package com.syllabus.service;

import org.springframework.stereotype.Service;

import com.syllabus.data.model.AlunoModel;
import com.syllabus.exception.StudentNotFoundException;
import com.syllabus.repository.AlunoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlunoService {
    
    private final AlunoRepository alunoRepository;

    public AlunoModel createAluno(AlunoModel aluno){
        return alunoRepository.save(aluno);
    }

    public AlunoModel getAluno(String id){
        return alunoRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Aluno nao encontrado"));
    }

    public AlunoModel updateAluno(String id, AlunoModel novoAluno){
        AlunoModel aluno = this.getAluno(id);
        novoAluno.setAlunoId(aluno.getAlunoId());

        return alunoRepository.save(aluno);
    }

    public void deleteAluno(String id){
        AlunoModel aluno = this.getAluno(id);
        alunoRepository.delete(aluno);
    }
}
