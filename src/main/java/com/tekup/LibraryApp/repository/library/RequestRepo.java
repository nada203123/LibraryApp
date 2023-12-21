package com.tekup.LibraryApp.repository.library;

import com.tekup.LibraryApp.model.library.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepo extends JpaRepository<Request,Long> {

    List<Request> findAllByUserId(Long userId);
}
