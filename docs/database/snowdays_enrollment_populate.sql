DELETE FROM snowdays_enrollment.users;
DELETE FROM snowdays_enrollment.roles;
DELETE FROM snowdays_enrollment.programs;
DELETE FROM snowdays_enrollment.rental_options;
DELETE FROM snowdays_enrollment.faculties;
DELETE FROM snowdays_enrollment.countries;

INSERT INTO `users` (`user_id`, `user_name`, `user_surname`, `user_password`, `user_birthday`, `user_email`, `user_role`, `user_username`, `user_group`) VALUES
(1, 'Luca', 'Bellettati', 'bellettati91', '1991-06-20', 'lucabelles@gmail.com', 'admin', 'lbellettati', NULL),
(2, 'Stefano', 'Mich', 'stefano93', '1993-08-22', 'Stefano.Mich@stud-inf.unibz.it', 'admin', 'smich', NULL),
(3, 'Ettore', 'Cip', 'ettore93', '1993-04-01', 'cip.ett@facebook.com', 'admin', 'epic', NULL),
(4, 'Michael', 'Urbani', 'urbani93', '1993-04-02', 'm.d.urbani1@gmail.com', 'admin', 'murbani', NULL),
(5, 'Anna', 'Tagliabue', 'tagliabue93', '1994-11-13', 'anna.tagliabue.754@facebook.com', 'admin', 'atagliabue', NULL),
(6, 'Verena', 'Bonetti', 'bonetti90', '0199-05-09', 'verena.bonetti@economics.unibz.it', 'admin', 'vbonetti', NULL),
(7, 'Daniel', 'Kaneider', 'kaneider89', '1989-02-15', 'daniel.kaneider@economics.unibz.it', 'admin', 'dkaneider', NULL),
(9, 'Alessandro', 'Di Mattia', 'anconadimattia', '2014-01-01', 'alessandro.dimattia@studio.unibo.it', 'group_manager', 'adimattia', NULL),
(10, 'Alex', 'Fiore', 'barifiore', '2014-01-01', 'alex.fiore94@gmail.com', 'group_manager', 'afiore', NULL),
(11, 'Gianvito', 'De Palma', 'bolognadepalma', '2014-01-01', 'whitelord_@hotmail.com', 'group_manager', 'gdepalma', NULL),
(12, 'Lorenzo', 'Misconel', 'ferraramisconel', '2014-01-01', 'lorenzo.misconel@student.unife.it', 'group_manager', 'lmisconel', NULL),
(13, 'Federica', 'Mazzer', 'padovamazzer', '2014-01-01', 'fede_mazzer@hotmail.it', 'group_manager', 'fmazzer', NULL),
(14, 'Andrea', 'Petruzzelli', 'pisapetruzzelli', '2014-01-01', 'andrea.petruzzelli@ymail.com', 'group_manager', 'apetruzzelli', NULL),
(15, 'Ilaria', 'Menigatti', 'perugiamenigatti', '2014-01-01', 'ilariamenigatti@gmail.com', 'group_manager', 'amenigatti', NULL),
(16, 'Alessandro', 'Stagni', 'polmilanostagni', '2014-01-01', 'alessandro.stagni88@gmail.com', 'group_manager', 'astagni', NULL),
(17, 'Alessandra', 'Scaravilli', 'federico2scaravilli', '2014-01-01', 'brusyale@hotmail.com', 'group_manager', 'ascaravilli', NULL),
(18, 'Eros', 'Zaccagnino', 'trentozaccagnino', '2014-01-01', 'eroszaccagnino@msn.com', 'group_manager', 'ezaccagnino', NULL),
(19, 'Oscar', 'Braschi', 'carlobobraschi', '2014-01-01', 'oscar9191@hotmail.it', 'group_manager', 'obraschi', NULL),
(20, 'Daniel', 'Di Cristo', 'poltorinodicristo', '2014-01-01', 'danieldicristo@yahoo.de', 'group_manager', 'ddicristo', NULL),
(21, 'Jan', 'Ripken', 'bertelsmannripken', '2014-01-01', 'jan.ripken@hotmail.de', 'group_manager', 'jripken', NULL),
(22, 'Niclas', 'Gast', 'dortmundgast', '2014-01-01', 'niclas.gast@web.de', 'group_manager', 'ngast', NULL),
(23, 'Christoph', 'Creutzburg', 'essencreutzburg', '2014-01-01', 'cc16687@t-online.de', 'group_manager', 'ccreutzburg', NULL),
(24, 'Jonas', 'Ohnenachname', 'esbohnenachname', '2014-01-01', 'sport@stubue.de', 'group_manager', 'johnenachname', NULL),
(25, 'Stephan', 'Sollwedel', 'hamburgsollwedel', '2014-01-01', 'stephan.sollwedel@hsu-hh.de', 'group_manager', 'ssollwedel', NULL),
(26, 'Marco', 'Weitzel', 'koblenzweitzel', '2014-01-01', 'marcoweitzel@gmx.de', 'group_manager', 'mweitzel', NULL),
(27, 'Anna', 'Dallago', 'innsbruckdallago', '2014-01-01', 'a.dallago@hotmail.com', 'group_manager', 'adallago', NULL),
(28, 'Roman', 'Auernheimer', 'ingolstadtauernheimer', '2014-01-01', 'Roman.Auernheimer@me.com', 'group_manager', 'rauernheimer', NULL),
(29, 'Elke', 'Maier', 'mannheimmaier', '2014-01-01', 'elkemaier@gmx.de', 'group_manager', 'emaier', NULL),
(30, 'Markus', 'Kerschbamer', 'salzburgkerschbamer', '2014-01-01', 'markuskerschi', 'group_manager', 'mkerschbamer', NULL),
(31, 'Fabian', 'Dostal', 'heidelbergdostal', '2014-01-01', 'fado.2000@web.de', 'group_manager', 'fdostal', NULL),
(32, 'Ruediger', 'Sauer', 'bayreuthsauer', '2014-01-01', 'ruefi.sauer@gmail.com', 'group_manager', 'rsauer', NULL),
(33, 'Peter', 'Rier', 'zurichrier', '2014-01-01', 'prier@hsr.ch', 'group_manager', 'prier', NULL),
(34, 'Oliver', 'Gunsch', 'bremengunsch', '2014-01-01', 'oliver.gunsch@economics.unibz.it', 'group_manager', 'ogunsch', NULL),
(35, 'Beatrice', 'Bridi', 'wienbridi', '2014-01-01', 'beatrice.bridi@gmail.com', 'group_manager', 'bbridi', NULL),
(36, 'Elena ', 'Bianchi', 'losannabianche', '2014-01-01', 'elena_bianchi@rocketmail.com', 'group_manager', 'ebianchi', NULL),
(37, 'Constantin ', 'Huesker', 'dublinhuesker', '2014-01-01', 'constantin.huesker2@mail.dcu.ie', 'group_manager', 'chuesker', NULL),
(38, 'Maria', 'Gallego', 'canariagallego', '2014-01-01', 'maria15.pani@gmail.com', 'group_manager', 'mgallego', NULL),
(39, 'Erik', 'Hallenstal', 'linnaeushallenstal', '2014-01-01', 'erik_hallenstal@hotmail.com', 'group_manager', 'ehallenstal', NULL),
(40, 'Borja', 'Garcia Lomba', 'basquelomba', '2014-01-01', 'b.garcialomba@gmail.com', 'group_manager', 'bglomba', NULL),
(41, 'Alexandra', 'Juegelt', 'avansjuegelt', '2014-01-01', 'a.juegelt@yahoo.de', 'group_manager', 'ajuegelt', NULL),
(42, 'Louis', 'Boutroux', 'reimsboutroux', '2014-01-01', 'louis.boutroux@rms.fr', 'group_manager', 'lboutroux', NULL),
(43, 'Sebastian', 'Fettig', 'zeppelinfettig', '2014-01-01', 's.fettig@zeppelin-university.net', 'group_manager', 'sfettig', NULL),
(61, 'Rachel', 'Baron', 'mainzbaron', NULL, 'rachels.m.baron@gmail.com', 'group_manager', 'rbaron', NULL);

