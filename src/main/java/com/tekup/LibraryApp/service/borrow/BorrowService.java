package com.tekup.LibraryApp.service.borrow;

import com.tekup.LibraryApp.DTO.request.BorrowBookRequest;
import com.tekup.LibraryApp.DTO.request.BorrowRenewalRequest;

import java.security.Principal;

public interface BorrowService {

    void borrowRequest(BorrowBookRequest borrowBookRequest, Principal principal);
    void borrowRenewal(BorrowRenewalRequest borrowRenewalRequest);

}
