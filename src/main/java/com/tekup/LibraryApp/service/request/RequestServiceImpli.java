package com.tekup.LibraryApp.service.request;

import com.tekup.LibraryApp.exception.ResourceNotFoundException;
import com.tekup.LibraryApp.model.library.Request;
import com.tekup.LibraryApp.model.library.RequestStatus;
import com.tekup.LibraryApp.model.user.User;
import com.tekup.LibraryApp.repository.library.BookRepo;
import com.tekup.LibraryApp.repository.library.RequestRepo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpli implements RequestService {
    private final BookRepo bookRepo;
    private final RequestRepo requestRepo;
    private final EntityManager entityManager;


    //wait for notification  Service to be implemented .
    //after saving the request you should schedule a notification when one of the copies is available.
    @Override
    public void request(Long bookId, Principal user) {
        User member = (User) ((UsernamePasswordAuthenticationToken) user).getPrincipal();
        entityManager.detach(member);
        var requestedBook=bookRepo.findById(bookId).orElseThrow(()->new ResourceNotFoundException("book not found"));

        var request = Request.builder()
                .book(requestedBook)
                .user(member)
                .requestStatus(RequestStatus.PENDING)
                .requestDate(LocalDateTime.now())
                .build();
        requestRepo.save(request);
    }

    @Override
    public List<Request> getRequests(Principal user) {
        User member = (User) ((UsernamePasswordAuthenticationToken) user).getPrincipal();
        entityManager.detach(member);
        return requestRepo.findAllByUserId(member.getId());
    }

    @Override
    public void cancelRequest(Long requestId) {
        var request=requestRepo.findById(requestId).orElseThrow(()->new ResourceNotFoundException("request not found"));
        request.setRequestStatus(RequestStatus.CANCELED);
        requestRepo.save(request);
    }

}
    //"The code below is not applicable to our case"
    // if the there is book copy available you add in emprunt table
    // else you add in reservation table .
//    @Override
//    public String reserve(ReservationRequest reservationRequest, Principal user) {
//
//        var bookOpt = bookRepo.findById(reservationRequest.getBookId()).orElseThrow();
//        var bookCopies = bookOpt.getBookCopies();
//        User member = (User) ((UsernamePasswordAuthenticationToken) user).getPrincipal();
//        entityManager.detach(member);
//
//        // check if this book copy id exist in the range of start date and end date in reservation
//        var available = bookCopies.stream()
//                .filter(bookCopy -> isReservationPeriodAvailable(bookCopy,
//                        reservationRequest.getStartDate(),
//                        reservationRequest.getEndDate()))
//                .findFirst()
//                .orElseThrow();
//
//        var reservation=Reservation.builder()
//                .startDate(reservationRequest.getStartDate())
//                .endDate(reservationRequest.getEndDate())
//                .bookCopy(available)
//                .user(member)
//                .build();
//        available.getReservations().add(reservation);
//
//        reservationRepo.save(reservation);
//        return "redirect:/reserve";
//    }
//
//    private boolean isReservationPeriodAvailable(BookCopy bookCopy, LocalDateTime requestedStartDate, LocalDateTime requestedEndDate) {
//        return bookCopy.getReservations().stream()
//                .noneMatch(reservation ->
//                        requestedStartDate.isBefore(reservation.getEndDate()) &&
//                                reservation.getStartDate().isBefore(requestedEndDate)
//                );
//    }
