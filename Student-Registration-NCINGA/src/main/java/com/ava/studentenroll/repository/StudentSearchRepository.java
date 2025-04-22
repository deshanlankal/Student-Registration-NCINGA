package com.ava.studentenroll.repository;

import com.ava.studentenroll.model.Student;

import java.util.List;

public interface StudentSearchRepository {

    List<Student> findByText(String text,int page, int size);
}
