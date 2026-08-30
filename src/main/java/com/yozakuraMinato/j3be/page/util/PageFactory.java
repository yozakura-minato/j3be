package com.yozakuraMinato.j3be.page.util;

import com.yozakuraMinato.j3be.common.util.CommonFactory;
import com.yozakuraMinato.j3be.page.api.dto.CreatePageRequest;
import com.yozakuraMinato.j3be.page.api.dto.GetPageByPathRequest;
import com.yozakuraMinato.j3be.page.model.type.PageAccess;
import com.yozakuraMinato.j3be.page.repository.projection.PageProfile;

import java.util.UUID;

public class PageFactory {
    public static PageProfile pageProfile(PageAccess pageAccess) {
        return new PageProfile(
                UUID.randomUUID(),
                CommonFactory.string(),
                CommonFactory.string(),
                CommonFactory.string(),
                pageAccess
        );
    }

    public static CreatePageRequest createPageRequest(PageAccess pageAccess) {
        return new CreatePageRequest(
                CommonFactory.string(),
                CommonFactory.string(),
                CommonFactory.string(),
                pageAccess
        );
    }

    public static GetPageByPathRequest getPageByPathRequest() {
        return new GetPageByPathRequest(
                CommonFactory.string(),
                CommonFactory.string()
        );
    }
}