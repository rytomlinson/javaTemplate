package com.navis.insightserver.Utils;

import com.navis.insightserver.dto.UserProfileDTO;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;

import java.util.List;

/**
 * Created by ChrisDAgostino on 9/19/17.
 */
public interface ISecurity {
    List<CommonProfile> GetProfiles(WebContext context);

    UserProfileDTO GetUserProfile(WebContext context);
}
