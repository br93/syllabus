package com.syllabus.converter;

import java.io.InputStream;
import java.util.List;

public interface CsvConverter <T> {

    List<T> csvToModel(InputStream inputStream);
    
}
