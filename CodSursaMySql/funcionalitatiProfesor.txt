-- PROFESOR
-- adauga si programeaza activitati
 DELIMITER |
  CREATE PROCEDURE adaugaActivitate(nume_activitate varchar(25), cnpp varchar(25), dataInit date, dataFinal date, numar int, nume_curs varchar(25),ora_i varchar(5),ora_s varchar(5),zi varchar(9)) 
  BEGIN
  declare ID_c, ID_p, ID_u,ID_a int;
  set ID_u=(select utilizator_id from utilizator where (cnp=cnpp));
  set ID_p=(select id_profesor from profesor where profesor.id_utilizator=ID_u);
  set ID_c=(select id_curs from cursuri where descriere=nume_curs and id_profesor=id_p);
  set ID_a=(select ID_activitate from calendar where calendar.activitate=nume_activitate and calendar.ID_profesor=id_p and calendar.id_curs=ID_c);
  if ID_a is not NULL
  then 
	update calendar
    set data_inceput=dataInit,
     data_incheiere=dataFinal,
     ora_inceput=ora_i,
     ora_incheiere=ora_s,
     calendar.zi=zi,
     numar_participanti=numar
     where calendar.ID_profesor=ID_p and calendar.ID_activitate=ID_a and calendar.id_curs=ID_c;
  else
    insert into calendar values(NULL,ID_p,ID_c,nume_activitate,dataInit,dataFinal,ora_i,ora_s,zi,numar);
  end if;
  END;
  |

-- gestionare ponderi note
drop procedure GestionarePonderi
DELIMITER |
CREATE PROCEDURE GestionarePonderi(pLab int,pCurs int,pSem int,numeCurs varchar(25),idu int)  
  BEGIN
  declare id,p,idp int;
  set idp=(select id_profesor from profesor where id_utilizator=idu);
  set id=(select id_curs from cursuri where descriere=numeCurs and id_profesor=idp);
  set p=(select pondere_laborator from ponderi_note where id_curs=id);
  if p is null
  then 
  insert into ponderi_note values(id,pLab,pSem,pCurs);
  else
  update ponderi_note
  set pondere_laborator=pLab, 
  pondere_curs=pCurs,
  pondere_seminar=pSem
  where ID_curs=id;
  end if;
  END;
  |

DELIMITER |
CREATE PROCEDURE utId(cnpp varchar(25))  
  BEGIN
  select utilizator_id from utilizator
  where cnp=cnpp;
  END;
  |
-- notare studenti
-- ex: 20% seminar, 35% laborator, 45% examen
drop procedure notare;
DELIMITER |
CREATE PROCEDURE notare(num varchar(25),prenum varchar(25),notaS numeric(4,2),notaL numeric(4,2),notaC numeric(4,2),numeCurs varchar(25))
  BEGIN
   	DECLARE id int;
    DECLARE idC int;
    DECLARE pS,pC,pL,idv int;
    set id=(SELECT ID_student from student inner join utilizator on utilizator.utilizator_id=student.ID_utilizator and utilizator.nume=num and utilizator.prenume=prenum);
     set idC=(select cursuri.id_curs from cursuri inner join inscriere_curs on (inscriere_curs.id_student=id and inscriere_curs.id_curs=cursuri.id_curs)where cursuri.descriere=numeCurs);
    set pS=(SELECT pondere_seminar from ponderi_note where ID_curs=idC);
    set pC=(SELECT pondere_curs from ponderi_note where ID_curs=idC);
    set pL=(SELECT pondere_laborator from ponderi_note where ID_curs=idC);
	set idV=(SELECT ID_nota from note where id_student=id and id_curs=idC);
    if(idV is NULL)
		then
			if pL=0 
			then
				if pS=0
					then insert into note values(NULL,id,idC,notaC,0,0,0.00);
				else
					insert into note values(NULL,id,idC,notaC,0,notaS,0.00);
				end if;
			else
			if pS=0
				then 
					insert into note values(NULL,id,idC,notaC,notaL,0,0.00);
			else
				insert into note values(NULL,id,idC,notaC,notaL,notaS,0.00);
			end if;
		end if;
        update note set medie=((nota_seminar*pS/100)+(nota_laborator*pL/100)+(nota_curs*pC/100)) where id_student=id and id_curs=idC;
	else
		if pL=0 
        then
			if pS=0
            then
            update note set nota_laborator=0, nota_seminar=0;
            else
            update note set nota_laborator=0;
            end if;
		else
			if pS=0
            then 
				update note set nota_seminar=0;
			else
				update note set nota_curs=notaC, nota_laborator=notaL, nota_seminar=notaS, medie=0.00 where id_nota=idV;
			end if;
		end if;
        update note set medie=((nota_seminar*pS/100)+(nota_laborator*pL/100)+(nota_curs*pC/100))where id_student=id and id_curs=idC;
	end if;
    update note set medie=((nota_seminar*pS/100)+(nota_laborator*pL/100)+(nota_curs*pC/100)) where id_student=id and id_curs=idC;
  END;
  |

