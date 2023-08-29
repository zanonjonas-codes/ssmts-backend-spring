INSERT INTO public.users
(id, created_at, updated_at, "version", email, first_name, is_enabled, last_name, "password")
VALUES('2d4337e2-45cc-11ee-be56-0242ac120002', NULL, NULL, 0, 'admin@ssmts.com', 'Admin', true, 'Zao', '$2a$10$E3ospYmu/rVA4AUN4KwJmOF0uS0YmY.RZvYHTa7cXlw00g8ndMmni');

INSERT INTO public."role"
(id, created_at, updated_at, "version", "role")
VALUES('2f724b8c-45ce-11ee-be56-0242ac120002', NULL, NULL, 0, 'ADMIN');

INSERT INTO public.users_granted_roles
(granted_roles_id, granted_users_id)
VALUES('2f724b8c-45ce-11ee-be56-0242ac120002', '2d4337e2-45cc-11ee-be56-0242ac120002');
