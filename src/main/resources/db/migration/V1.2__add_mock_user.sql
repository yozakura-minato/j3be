insert into users (
    id,
    email,
    hashed_password,
    display_path,
    status,
    created_at,
    created_by,
    is_deleted
) values (
    '11111111-1111-1111-1111-111111111111',
    'user@joho3.com',
    'password4user',
    'user',
    'VALIDATED',
    now(),
    '00000000-0000-0000-0000-000000000000',
    false
);
