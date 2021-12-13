package com.lhind.application.controller.v1;

import com.lhind.application.security.logout.OnUserLogoutSuccessEvent;
import com.lhind.application.service.AuthenticatedUserService;
import com.lhind.application.swagger.SwaggerConstant;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lhind.application.controller.v1.LogoutController.BASE_URL;

@Slf4j
@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
@Api(tags = {SwaggerConstant.LOGOUT_API_TAG})
public class LogoutController {

    public static final String BASE_URL = "/api/v1/logout";

    private final AuthenticatedUserService authenticatedUserService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<String> logoutUser(@RequestHeader(name = "Authorization") String bearerToken) {
        log.info("Accessing endpoint {} to log out the user.", BASE_URL);

        String token = bearerToken.replace("Bearer ", "");
        String loggedUsername = authenticatedUserService.getLoggedUsername();

        OnUserLogoutSuccessEvent logoutSuccessEvent =
                new OnUserLogoutSuccessEvent(loggedUsername, token);

        applicationEventPublisher.publishEvent(logoutSuccessEvent);

        return new ResponseEntity<>("User logged out successfully.", HttpStatus.OK);
    }

}
