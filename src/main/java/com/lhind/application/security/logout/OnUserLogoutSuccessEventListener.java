package com.lhind.application.security.logout;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnUserLogoutSuccessEventListener implements ApplicationListener<OnUserLogoutSuccessEvent> {

    private final LoggedOutJwtTokenCache tokenCache;

    public void onApplicationEvent(OnUserLogoutSuccessEvent event) {
        tokenCache.markLogoutEventForToken(event);
    }

}
