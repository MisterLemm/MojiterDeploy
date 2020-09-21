delete from user_role;
delete from usr;

insert into usr(id, active, password, username) values
(1, true, '$2a$08$8bg3YKaTll3b61pMmnvRN.h4oDDZUCn1ngIol3lDG3N/GYbR4.8mW', 'admin' ),
(2, true, '$2a$08$8bg3YKaTll3b61pMmnvRN.h4oDDZUCn1ngIol3lDG3N/GYbR4.8mW', 'mike');

insert into user_role (user_id, roles)
values
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER');

