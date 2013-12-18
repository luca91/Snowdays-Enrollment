DELETE FROM snowdays_enrollment.users;

INSERT INTO snowdays_enrollment.users 
VALUES (1, 'Luca', 'Bellettati', 'bellettati91', '1991-06-20', 'lucabelles@gmail.com', 'admin', 'lbellettati');

INSERT INTO snowdays_enrollment.users 
VALUES (2, 'Stefano', 'Mich', 'stefano93', '1993-08-22', 'Stefano.Mich@stud-inf.unibz.it', 'admin', 'smich');

INSERT INTO snowdays_enrollment.users 
VALUES (3, 'Ettore', 'Cip', 'ettore93', '1993-04-01', 'cip.ett@facebook.com', 'admin', 'epic');

INSERT INTO snowdays_enrollment.roles
VALUES ('Admin', 'lucabelles@gmail.com');

INSERT INTO snowdays_enrollment.roles
VALUES ('Admin', 'Stefano.Mich@stud-inf.unibz.it');

INSERT INTO snowdays_enrollment.roles
VALUES ('Admin', 'cip.ett@facebook.com');