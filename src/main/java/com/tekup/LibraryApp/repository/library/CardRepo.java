package com.tekup.LibraryApp.repository.library;

import com.tekup.LibraryApp.model.library.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepo extends JpaRepository<Card,Long> {


}
