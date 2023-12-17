package com.tekup.LibraryApp.service.reservation;


import com.tekup.LibraryApp.payload.request.ReservationRequest;

import java.security.Principal;

public interface ReservationService {
    String reserve(ReservationRequest reservationRequest, Principal user);

}
