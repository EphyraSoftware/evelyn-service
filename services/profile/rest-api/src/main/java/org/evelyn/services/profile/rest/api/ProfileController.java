package org.evelyn.services.profile.rest.api;

import org.evelyn.library.authentication.token.TokenInfo;
import org.evelyn.library.authentication.token.TokenLookup;
import org.evelyn.services.profile.api.AuthenticationInfo;
import org.evelyn.services.profile.api.Profile;
import org.evelyn.services.profile.api.ProfileService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin(origins = "*")
@RestController
public class ProfileController {
    private final ProfileService profileService;
    private TokenLookup tokenLookup;

    public ProfileController(ProfileService profileService, TokenLookup tokenLookup) {
        this.profileService = profileService;
        this.tokenLookup = tokenLookup;
    }

    @GetMapping(value = "/profiles")
    @ResponseBody
    public Profile getProfile(final Principal principal) {
        AuthenticationInfo authInfo = toTokenInfo(tokenLookup.getTokenInfo());
        return profileService.getProfile(authInfo);
    }

    @PutMapping(value = "/profiles")
    @ResponseBody
    public Profile updateProfile(final Principal principal, @RequestBody Profile profile) {
        return profileService.updateProfile(principal.getName(), profile);
    }

    private AuthenticationInfo toTokenInfo(TokenInfo tokenInfo) {
        var authInfo = new AuthenticationInfo();
        authInfo.setSubject(tokenInfo.getSubject());
        authInfo.setEmail(tokenInfo.getEmail());
        return authInfo;
    }
}
