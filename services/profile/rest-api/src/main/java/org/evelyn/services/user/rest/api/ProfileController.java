package org.evelyn.services.user.rest.api;

import org.evelyn.services.user.api.Profile;
import org.evelyn.services.user.api.ProfileService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(value = "/profiles")
    @ResponseBody
    public Profile getProfile(final Principal principal) {
        return profileService.getProfile(principal.getName());
    }
}
