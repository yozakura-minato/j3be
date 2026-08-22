------------------------------------------------------------------------------------------------------------------------
create table if not exists users (
    id uuid primary key,

    email varchar(255) /*unidx_user_email*/ not null,
    hashed_password varchar(255) not null,
    google_id varchar(255) /*unidx_user_google_id*/,
    display_path varchar(255) /*unidx_user_display_path*/ not null,
    status varchar(255) not null,

    created_at timestamptz not null,
    created_by uuid not null,
    updated_at timestamptz,
    updated_by uuid,
    constraint chk_update_audit_fields
        check ((updated_at is null) = (updated_by is null)),
    is_deleted boolean not null,
    constraint chk_delete_audit_fields
        check (is_deleted = false or ((updated_at is not null) and (updated_by is not null)))
);

create unique index unidx_user_email on users(email)
    where is_deleted = false;
create unique index unidx_user_google_id on users(google_id)
    where is_deleted = false;
create unique index unidx_user_display_path on users(display_path)
    where is_deleted = false;

------------------------------------------------------------------------------------------------------------------------
create table if not exists notes (
    id uuid primary key,
    user_id uuid,
    constraint fk_notes_ref_users
        foreign key(user_id) references users(id),

    title varchar(255) not null,
    description text,
    text text not null,

    created_at timestamptz not null,
    created_by uuid not null,
    updated_at timestamptz,
    updated_by uuid,
    constraint chk_update_audit_fields
        check ((updated_at is null) = (updated_by is null)),
    is_deleted boolean not null,
    constraint chk_delete_audit_fields
        check (is_deleted = false or ((updated_at is not null) and (updated_by is not null)))
);

------------------------------------------------------------------------------------------------------------------------
create table if not exists links (
    id uuid primary key,
    user_id uuid,
    constraint fk_links_ref_users
        foreign key(user_id) references users(id),

    title varchar(255) not null,
    description text,
    url text /*unidx_link_url*/ not null,

    created_at timestamptz not null,
    created_by uuid not null,
    updated_at timestamptz,
    updated_by uuid,
    constraint chk_update_audit_fields
        check ((updated_at is null) = (updated_by is null)),
    is_deleted boolean not null,
    constraint chk_delete_audit_fields
        check (is_deleted = false or ((updated_at is not null) and (updated_by is not null)))
);

create unique index unidx_link_url on links(url, user_id)
    where is_deleted = false;

------------------------------------------------------------------------------------------------------------------------
create table if not exists pages (
    id uuid primary key,
    user_id uuid,
    constraint fk_pages_ref_users
        foreign key(user_id) references users(id),

    display_path varchar(255) /*unidx_page_display_path*/ not null,
    title varchar(255) not null,
    description text,
    access varchar(255) not null,

    created_at timestamptz not null,
    created_by uuid not null,
    updated_at timestamptz,
    updated_by uuid,
    constraint chk_update_audit_fields
        check ((updated_at is null) = (updated_by is null)),
    is_deleted boolean not null,
    constraint chk_delete_audit_fields
        check (is_deleted = false or ((updated_at is not null) and (updated_by is not null)))
);

create unique index unidx_page_display_path on pages(display_path, user_id)
    where is_deleted = false;

------------------------------------------------------------------------------------------------------------------------
create table if not exists page_contents (
    content_id uuid,
    page_id uuid,
    primary key(content_id, page_id),
    constraint fk_page_contents_ref_pages
        foreign key(page_id)references pages(id),
    user_id uuid,
    constraint fk_page_contents_ref_users
        foreign key(user_id) references users(id),

    index bigint /*unidx_page_content_index*/ not null,

    created_at timestamptz not null,
    created_by uuid not null,
    updated_at timestamptz,
    updated_by uuid,
    constraint chk_update_audit_fields
        check ((updated_at is null) = (updated_by is null)),
    is_deleted boolean not null,
    constraint chk_delete_audit_fields
        check (is_deleted = false or ((updated_at is not null) and (updated_by is not null)))
);

create unique index unidx_page_content_index on page_contents(index, content_id, page_id, user_id)
    where is_deleted = false;

------------------------------------------------------------------------------------------------------------------------
create or replace view contents as (
    select
        n.id as id,
        n.user_id as user_id,
        n.title as title,
        n.description as description,
        n.text as data,
        'NOTE' as type,
        'PUBLIC' as access,
        n.is_deleted as is_deleted
    from notes n
) union all (
    select
        l.id as id,
        l.user_id as user_id,
        l.title as title,
        l.description as description,
        l.url as data,
        'LINK' as type,
        'PUBLIC' as access,
        l.is_deleted as is_deleted
    from links l
) union all (
    select
        p.id as id,
        p.user_id as user_id,
        p.title as title,
        p.description as description,
        p.display_path as data,
        'PAGE' as type,
        p.access as access,
        p.is_deleted as is_deleted
    from pages p
    where p.is_deleted = false
);
