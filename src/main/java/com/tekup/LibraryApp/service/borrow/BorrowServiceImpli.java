package com.tekup.LibraryApp.service.borrow;

import com.tekup.LibraryApp.DTO.request.BorrowBookRequest;
import com.tekup.LibraryApp.DTO.request.BorrowRenewalRequest;
import com.tekup.LibraryApp.exception.ResourceNotFoundException;
import com.tekup.LibraryApp.model.library.Borrow;
import com.tekup.LibraryApp.model.library.StatusCopy;
import com.tekup.LibraryApp.model.user.User;
import com.tekup.LibraryApp.repository.library.BookCopyRepo;
import com.tekup.LibraryApp.repository.library.BorrowRepo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpli implements BorrowService{
    private final BorrowRepo borrowRepo;
    private final BookCopyRepo bookCopyRepo;
    private final EntityManager entityManager;


    //you should schedule a notification at the end date of the borrow request.(waiting for notification process to be implemented)
    @Override
    public void borrowRequest(BorrowBookRequest borrowBookRequest, Principal principal) {
        User member = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        entityManager.detach(member);

        //find available copy to borrow
        var bookCopy=bookCopyRepo.findFirstByBook_IdAndStatusCopy(
                borrowBookRequest.getBookId(),
                StatusCopy.AVAILABLE
        ).orElseThrow(()->new ResourceNotFoundException("No book copy is available"));
        bookCopy.setStatusCopy(StatusCopy.UNAVAILABLE);

        var borrowRequest = Borrow.builder()
                .user(member)
                .startDate(borrowBookRequest.getStartDate())
                .endDate(borrowBookRequest.getEndDate())
                .bookCopy(bookCopy)
                .build();

        borrowRepo.save(borrowRequest);
    }

    @Override
    public void borrowRenewal(BorrowRenewalRequest borrowRenewalRequest) {

    }

    @Override
    public List<Borrow> getBorrows(Principal user) {
        User member = (User) ((UsernamePasswordAuthenticationToken) user).getPrincipal();
        entityManager.detach(member);
        return borrowRepo.findAllByUserId(member.getId());
    }
}
