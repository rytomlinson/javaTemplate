package com.navis.insightserver.Utils;

import com.navis.insightserver.dto.UserProfileDTO;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ChrisDAgostino on 9/19/17.
 */
@Component
public class Security implements ISecurity {
    @Override
    public List<CommonProfile> GetProfiles(final WebContext context) {
        final ProfileManager manager = new ProfileManager(context);
        return manager.getAll(true);
    }

    @Override
    public UserProfileDTO GetUserProfile(final WebContext context)
    {
        CommonProfile profile = GetProfiles(context).get(0);

        String token = profile.getAttribute("id_token").toString();
        String state = profile.getAttribute("sid").toString();

        return new UserProfileDTO(profile.getId(), profile.getDisplayName(), token, state);
    }
}
