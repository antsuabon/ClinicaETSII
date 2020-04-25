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


INSERT INTO users (id, username, password, enabled, name, surname, dni, email, phone) VALUES
(1, 'doctor1', 'doctor1', true, 'Pablo', 'Rodriguez Garrido','12345678N','pablo@gmail.com','956784325');
INSERT INTO doctors (doctor_id, collegiate_code) VALUES (1,'303012345');
INSERT INTO authorities VALUES (1,'doctor');

INSERT INTO users (id, username, password, enabled, name, surname, dni, email, phone) VALUES
(2, 'doctor2', 'doctor2', true, 'María', 'Laso Escot','12345678S','maria@gmail.com','956787025');
INSERT INTO doctors (doctor_id, collegiate_code) VALUES (2,'303092345');
INSERT INTO authorities VALUES (2,'doctor');


INSERT INTO users (id, username, password, enabled, name, surname, dni, email, phone) VALUES
(3, 'doctor3', 'doctor3', true, 'José', 'Salado Asenjo','12345678P','jose@gmail.com','956780025');
INSERT INTO doctors (doctor_id, collegiate_code) VALUES (3,'303012445');
INSERT INTO authorities VALUES (3,'doctor');

INSERT INTO users (id, username, password, enabled, name, surname, dni, email, phone) VALUES
(7, 'doctor4', 'doctor4', true, 'Pepe', 'Loco Malicioso','12345678L','loco@gmail.com','956780025');
INSERT INTO doctors (doctor_id, collegiate_code) VALUES (7,'303012545');
INSERT INTO authorities VALUES (7,'doctor');

INSERT INTO users (id, username, password, enabled, name, surname, dni, email, phone) VALUES
(6, 'administrative1', 'administrative1', true, 'Jesús', 'Fernandez Rodríguez','12345678P','jesús@gmail.com','983762578');
INSERT INTO authorities VALUES (6,'administrative');

INSERT INTO users (id, username, password, enabled, name, surname, dni, email, phone) VALUES
(8, 'admin', 'admin', true, 'Jesús', 'Fernandez Rodríguez','12345678P','jesús@gmail.com','983762578');
INSERT INTO authorities VALUES (8,'admin');

INSERT INTO doctor_services (doctor_id, service_id) VALUES (2,1);
INSERT INTO doctor_services (doctor_id, service_id) VALUES (3,10);
INSERT INTO doctor_services (doctor_id, service_id) VALUES (3,13);
INSERT INTO doctor_services (doctor_id, service_id) VALUES (3,2);
INSERT INTO doctor_services (doctor_id, service_id) VALUES (1,2);


INSERT INTO users (id, username, password, enabled, name, surname, dni, email, phone) VALUES
(4, 'patient1', 'patient1', true, 'Alejandro', 'Sánchez Saavedra','12345678N','alejandro@gmail.com','956784225');
INSERT INTO patients (patient_id, nss, birth_date, phone2, address, state, general_practitioner_id) VALUES
(4,'12345678S','1982-02-22','953333333','C/Calle de ejemplo','Sevilla',1);
INSERT INTO authorities VALUES (4,'patient');
INSERT INTO users (id, username, password, enabled, name, surname, dni, email, phone) VALUES
(5, 'patient2', 'patient2', true, 'Maria', 'Laso Escot','12345675N','maria@gmail.com','956787225');
INSERT INTO patients (patient_id, nss, birth_date, phone2, address, state, general_practitioner_id) VALUES
(5,'12345778S','1999-02-22','953334333','C/Laso','Utrera',2);
INSERT INTO authorities VALUES (5,'patient');
INSERT INTO users (id, username, password, enabled, name, surname, dni, email, phone) VALUES
(9, 'patient3', 'patient3', true, 'Jaime', 'Rovira Vázquez','12435675S','jaime@gmail.com','956784321');
INSERT INTO patients (patient_id, nss, birth_date, phone2, address, state, general_practitioner_id) VALUES
(9,'12345778S','1999-08-28','953334333','C/García Lorca','Utrera',2);
INSERT INTO authorities VALUES (9,'patient');
INSERT INTO users (id, username, password, enabled, name, surname, dni, email, phone) VALUES
(10, 'patient4', 'patient4', true, 'Lucas', 'Perez Lopez','12435655S','lucas@gmail.com','956784321');
INSERT INTO patients (patient_id, nss, birth_date, phone2, address, state, general_practitioner_id) VALUES
(10,'12345678912','1999-08-28','953334333','C/García Lorca','Utrera',2);
INSERT INTO authorities VALUES (10,'patient');

