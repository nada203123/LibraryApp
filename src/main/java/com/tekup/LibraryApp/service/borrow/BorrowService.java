package com.tekup.LibraryApp.service.borrow;

import com.tekup.LibraryApp.DTO.request.BorrowBookRequest;
import com.tekup.LibraryApp.DTO.request.BorrowRenewalRequest;
import com.tekup.LibraryApp.model.library.Borrow;

import java.security.Principal;
import java.util.List;

public interface BorrowService {

    void borrowRequest(BorrowBookRequest borrowBookRequest, Principal principal);
    void borrowRenewal(BorrowRenewalRequest borrowRenewalRequest);

    List<Borrow> getBorrows(Principal user);
}
