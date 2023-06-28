package com.syllabus.util;

import org.mapstruct.Mapper;

import com.syllabus.data.AlunoRequest;
import com.syllabus.data.AlunoResponse;
import com.syllabus.data.model.AlunoModel;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    public AlunoModel toAlunoModel(AlunoRequest dto);
    public AlunoResponse toAlunoResponse(AlunoModel model);
    
}
