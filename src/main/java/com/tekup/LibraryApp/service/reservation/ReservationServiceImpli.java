package com.tekup.LibraryApp.service.reservation;

import com.tekup.LibraryApp.model.library.BookCopy;
import com.tekup.LibraryApp.model.library.Reservation;
import com.tekup.LibraryApp.model.user.User;
import com.tekup.LibraryApp.payload.request.ReservationRequest;
import com.tekup.LibraryApp.repository.library.BookRepo;
import com.tekup.LibraryApp.repository.library.ReservationRepo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpli implements ReservationService {
    private final BookRepo bookRepo;
    private final ReservationRepo reservationRepo;
    private final EntityManager entityManager;







    @Override
    public String reserve(ReservationRequest reservationRequest, Principal user) {

        var bookOpt = bookRepo.findById(reservationRequest.getBookId()).orElseThrow();
        var bookCopies = bookOpt.getBookCopies();
        User reader = (User) ((UsernamePasswordAuthenticationToken) user).getPrincipal();
        entityManager.detach(reader);

        // check if this book copy id exist in the range of start date and end date in reservation
        var available = bookCopies.stream()
                .filter(bookCopy -> isReservationPeriodAvailable(bookCopy,
                        reservationRequest.getStartDate(),
                        reservationRequest.getEndDate()))
                .findFirst()
                .orElseThrow();

        var reservation=Reservation.builder()
                .startDate(reservationRequest.getStartDate())
                .endDate(reservationRequest.getEndDate())
                .bookCopy(available)
                .user(reader)
                .build();
        available.getReservations().add(reservation);

        reservationRepo.save(reservation);
        return "";
    }

    private boolean isReservationPeriodAvailable(BookCopy bookCopy, LocalDateTime requestedStartDate, LocalDateTime requestedEndDate) {
        return bookCopy.getReservations().stream()
                .noneMatch(reservation ->
                        requestedStartDate.isBefore(reservation.getEndDate()) &&
                                reservation.getStartDate().isBefore(requestedEndDate)
                );
    }
}
