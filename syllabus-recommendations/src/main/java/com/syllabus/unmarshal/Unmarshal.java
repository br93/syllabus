package com.syllabus.unmarshal;

import java.util.List;

public interface Unmarshal<T> {

    T toResponse(Object object);
    T[] toArray(Object object);
    List<T> toList(Object object);
    
}
