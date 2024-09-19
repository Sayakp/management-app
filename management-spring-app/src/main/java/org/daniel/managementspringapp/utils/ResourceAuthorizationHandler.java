package org.daniel.managementspringapp.utils;

import org.daniel.managementspringapp.exception.UserNotAuthorizedException;
import org.daniel.managementspringapp.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ResourceAuthorizationHandler {

    private ResourceAuthorizationHandler() {
        throw new IllegalStateException("Utility class");
    }

    public static void verifyUserCanModifyResource(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        boolean isAuthor = userId.equals(userDetails.getId());
        // todo, will add admin role check here later

        if (!isAuthor) {
            throw new UserNotAuthorizedException("User is not authorized to modify this entry");
        }
    }
}
