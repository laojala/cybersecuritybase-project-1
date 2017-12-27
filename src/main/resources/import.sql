INSERT INTO Signup (attendee, message) VALUES ('Susan Security', 'Looking forward to this!');
INSERT INTO Signup (attendee, message) VALUES ('Someone Secret', 'Glutein free food. Thanks!');


INSERT INTO users (username, email, password, enabled) VALUES ('sam','abc@abc.com','salasana', true);
INSERT INTO users (username, email, password, enabled) VALUES ('admin','def@def.com','password', true);

INSERT INTO user_roles (userid, role) VALUES (001, 'ROLE_USER');
INSERT INTO user_roles (userid, role) VALUES (002, 'ROLE_ADMIN'); 
--INSERT INTO user_roles (userid, role) VALUES (002, 'ROLE_USER');