package com.tekup.LibraryApp.payload.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationRequest {
    private long bookId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
