package com.tekup.LibraryApp.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowBookRequest {
    private Long bookId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
