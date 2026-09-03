------------------------------------------------------------------------------------------------------------------------
create table if not exists users (
    id              uuid            primary key,

    email           varchar(255)    not null        /*unidx_user_email*/,
    hashed_password varchar(255)    not null,
    google_id       varchar(255)                    /*unidx_user_google_id*/,
    display_path    varchar(255)    not null        /*unidx_user_display_path*/,
    status          varchar(255)    not null,

    created_at      timestamptz     not null,
    created_by      uuid            not null,
    updated_at      timestamptz                     /*chk_update_audit_fields*/,
    updated_by      uuid,                           /*chk_update_audit_fields*/
    is_deleted      boolean         not null        /*chk_delete_audit_fields*/,

    constraint chk_update_audit_fields
        check ((updated_at is null) = (updated_by is null)),
    constraint chk_delete_audit_fields
        check (is_deleted = false or ((updated_at is not null) and (updated_by is not null)))
);

create unique index unidx_user_email on users (email)
    where is_deleted = false;
create unique index unidx_user_google_id on users (google_id)
    where is_deleted = false;
create unique index unidx_user_display_path on users (display_path)
    where is_deleted = false;

------------------------------------------------------------------------------------------------------------------------
create table if not exists notes (
    id              uuid            primary key,
    user_id         uuid                            /*fk_notes_ref_users*/,

    title           varchar(255)    not null,
    description     text,
    text            text            not null,

    created_at      timestamptz     not null,
    created_by      uuid            not null,
    updated_at      timestamptz                     /*chk_update_audit_fields*/,
    updated_by      uuid                            /*chk_update_audit_fields*/,
    is_deleted      boolean         not null        /*chk_delete_audit_fields*/,

    constraint fk_notes_ref_users
        foreign key (user_id) references users (id),

    constraint chk_update_audit_fields
        check ((updated_at is null) = (updated_by is null)),
    constraint chk_delete_audit_fields
        check (is_deleted = false or ((updated_at is not null) and (updated_by is not null)))
);

------------------------------------------------------------------------------------------------------------------------
create table if not exists links (
    id              uuid            primary key,
    user_id         uuid                            /*fk_links_ref_users*/,

    title           varchar(255)    not null,
    description     text,
    url             text            not null        /*unidx_link_url*/,

    created_at      timestamptz     not null,
    created_by      uuid            not null,
    updated_at      timestamptz                     /*chk_update_audit_fields*/,
    updated_by      uuid                            /*chk_update_audit_fields*/,
    is_deleted      boolean         not null,       /*chk_delete_audit_fields*/

    constraint fk_links_ref_users
        foreign key (user_id) references users (id),

    constraint chk_update_audit_fields
        check ((updated_at is null) = (updated_by is null)),
    constraint chk_delete_audit_fields
        check (is_deleted = false or ((updated_at is not null) and (updated_by is not null)))
);

create unique index unidx_link_url on links (url, user_id)
    where is_deleted = false;

------------------------------------------------------------------------------------------------------------------------
create table if not exists pages (
    id              uuid            primary key,
    user_id         uuid                            /*fk_pages_ref_users*/,


    display_path    varchar(255)    not null        /*unidx_page_display_path*/,
    title           varchar(255)    not null,
    description     text,
    access          varchar(255)    not null,

    created_at      timestamptz     not null,
    created_by      uuid            not null,
    updated_at      timestamptz                     /*chk_update_audit_fields*/,
    updated_by      uuid                            /*chk_update_audit_fields*/,
    is_deleted      boolean         not null        /*chk_delete_audit_fields*/,

    constraint fk_pages_ref_users
        foreign key (user_id) references users (id),

    constraint chk_update_audit_fields
        check ((updated_at is null) = (updated_by is null)),
    constraint chk_delete_audit_fields
        check (is_deleted = false or ((updated_at is not null) and (updated_by is not null)))
);

create unique index unidx_page_display_path on pages (display_path, user_id)
    where is_deleted = false;

------------------------------------------------------------------------------------------------------------------------
create table if not exists page_contents (
    content_id      uuid,           /*primary key*/
    page_id         uuid,           /*primary key*/ /*fk_page_contents_ref_pages*/
    user_id         uuid,                           /*fk_page_contents_ref_users*/

    index           bigint          not null        /*unidx_page_content_index*/,

    created_at      timestamptz     not null,
    created_by      uuid            not null,
    updated_at      timestamptz                     /*chk_update_audit_fields*/,
    updated_by      uuid                            /*chk_update_audit_fields*/,
    is_deleted      boolean         not null,

    primary key (content_id, page_id)               /*chk_delete_audit_fields*/,

    constraint fk_page_contents_ref_pages
        foreign key (page_id) references pages (id),
    constraint fk_page_contents_ref_users
        foreign key (user_id) references users (id),

    constraint chk_update_audit_fields
        check ((updated_at is null) = (updated_by is null)),
    constraint chk_delete_audit_fields
        check (is_deleted = false or ((updated_at is not null) and (updated_by is not null)))
);

create unique index unidx_page_content_index on page_contents (index, content_id, page_id, user_id)
    where is_deleted = false;
