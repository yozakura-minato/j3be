------------------------------------------------------------------------------------------------------------------------
create or replace view contents as (
    select  n.id            as id,
            n.user_id       as user_id,
            n.title         as title,
            n.description   as description,
            n.text          as data,
            'NOTE'          as type,
            'PUBLIC'        as access,
            n.is_deleted    as is_deleted
    from notes n
) union all (
    select  l.id            as id,
            l.user_id       as user_id,
            l.title         as title,
            l.description   as description,
            l.url           as data,
            'LINK'          as type,
            'PUBLIC'        as access,
            l.is_deleted    as is_deleted
    from links l
) union all (
    select  p.id            as id,
            p.user_id       as user_id,
            p.title         as title,
            p.description   as description,
            p.display_path  as data,
            'PAGE'          as type,
            p.access        as access,
            p.is_deleted    as is_deleted
    from pages p
    where p.is_deleted = false
);
