package com.syllabus.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.syllabus.client.account.AccountClient;
import com.syllabus.client.account.AccountResponse;
import com.syllabus.data.model.AlunoModel;
import com.syllabus.exception.StudentNotFoundException;
import com.syllabus.repository.AlunoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final AccountClient accountClient;

    public AlunoModel createAluno(AlunoModel aluno) {
        this.updateInstantAluno(aluno, Instant.now(), false);
        aluno.setEmail(this.getUser().getEmail());

        return alunoRepository.save(aluno);
    }

    public AlunoModel getAluno(String id) {
        return alunoRepository.findByAlunoIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new StudentNotFoundException("Aluno nao encontrado"));
    }

    public AlunoModel updateAluno(String id, AlunoModel novoAluno) {
        AlunoModel aluno = this.getAluno(id);
        novoAluno.setAlunoId(aluno.getAlunoId());
        this.updateInstantAluno(novoAluno, aluno.getCreatedAt(), false);

        return alunoRepository.save(novoAluno);
    }

    public void deleteAluno(String id) {
        AlunoModel aluno = this.getAluno(id);
        this.updateInstantAluno(aluno, Instant.now(), true);

        alunoRepository.save(aluno);
    }

    private AccountResponse getUser() {
        return this.accountClient.getAccount().getUser();
    }

    private void updateInstantAluno(AlunoModel aluno, Instant instant, boolean delete) {

        aluno.setUpdatedAt(Instant.now());
        
        if (!delete)
            aluno.setCreatedAt(instant);
        else
            aluno.setDeletedAt(instant);

    }

}
