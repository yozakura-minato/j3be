package com.yozakuraMinato.j3be.page.util;

import com.yozakuraMinato.j3be.common.annotation.IgnoreIdAndAuditFields;
import com.yozakuraMinato.j3be.page.api.request.CreatePageRequest;
import com.yozakuraMinato.j3be.page.api.request.UpdatePageRequest;
import com.yozakuraMinato.j3be.page.model.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PageMapper {
    @IgnoreIdAndAuditFields
    @Mapping(target = "displayPath", source = "displayPath")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "access", source = "access")
    Page createPageRequestDtoToEntity(CreatePageRequest request);

    @IgnoreIdAndAuditFields
    @Mapping(target = "displayPath", source = "displayPath")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "access", source = "access")
    void updatePageFromUpdatePageRequest(UpdatePageRequest updatePageRequest, @MappingTarget Page page);
}
