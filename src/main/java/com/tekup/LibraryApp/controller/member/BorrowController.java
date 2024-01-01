package com.tekup.LibraryApp.controller.member;

import com.tekup.LibraryApp.DTO.request.BorrowBookRequest;
import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.model.notification.Notification;
import com.tekup.LibraryApp.model.user.User;
import com.tekup.LibraryApp.service.book.BookService;
import com.tekup.LibraryApp.service.borrow.BorrowService;
import com.tekup.LibraryApp.service.notification.NotificationService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/borrow")
public class BorrowController {
    private final BorrowService borrowService;
    private final BookService bookService;
    private final NotificationService notificationService;
    private final EntityManager entityManager;

    @GetMapping
    String showBorrowForm(
            Model model,
            @RequestParam(name = "bookId") Long bookId,
            @ModelAttribute("borrow") BorrowBookRequest borrowBookRequest,
            Principal principal
    ) {
        Book book = bookService.getBookById(bookId); // retrieve book details
        model.addAttribute("book", book);


        User member = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        entityManager.detach(member);
        Long userId = member.getId();

        List<Notification> notifications = this.notificationService.getLatestNotifications(userId);
        long unreadNotificationCount = this.notificationService.countUnreadNotifications(userId);

        for (Notification notification : notifications) {
            LocalDateTime createdAt = notification.getCreatedAt();
            String formattedTime = calculateFormattedTime(createdAt);
            notification.setFormattedTime(formattedTime);
        }

        model.addAttribute("notifications", notifications);
        model.addAttribute("unreadNotificationCount", unreadNotificationCount);

        return "/member/borrow";
    }

    private String calculateFormattedTime(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);

        if (duration.toMinutes() < 1) {
            return "just now";
        } else if (duration.toHours() < 1) {
            long minutes = duration.toMinutes();
            return minutes + (minutes > 1 ? " minutes ago" : " minute ago");
        } else if (duration.toDays() < 1) {
            long hours = duration.toHours();
            return hours + (hours > 1 ? " hours ago" : " hour ago");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm", Locale.ENGLISH);
            return createdAt.format(formatter);
        }
    }

    @PostMapping
    String borrowBook(@ModelAttribute("borrow") BorrowBookRequest borrowBookRequest, Principal user) {
        System.out.println(borrowBookRequest);
        borrowService.borrowRequest(borrowBookRequest, user);
        return "redirect:/member/books";
        //return redirect:/member/myborrows
    }

}