INSERT INTO `roles` (`role_name`, `user_username`, `group_assigned`) VALUES
('admin', 'lbellettati', 0),
('admin', 'smich', 0),
('admin', 'epic', 0),
('admin', 'murbani', 0),
('admin', 'atagliabue', 0),
('admin', 'vbonetti', 0),
('admin', 'dkaneider', 0),
('group_manager', 'adimattia', 1),
('group_manager', 'adallago', 0),
('group_manager', 'afiore', 0),
('group_manager', 'ajuegelt', 0),
('group_manager', 'amenigatti', 0),
('group_manager', 'apetruzzelli', 1),
('group_manager', 'ascaravilli', 0),
('group_manager', 'astagni', 1),
('group_manager', 'bbridi', 1),
('group_manager', 'bglomba', 1),
('group_manager', 'ccreutzburg', 1),
('group_manager', 'chuesker', 0),
('group_manager', 'ddicristo', 0),
('group_manager', 'ebianchi', 1),
('group_manager', 'ehallenstal', 0),
('group_manager', 'emaier', 0),
('group_manager', 'ezaccagnino', 1),
('group_manager', 'fdostal', 0),
('group_manager', 'fmazzer', 1),
('group_manager', 'gdepalma', 1),
('group_manager', 'johnenachname', 1),
('group_manager', 'jripken', 1),
('group_manager', 'lboutroux', 1),
('group_manager', 'lmisconel', 1),
('group_manager', 'mgallego', 1),
('group_manager', 'mkerschbamer', 0),
('group_manager', 'mweitzel', 0),
('group_manager', 'ngast', 1),
('group_manager', 'obraschi', 1),
('group_manager', 'ogunsch', 1),
('group_manager', 'prier', 0),
('group_manager', 'rsauer', 0),
('group_manager', 'sfettig', 1),
('group_manager', 'ssollwedel', 1),
('group_manager', 'rauernheimer', 0),
('group_manager', 'rbaron', 1);

