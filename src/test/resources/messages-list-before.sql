delete from message;

insert into message(id, tag, text, user_id) values
(1, 'tag1', 'text1', 1),
(2, 'tag2', 'text2', 1),
(3, 'tag1', 'text3', 1),
(4, 'tag4', 'text4', 1);

alter sequence hibernate_sequence restart with 10;