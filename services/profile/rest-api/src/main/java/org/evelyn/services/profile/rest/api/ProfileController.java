package org.evelyn.services.profile.rest.api;

import org.evelyn.library.authentication.token.TokenInfo;
import org.evelyn.library.authentication.token.TokenLookup;
import org.evelyn.services.profile.api.AuthenticationInfo;
import org.evelyn.services.profile.api.Profile;
import org.evelyn.services.profile.api.ProfileService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class ProfileController {
    private final ProfileService profileService;
    private final TokenLookup tokenLookup;

    public ProfileController(ProfileService profileService, TokenLookup tokenLookup) {
        this.profileService = profileService;
        this.tokenLookup = tokenLookup;
    }

    @GetMapping(value = "/profiles")
    @ResponseBody
    public Profile getProfile(@AuthenticationPrincipal Jwt principal) {
        TokenInfo tokenInfo = tokenLookup.getTokenInfo(principal);
        return profileService.getProfile(toAuthenticationInfo(tokenInfo));
    }

    @PutMapping(value = "/profiles")
    @ResponseBody
    public Profile updateProfile(@AuthenticationPrincipal Jwt principal, @RequestBody Profile profile) {
        TokenInfo tokenInfo = tokenLookup.getTokenInfo(principal);
        return profileService.updateProfile(tokenInfo.getSubject(), profile);
    }

    private AuthenticationInfo toAuthenticationInfo(TokenInfo tokenInfo) {
        var authInfo = new AuthenticationInfo();
        authInfo.setSubject(tokenInfo.getSubject());
        authInfo.setEmail(tokenInfo.getEmail());
        return authInfo;
    }
}