INSERT INTO snowdays_enrollment.programs
VALUES (1, 'friday', 'Ski Race');

INSERT INTO snowdays_enrollment.programs
VALUES (2, 'friday', 'Snowshoe Hike');

INSERT INTO snowdays_enrollment.programs
VALUES (3, 'friday', 'Relax');

INSERT INTO snowdays_enrollment.programs
VALUES (4, 'friday', 'Snowboard Race');

INSERT INTO snowdays_enrollment.rental_options
VALUES (1, 'Only skis');

INSERT INTO snowdays_enrollment.rental_options
VALUES (2, 'Skis and boots');

INSERT INTO snowdays_enrollment.rental_options
VALUES (3, 'Only snowboard');

INSERT INTO snowdays_enrollment.rental_options
VALUES (4, 'Snowboard and boots');

INSERT INTO snowdays_enrollment.rental_options
VALUES (5, 'none');

INSERT INTO snowdays_enrollment.faculties (faculty_name, faculty_server_name)
VALUES ('Computer Science', 'stud-inf.unibz.it');

INSERT INTO snowdays_enrollment.faculties (faculty_name, faculty_server_name)
VALUES ('Economics', 'economics.unibz.it');

INSERT INTO snowdays_enrollment.faculties (faculty_name, faculty_server_name)
VALUES ('Design', 'desing-art.unibz.it');

INSERT INTO snowdays_enrollment.faculties (faculty_name, faculty_server_name)
VALUES ('Science and Technology', 'natec.unibz.it');

INSERT INTO snowdays_enrollment.faculties (faculty_name, faculty_server_name)
VALUES ('Education', 'education.unibz.it');

INSERT INTO snowdays_enrollment.countries (country_name) VALUES ('ITALY');
INSERT INTO snowdays_enrollment.countries (country_name)VALUES ('GERMANY');
INSERT INTO snowdays_enrollment.countries (country_name) VALUES ('SWITZERLAND');
INSERT INTO snowdays_enrollment.countries (country_name) VALUES ('DENMARK');
INSERT INTO snowdays_enrollment.countries (country_name) VALUES ('SPAIN');
INSERT INTO snowdays_enrollment.countries (country_name) VALUES ('SWEDEN');
INSERT INTO snowdays_enrollment.countries (country_name) VALUES ('IRELAND');

INSERT INTO snowdays_enrollment.settings VALUES ('all_blocked', 'blocked');