INSERT INTO appointments (id, priority, start_time, end_time, patient_id) VALUES
(1, false, '2020-03-07 11:00:00', '2020-03-07 11:07:00', 4);
INSERT INTO appointments (id, priority, start_time, end_time, patient_id) VALUES
(2, false, '2020-03-09 11:00:00', '2020-03-09 11:07:00', 4);
INSERT INTO appointments (id, priority, start_time, end_time, patient_id) VALUES
(3, false, '2020-03-12 11:00:00', '2020-03-13 11:07:00', 5);
INSERT INTO appointments (id, priority, start_time, end_time, patient_id) VALUES
(4, false, '2020-03-14 11:00:00', '2020-03-14 11:07:00', 4);


INSERT INTO medicines (id, generical_name, commercial_name, quantity, indications, contraindications) VALUES
(1,'Ibuprofeno','Dalsy',1.0,'Dolor leve y moderado','En síndrome de pólipos nasales, angioedema y reactividad broncoespástica a aspirina u otros AINEs.');
INSERT INTO medicines (id, generical_name, commercial_name, quantity, indications, contraindications) VALUES
(2,'Paracetamol','Paracel',1.0,'Dolor y fiebre','Paracetamol debe utilizarse con precaución en alcohólicos crónicos y en pacientes con deficiencia en glucosa-6 fosfato-deshidrogenasa.');
INSERT INTO medicines (id, generical_name, commercial_name, quantity, indications, contraindications) VALUES
(3,'Omeprazol','Arapride',1.0,'Ulcera duodenal o gástrica','Debe utilizarse con precaución durante el embarazo y lactancia.');


INSERT INTO consultations (id,start_time,end_time,anamnesis,remarks,discharge_type_id,appointment_id) VALUES
(1,'2020-03-07 11:00:00','2020-03-07 11:07:00', 'Dolor de estómago','Fiebres altas',4,1);
INSERT INTO consultations (id,start_time,end_time,anamnesis,remarks,discharge_type_id,appointment_id) VALUES
(2,'2020-03-09 11:00:00','2020-03-09 11:07:00', 'Dolor de rodilla','Inflamación',2,2);
INSERT INTO consultations (id,start_time,end_time,anamnesis,remarks,discharge_type_id,appointment_id) VALUES
(3,'2020-03-12 11:00:00','2020-03-12 11:07:00', 'Vómitos','Fiebre moderada',4,3);

INSERT INTO diagnoses (id, name) VALUES (1,'Gripe invernal');
INSERT INTO diagnoses (id, name) VALUES (2,'Resfriado Severo');
INSERT INTO diagnoses (id, name) VALUES (3,'Rotura de rodilla');

INSERT INTO consultation_diagnoses (consultation_id, diagnosis_id) VALUES (1,1);
INSERT INTO consultation_diagnoses (consultation_id, diagnosis_id) VALUES (1,2);
INSERT INTO consultation_diagnoses (consultation_id, diagnosis_id) VALUES (2,3);

INSERT INTO constants (id, value_constant, constant_type_id) VALUES (1,75.0,1);
INSERT INTO constants (id, value_constant, constant_type_id) VALUES (2,39.0,5);
INSERT INTO constants (id, value_constant, constant_type_id) VALUES (3,60.0,1);

INSERT INTO consultation_constants (consultation_id, constant_id) VALUES (1,1);
INSERT INTO consultation_constants (consultation_id, constant_id) VALUES (1,2);
INSERT INTO consultation_constants (consultation_id, constant_id) VALUES (3,3);


INSERT INTO examinations (id, start_time, description,consultation_id) VALUES (1, '2020-03-07 11:00:00', 'Tiene el vientre muy hinchado',1);
INSERT INTO examinations (id, start_time, description,consultation_id) VALUES (2, '2020-04-07 11:00:00', 'Tiene la cara pálida',1);
INSERT INTO examinations (id, start_time, description,consultation_id) VALUES (3, '2020-05-07 11:00:00', 'Tiene la rodilla enrojecida',2);

INSERT INTO prescriptions (id, start_date,dosage,days,pharmaceutical_warning,patient_warning,medicine_id,patient_id,doctor_id) VALUES
(1,'2020-03-09 11:00:00',1,7,'Vender solo con receta','Puede provocar efectos secundarios',1,4,1);
INSERT INTO prescriptions (id, start_date, dosage, days, pharmaceutical_warning, patient_warning, medicine_id, patient_id, doctor_id) VALUES
(2,'2020-02-20 13:00:00', 3, 20, 'No tomar sin agua', 'No fragmentar', 2, 4, 1);

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
