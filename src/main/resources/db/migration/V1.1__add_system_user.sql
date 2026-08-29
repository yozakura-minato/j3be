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
    '00000000-0000-0000-0000-000000000000',
    'system@joho3.com',
    'hashedpassword4system',
    'system',
    'VALIDATED',
    now(),
    '00000000-0000-0000-0000-000000000000',
    false
);