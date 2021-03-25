
----------------------------------- Functionalitati administrator ---------------------

-- insereaza utilizator 
delimiter //
create procedure insert_utilizator(CNP VARCHAR(25),nume VARCHAR(25),prenume VARCHAR(25),adresa VARCHAR(25),IBAN VARCHAR(25),numar_telefon VARCHAR(25),numar_contract INT,email VARCHAR(25))
begin
insert into utilizator(CNP,nume,prenume,adresa,IBAN,numar_telefon,numar_contract,email) values (CNP,nume,prenume,adresa,IBAN,numar_telefon,numar_contract,email);
end;//

-- insereaza student
delimiter //
create procedure insert_student(ID_utilizator INT,an_studiu INT,ore INT)
begin
insert into student(ID_utilizator,an_studiu,ore) values (ID_utilizator,an_studiu,ore);
end;//

-- insereaza profesor
delimiter //
create procedure insert_profesor(ID_utilizator INT,numar_minim_ore INT,numar_maxim_ore INT, departament VARCHAR(25),numar_total_studenti INT)
begin
insert into profesor(ID_utilizator,numar_minim_ore,numar_maxim_ore, departament,numar_total_studenti)
values (ID_utilizator,numar_minim_ore,numar_maxim_ore, departament,numar_total_studenti);
end;//

-- super-administratorul poate sa insereze administratori
delimiter //
create procedure insert_administrator(ID_utilizator1 INT,super_administrator1 BOOLEAN,nume1 varchar(25),prenume1 varchar(25))
begin
declare ids boolean;
set ids=(select super_administrator from administrator,utilizator where utilizator_ID=ID_utilizator and utilizator.nume=nume1 and utilizator.prenume=prenume1);
if ids=true
then
insert into administrator(ID_utilizator,super_administrator) values (ID_utilizator1,super_administrator1);
end if;
end;//

-- sterge un stuent
delimiter //
create procedure delete_student(nume varchar(25),prenume varchar(25),cnp varchar(25))
begin
declare id int; 
set id=(select ID_student from student,utilizator where utilizator_ID=ID_utilizator and utilizator.nume=nume and utilizator.prenume=prenume and utilizator.CNP=cnp);
delete from note 
where note.id_student=id;
delete from inscriere_curs
where inscriere_curs.ID_student=id; 
delete from student
where student.ID_student=id;
end;//
drop procedure delete_student;

-- sterge un profesor
delimiter //
create procedure delete_profesor(nume varchar(25),prenume varchar(25),cnp varchar(25))
begin
declare id int; 
set id=(select ID_profesor from profesor,utilizator where utilizator_ID=ID_utilizator and utilizator.nume=nume and utilizator.prenume=prenume and utilizator.CNP=cnp);
delete from calendar
where calendar.ID_profesor=id;
delete from curs_profesor
where curs_profesor.ID_profesor=id;
delete from profesor 
where profesor.ID_profesor=id;
end;//

-- super-administratorul poate sa sterga un administrator
delimiter //
create procedure delete_administrator(nume varchar(25),prenume varchar(25),nume1 varchar(25),prenume1 varchar(25),cnp varchar(25))
begin
declare id int; 
if true=(select super_administrator from administrator,utilizator where utilizator_ID=ID_utilizator and utilizator.nume=nume1 and utilizator.prenume=prenume1)
then
set id=(select ID_administrator from administrator,utilizator where utilizator_ID=ID_utilizator and utilizator.nume=nume and utilizator.prenume=prenume and utilizator.CNP=cnp);
delete from administrator
where administrator.ID_administrator=id;
end if;
end;//


delimiter //
create procedure update_adresa(nume varchar(25),prenume varchar(25),adresa varchar(25),cnp varchar(25))
begin
declare id int; 
set id=(select utilizator_id from utilizator where utilizator.nume=nume and utilizator.prenume=prenume and utilizator.CNP=cnp);
update utilizator set adresa=adresa
where utilizator.utilizator_id=id;
end;//


delimiter //
create procedure update_email(nume varchar(25),prenume varchar(25),email varchar(25),cnp varchar(25))
begin
declare id int; 
set id=(select utilizator_id from utilizator where utilizator.nume=nume and utilizator.prenume=prenume and utilizator.CNP=cnp);
update utilizator set email=email
where utilizator.utilizator_id=id;
end;//

delimiter //
create procedure update_telefon(nume varchar(25),prenume varchar(25),numar_telefon varchar(25),cnp varchar(25))
begin
declare id int; 
set id=(select utilizator_id from utilizator where utilizator.nume=nume and utilizator.prenume=prenume and utilizator.CNP=cnp);
update utilizator set numar_telefon=numar_telefon
where utilizator.utilizator_id=id;
end;//

delimiter //
create procedure update_contract(nume varchar(25),prenume varchar(25),numar_contract varchar(25),cnp varchar(25))
begin
declare id int; 
set id=(select utilizator_id from utilizator where utilizator.nume=nume and utilizator.prenume=prenume and utilizator.CNP=cnp);
update utilizator set numar_contract=numar_contract
where utilizator.utilizator_id=id;
end;//

