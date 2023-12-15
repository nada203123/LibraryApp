package com.tekup.LibraryApp.repository.library;

import com.tekup.LibraryApp.model.library.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepo extends JpaRepository<Reservation,Long> {
}