DELIMITER |
CREATE PROCEDURE viz_note(num varchar(25),prenum varchar(25),numeCurs varchar(25))
  BEGIN
   	DECLARE id int;
    DECLARE idC int;
    DECLARE pS,pC,pL,idv int;
	set id=(SELECT ID_student from student inner join utilizator on utilizator.utilizator_id=student.ID_utilizator and utilizator.nume=num and utilizator.prenume=prenum);
        set idC=(select cursuri.id_curs from cursuri inner join inscriere_curs on (inscriere_curs.id_student=id and inscriere_curs.id_curs=cursuri.id_curs)where cursuri.descriere=numeCurs);
    SELECT nota_curs,nota_laborator, nota_seminar, medie
    from note
    where id_student=id and id_curs=idC;
  END;
  |


DELIMITER |
CREATE PROCEDURE viz_inf_as_profesor(cnpp varchar(25))
  BEGIN
   SELECT *
   from utilizator
   where utilizator.cnp=cnpp;
  END;
  |

-- v
-- vizualizare liste studenti si descarcare catalog
DELIMITER |
CREATE PROCEDURE lista_studenti(nume_curs varchar(25), CNPP varchar(25))
  BEGIN
  DECLARE idP,idU,idC int;
  set idU=(select utilizator_id from utilizator where CNP=CNPP);
  set idP=(select id_profesor from profesor where profesor.id_utilizator=idU);
  set idC=(select id_curs from cursuri where descriere=nume_curs and id_profesor=idP);
   SELECT utilizator.nume, utilizator.prenume 
	from utilizator
	inner join student on utilizator.utilizator_id=student.ID_utilizator
	inner join inscriere_curs on student.id_student=inscriere_curs.id_student 
    where inscriere_curs.ID_curs=idC
	order by utilizator.nume;
  END;
  |
  use proiect;
  drop procedure lista_studenti;
-- vizualizare/ descarcare activitati curente/ viitoare
DELIMITER |
CREATE PROCEDURE vizualizare_activitati_curente_AS_profesor(cnpp varchar(25))
  BEGIN
  declare idP,idU int;
  set idU=(select utilizator_id from utilizator where cnp=cnpp);
  set idP=(select id_profesor from profesor where profesor.id_utilizator=idU);
	SELECT utilizator.nume, utilizator.prenume, calendar.activitate as 'ACTIVITATI CURENTE', calendar.data_inceput as "DATA INCEPUT", calendar.ora_inceput, calendar.ora_incheiere, calendar.zi
	from utilizator
	inner join profesor on utilizator.utilizator_id=profesor.ID_utilizator
	inner join calendar on profesor.ID_profesor=calendar.ID_profesor
	where calendar.data_inceput<=current_date and calendar.data_incheiere>=current_date and profesor.id_profesor=idP;
  END;
  |

DELIMITER |
CREATE PROCEDURE vizualizare_activitati_viitoare_AS_profesor(cnpp varchar(25))
  BEGIN
  declare idP,idU int;
  set idU=(select utilizator_id from utilizator where (cnp=cnpp));
  set idP=(select id_profesor from profesor where profesor.id_utilizator=idU);
	SELECT utilizator.nume, utilizator.prenume, calendar.activitate as 'ACTIVITATI VIITOARE',calendar.data_inceput as "DATA INCEPUT", calendar.ora_inceput, calendar.ora_incheiere, calendar.zi
	from utilizator
	inner join profesor on utilizator.utilizator_id=profesor.ID_utilizator
	inner join calendar on profesor.ID_profesor=calendar.ID_profesor
	where calendar.data_inceput>current_date and profesor.id_profesor=idP;
  END;
  |
  
use proiect;
drop procedure vizualizare_activitati_viitoare_AS_profesor;
DELIMITER |
CREATE PROCEDURE vizualizare_cursuri(cnpp varchar(25))
  BEGIN
  declare idP,idU int;
  set idU=(select utilizator_id from utilizator where (cnp=cnpp));
  set idP=(select id_profesor from profesor where profesor.id_utilizator=idU);
  select descriere from cursuri where id_profesor=idP;
  
  END;
  |

DELIMITER //
CREATE PROCEDURE vizualizare_activitati_zi_curenta_AS_profesor (CNP VARCHAR(25))
BEGIN
	declare id_p,id_u int;
    set id_u=(select utilizator_id from utilizator where utilizator.cnp=CNP);
    set id_p=(select id_profesor from profesor where id_utilizator=id_u);
    
    SELECT utilizator.nume as 'Nume profesor', utilizator.prenume as 'Prenume profesor', cursuri.descriere as 'Curs', calendar.activitate as 'Activitate', calendar.ora_inceput as 'Ora incepere', calendar.ora_incheiere as 'Ora terminare' 
    from cursuri
    join calendar on cursuri.id_curs = calendar.id_curs
    join profesor on calendar.id_profesor = profesor.id_profesor
    join utilizator on profesor.id_utilizator = utilizator.utilizator_id
    where calendar.data_inceput < current_date and calendar.data_incheiere > current_date
    and calendar.zi = (SELECT dayname(current_date ))
    and cursuri.id_profesor=id_p and utilizator.utilizator_id=id_u;
END; //

  