delimiter //
create procedure update_iban(nume varchar(25),prenume varchar(25),IBAN varchar(25),cnp varchar(25))
begin
declare id int; 
set id=(select utilizator_id from utilizator where utilizator.nume=nume and utilizator.prenume=prenume and utilizator.CNP=cnp);
update utilizator set IBAN=IBAN
where utilizator.utilizator_id=id;
end;//

delimiter //
create procedure update_nrore(nume varchar(25),prenume varchar(25),numar_maxim_ore INT,cnp varchar(25))
begin
declare id int; 
set id=(select ID_profesor from profesor,utilizator where utilizator_ID=ID_utilizator and utilizator.nume=nume and utilizator.prenume=prenume and utilizator.CNP=cnp);
update profesor set numar_maxim_ore=numar_maxim_ore
where profesor.ID_profesor=id;
end;//

delimiter //
create procedure update_an(nume varchar(25),prenume varchar(25),an_studiu INT,cnp varchar(25))
begin
declare id int; 
set id=(select ID_student from student,utilizator where utilizator_ID=ID_utilizator and utilizator.nume=nume and utilizator.prenume=prenume and utilizator.CNP=cnp);
update student set an_studiu=an_studiu
where student.ID_student=id;
end;//

-- cauta un utilazator dupa nume
delimiter //
create procedure cauta_nume(nume varchar(25),prenume varchar(25))
begin
select CNP,nume,prenume,adresa,IBAN,numar_telefon,numar_contract,email
from utilizator
where utilizator.nume=nume
and utilizator.prenume=prenume;
end;//

-- filtreaza si afiseaza studentii
delimiter //
create procedure filtare_studenti()
begin
select CNP,nume,prenume
from utilizator,student
where utilizator_ID=ID_utilizator;
end;//


-- filtraza si afiseaza profesorii
delimiter //
create procedure filtare_profesori()
begin
select CNP,nume,prenume
from utilizator,profesor
where utilizator_ID=ID_utilizator;
end;//

-- filtreaza si afiseaza administratorii
delimiter //
create procedure filtare_administratori()
begin
select CNP,nume,prenume
from utilizator,administrator
where utilizator_ID=ID_utilizator;
end;//

-- asigneaza un profesor la un curs

delimiter //
create procedure asignare_prof(nume1 varchar(25),prenume1 varchar(25),numecurs varchar(25))
begin
declare id int; 
declare idcurs int;
declare idu int;

set id=(select ID_profesor from profesor,utilizator where utilizator_ID=ID_utilizator and utilizator.nume=nume1 and utilizator.prenume=prenume1);
set idcurs=(select ID_curs from cursuri where cursuri.descriere=numecurs LIMIT 1); 
set idu=(select id from curs_profesor where id_profesor=id and id_curs=idcurs);
if idu is null
then
insert into curs_profesor(ID_curs,ID_profesor)  values (idcurs,id);
update cursuri set id_profesor=id where id_curs=idcurs;
end if;
end;//


-- cauta un curs si afiseaza numele profesorului de la curs
delimiter //
create procedure cauta_curs(nume varchar(25))
begin
declare id int;
set id=(select ID_curs from cursuri where cursuri.descriere=nume LIMIT 1); 
select utilizator.nume,utilizator.prenume
from utilizator,profesor,curs_profesor
where utilizator.utilizator_id=profesor.ID_utilizator
and profesor.ID_profesor=curs_profesor.ID_profesor
and curs_profesor.ID_curs=id;
end;//

delimiter //
create procedure cauta_studenti(nume1 varchar(25))
begin
declare id int;
set id=(select ID_curs from cursuri where cursuri.descriere=nume1 LIMIT 1); 
select utilizator.nume,utilizator.prenume
from utilizator,student,inscriere_curs
where utilizator.utilizator_id=student.ID_utilizator
and student.ID_student=inscriere_curs.ID_student
and inscriere_curs.ID_curs=id;
end;//

delimiter //
create procedure viz_info(nume varchar(25),prenume varchar(25))
begin
select CNP,nume,prenume,adresa,IBAN,numar_telefon,numar_contract,email
from utilizator
where utilizator.prenume=prenume
and utilizator.nume=nume;
end;//

delimiter //
create procedure cauta_id(nume varchar(25),prenume varchar(25))
begin
select utilizator_id
from utilizator
where utilizator.nume=nume
and utilizator.prenume=prenume;
end;//

delimiter //
create procedure este_super(nume varchar(25),prenume varchar(25))
begin
select ID_administrator
from administrator,utilizator
where utilizator_id=ID_utilizator
and utilizator.nume=nume
and utilizator.prenume=prenume
and administrator.super_administrator=true;
end;//

delimiter //
create procedure cauta_idcurs(nume varchar(25))
begin
select ID_curs 
from cursuri
where descriere=nume LIMIT 1;
end;//

