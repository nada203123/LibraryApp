package com.tekup.LibraryApp.service.request;


import com.tekup.LibraryApp.model.library.Request;

import java.security.Principal;
import java.util.List;

public interface RequestService {
    void request(Long bookId , Principal user);

    List<Request> getRequests(Principal user);

    void cancelRequest(Long RequestId);

}
