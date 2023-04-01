INSERT INTO authorities (username, authority) VALUES ('bob','ROLE_USER');
INSERT INTO users (username, password, enabled) VALUES ('bob', '$2a$10$qW7beUQsqn48JYvLXEALKe/svi6MQfml.GJ8hMrMTVvXFXIjLa9ey', '1');
INSERT INTO authorities (username, authority) VALUES ('joe@king.com','ROLE_ADMIN');
INSERT INTO users (username, password, enabled) VALUES ('joe@king.com', '$2a$10$iU5zveDE1BR6JsgeozC6/enuAIRCPaq80x9qF14EE/.7SvRQ2bA3S', '1');


INSERT INTO details (username, first_name, last_name, email, photo) values 
	('bob','Bob','Smith','bob.smith@email.com', FILE_READ('classpath:/static/images/bob.jpg'));
INSERT INTO details (username, first_name, last_name, email, photo) values 
	('joe@king.com','Joe','King','joe@king.com', FILE_READ('classpath:/static/images/joe.jpg'));
