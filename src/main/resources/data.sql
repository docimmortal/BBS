INSERT INTO Authorities (username, authority) VALUES ('bob','ROLE_USER');
INSERT INTO Users (username, password, enabled) VALUES ('bob', '$2a$10$MDR4W.xVzDaD//8C2tx4mO25XrJSysQGComXcUsOp/jOC2a3Rhao2', '1');
INSERT INTO Authorities (username, authority) VALUES ('joe@king.com','ROLE_ADMIN');
INSERT INTO Users (username, password, enabled) VALUES ('joe@king.com', '$2a$10$6a5NW/vV4M83iMMyw5wVAeG6WIDsmDTA.uOm3TQW7mEIqP771O5oO', '1');


INSERT INTO User_Details (username, first_name, last_name, email, photo, last_login) values 
	('bob','Bob','Smith','bob.smith@email.com', FILE_READ('classpath:/static/images/bob.jpg'), current_timestamp());
INSERT INTO User_Details (username, first_name, last_name, email, photo, last_login) values 
	('joe@king.com','Joe','King','joe@king.com', FILE_READ('classpath:/static/images/joe.jpg'), current_timestamp());
	
INSERT INTO Message_Forums(name, description) values ('General','Catch-all everyday chat');
INSERT INTO Message_Forums(name, description) values ('Humor','Funny stuff');

INSERT INTO Messages(title, message, timestamp, user_details_id, message_forum_id) values 
	('Hello!',CAST('This is a test message.' AS CLOB),'2025-01-01 09:01:22',1,1);
INSERT INTO Messages(title, message, timestamp, user_details_id, message_forum_id) values 
	('Test 2',CAST('This is another test message.' AS CLOB),'2025-01-03 13:05:12',2,1);
INSERT INTO Messages(title, message, timestamp, user_details_id, message_forum_id) values 
	('Ha ha!',CAST('This is another new message.' AS CLOB),'2025-01-01 09:20:34',1,2);
