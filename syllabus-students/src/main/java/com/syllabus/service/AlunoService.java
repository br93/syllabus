package com.syllabus.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.syllabus.data.model.AlunoModel;
import com.syllabus.exception.StudentNotFoundException;
import com.syllabus.repository.AlunoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoModel createAluno(AlunoModel aluno) {
        aluno.setCreatedAt(Instant.now());
        aluno.setUpdatedAt(Instant.now());
        return alunoRepository.save(aluno);
    }

    public AlunoModel getAluno(String id) {
        return alunoRepository.findByAlunoIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new StudentNotFoundException("Aluno nao encontrado"));
    }

    public AlunoModel updateAluno(String id, AlunoModel novoAluno) {
        AlunoModel aluno = this.getAluno(id);
        novoAluno.setAlunoId(aluno.getAlunoId());

        novoAluno.setCreatedAt(aluno.getCreatedAt());
        novoAluno.setUpdatedAt(Instant.now());

        return alunoRepository.save(novoAluno);
    }

    public void deleteAluno(String id) {
        AlunoModel aluno = this.getAluno(id);

        aluno.setUpdatedAt(Instant.now());
        aluno.setDeletedAt(Instant.now());
        alunoRepository.save(aluno);
    }
}
