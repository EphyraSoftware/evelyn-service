package org.evelyn.services.profile.rest.api;

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

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(value = "/profiles")
    @ResponseBody
    public Profile getProfile(final Principal principal) {
        return profileService.getProfile(principal.getName());
    }

    @PutMapping(value = "/profiles")
    @ResponseBody
    public Profile updateProfile(final Principal principal, @RequestBody Profile profile) {
        return profileService.updateProfile(principal.getName(), profile);
    }
}
