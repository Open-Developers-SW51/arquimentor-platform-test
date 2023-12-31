package com.arquimentor.platform.arquimentor.interfaces.rest.transform;

import com.arquimentor.platform.arquimentor.domain.model.aggregates.MentorProfile;
import com.arquimentor.platform.arquimentor.domain.model.aggregates.StudentProfile;
import com.arquimentor.platform.arquimentor.interfaces.rest.resources.MentorProfileResource;
import com.arquimentor.platform.arquimentor.interfaces.rest.resources.StudentProfileResource;

public class MentorProfileEntityToResource {
    public static MentorProfileResource toResourceFromEntity(MentorProfile entity) {
        return new MentorProfileResource(
                entity.getId(),
                entity.getPhonenumber(),
                entity.getDescription(),
                entity.getUserprofilephoto(),
                entity.getCertificates(),
                entity.getMentor()
        );
    }
}
