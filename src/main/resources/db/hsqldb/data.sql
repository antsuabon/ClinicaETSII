-- One admin user, named admin1 with passwor 4dm1n and authority admin
-- INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
-- INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
-- INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
-- INSERT INTO authorities VALUES ('owner1','owner');
-- One vet user, named vet1 with passwor v3t
-- INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
-- INSERT INTO authorities VALUES ('vet1','veterinarian');
-- 
-- INSERT INTO vets VALUES (1, 'James', 'Carter');
-- INSERT INTO vets VALUES (2, 'Helen', 'Leary');
-- INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
-- INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
-- INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
-- INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');
-- 
INSERT INTO services VALUES (1, 'Consulta Niños');
INSERT INTO services VALUES (2, 'Consulta Adultos');
INSERT INTO services VALUES (3, 'Vacunaciones infantiles');
INSERT INTO services VALUES (4, 'Vacunación de la Gripe');
INSERT INTO services VALUES (5, 'Vacunación del Tétanos');
INSERT INTO services VALUES (6, 'Vacunación de la Hepatitis «B»');
INSERT INTO services VALUES (7, 'Prevención de Enfermedades Cardiovasculares');
INSERT INTO services VALUES (8, 'Atención a Pacientes Crónicos: Hipertensión Arterial');
INSERT INTO services VALUES (9, 'Atención a Pacientes Crónicos: Diabetes');
INSERT INTO services VALUES (10, 'Atención a Pacientes Crónicos: EPOC');
INSERT INTO services VALUES (11, 'Atención a Pacientes Crónicos: Obesidad');
INSERT INTO services VALUES (12, 'Atención a Pacientes Crónicos: Hipercolesterolemia');
INSERT INTO services VALUES (13, 'Diagnóstico Precoz del Cáncer de Cérvix');
INSERT INTO services VALUES (14, 'Atención al Consumidor Excesivo de Alcohol');
INSERT INTO services VALUES (15, 'Atención a Pacientes con VIH-SIDA');
INSERT INTO services VALUES (16, 'Cirugía Menor');
INSERT INTO services VALUES (17, 'Retinografía');
INSERT INTO services VALUES (18, 'Infintración');
INSERT INTO services VALUES (19, 'Teledermatología');

INSERT INTO constant_types VALUES (1, 'Peso');
INSERT INTO constant_types VALUES (2, 'IMC');
INSERT INTO constant_types VALUES (3, 'PerCef');
INSERT INTO constant_types VALUES (4, 'PerAbd');
INSERT INTO constant_types VALUES (5, 'Temperatura');
INSERT INTO constant_types VALUES (6, 'FrecCard');
INSERT INTO constant_types VALUES (7, 'FrecResp');
INSERT INTO constant_types VALUES (8, 'TAS');
INSERT INTO constant_types VALUES (9, 'TAD');
INSERT INTO constant_types VALUES (10, 'Diuresis');
INSERT INTO constant_types VALUES (11, 'Pulsioximetría');
INSERT INTO constant_types VALUES (12, 'PVC');
INSERT INTO constant_types VALUES (13, 'SNG');
INSERT INTO constant_types VALUES (14, 'Drenajes');
INSERT INTO constant_types VALUES (15, 'GI Basal');
INSERT INTO constant_types VALUES (16, 'SA02');
INSERT INTO constant_types VALUES (17, 'GCS');
INSERT INTO constant_types VALUES (18, 'EVA');
INSERT INTO constant_types VALUES (19, 'HbA1c');

INSERT INTO discharge_types VALUES (1, 'Domicilio');
INSERT INTO discharge_types VALUES (2, 'Hospital');
INSERT INTO discharge_types VALUES (3, 'Hospital (Acompañado de personal sanitario)');
INSERT INTO discharge_types VALUES (4, 'Revisión');
INSERT INTO discharge_types VALUES (5, 'Especialidad');
INSERT INTO discharge_types VALUES (6, 'Fuga');




-- INSERT INTO vet_specialties VALUES (2, 1);
-- INSERT INTO vet_specialties VALUES (3, 2);
-- INSERT INTO vet_specialties VALUES (3, 3);
-- INSERT INTO vet_specialties VALUES (4, 2);
-- INSERT INTO vet_specialties VALUES (5, 1);
-- 
-- INSERT INTO types VALUES (1, 'cat');
-- INSERT INTO types VALUES (2, 'dog');
-- INSERT INTO types VALUES (3, 'lizard');
-- INSERT INTO types VALUES (4, 'snake');
-- INSERT INTO types VALUES (5, 'bird');
-- INSERT INTO types VALUES (6, 'hamster');
-- 
-- INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
-- INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
-- INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
-- INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
-- INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
-- INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
-- INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
-- INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
-- INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
-- INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
-- 
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);
-- 
-- INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
-- INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
-- INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
-- INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');
-- 
