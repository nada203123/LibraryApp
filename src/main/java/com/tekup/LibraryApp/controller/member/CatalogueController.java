package com.tekup.LibraryApp.controller.member;

import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.model.notification.Notification;
import com.tekup.LibraryApp.model.user.User;
import com.tekup.LibraryApp.payload.request.BookCatalogueFilter;
import com.tekup.LibraryApp.payload.request.ReservationRequest;
import com.tekup.LibraryApp.service.catalogue.CatalogueService;
import com.tekup.LibraryApp.service.category.CategoryService;
import com.tekup.LibraryApp.service.notification.NotificationService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class CatalogueController {
    private final CatalogueService catalogueService;
    private final CategoryService categoryService;
    private final NotificationService notificationService;
    private final EntityManager entityManager;

    @GetMapping("/books")
    public String showCatalogue(Model model, Principal principal, @RequestParam(defaultValue = "1", name = "page") int pageNo
    ) {
        final int PAGE_SIZE = 5;
        Page<Book> page = catalogueService.findPaginated(pageNo - 1, PAGE_SIZE);
        var books = page.getContent();
        model.addAttribute("filter", new BookCatalogueFilter());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("books", books);
        model.addAttribute("reservationRequest", new ReservationRequest());

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

        return "/member/books";
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

    @PostMapping("/books")
    public String showCatalogueFiltred(
            Model model,
            @RequestParam(defaultValue = "1", name = "page") int pageNo,
            BookCatalogueFilter bookCatalogueFilter
    ) {
        final int PAGE_SIZE = 5;
        Page<Book> page = catalogueService.findBooksByFilters(bookCatalogueFilter, pageNo - 1, PAGE_SIZE);
        model.addAttribute("filter", bookCatalogueFilter);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("books", page.getContent());
        model.addAttribute("reservationRequest", new ReservationRequest());
        return "/member/books";
    }

}
