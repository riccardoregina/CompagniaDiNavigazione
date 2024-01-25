------------------------------------------
-- trigger per aggiornare i posti (per passeggeri) disponibili per una corsa specifica
create trigger triggeraggiornapostipasseggero
    after insert
    on biglietto
    for each row
execute procedure aggiornapostipasseggero();

create function aggiornapostipasseggero() returns trigger
    language plpgsql
as
$$
declare
    v_corsa_sup navigazione.corsaregolare.corsasup%type;
begin
    select corsasup into v_corsa_sup
    from navigazione.corsaregolare
    where idcorsa = new.idcorsa;

    raise notice 'corsa sup %', v_corsa_sup;
    --se il biglietto acquistato è per una corsa principale 
    if v_corsa_sup is null then 
        --aggiorna i posti disponibili anche per le sottocorse
        update navigazione.corsaspecifica
        set postidisppass = postidisppass - 1
        where data = new.data and idcorsa in (select CR.idcorsa
                                              from navigazione.corsaregolare as CR
                                              where CR.corsasup = new.idcorsa or CR.idcorsa = new.idcorsa);
    else --se invece il biglietto acquistato è per una sottotratta 
        --aggiorna i posti per la sottotratta in questione
        update navigazione.corsaspecifica
        set postidisppass = postidisppass - 1
        where idcorsa = new.idCorsa and data = new.data;
        
        --aggiorna i posti per la corsa principale
        update navigazione.corsaspecifica
        set postidisppass = postidisppass - 1
        where idcorsa = v_corsa_sup and data = new.data;
    end if;
    
    return null;
end;
$$;

----------------------------------------
-- trigger per aggiornare i posti (per veicoli) disponibili per una corsa specifica
create trigger triggeraggiornapostiveicolo
    after insert
    on biglietto
    for each row
    when (new.veicolo IS NOT NULL)
execute procedure aggiornapostiveicolo();

create function aggiornapostiveicolo() returns trigger
    language plpgsql
as
$$
declare
    v_corsa_sup navigazione.corsaregolare.corsasup%type;
begin
    select corsasup into v_corsa_sup
    from navigazione.corsaregolare
    where idcorsa = new.idcorsa;

    --se il biglietto acquistato è per una corsa principale 
    if v_corsa_sup is null then
        --aggiorna i posti disponibili anche per le sottocorse
        update navigazione.corsaspecifica
        set postidispvei = postidispvei - 1
        where data = new.data and idcorsa in (select CR.idcorsa
                                              from navigazione.corsaregolare as CR
                                              where CR.corsasup = new.idcorsa or CR.idcorsa = new.idcorsa);
    else --se invece il biglietto acquistato è per una sottotratta 
        --aggiorna i posti per la sottotratta in questione
        update navigazione.corsaspecifica
        set postidispvei = postidispvei - 1
        where idcorsa = new.idCorsa and data = new.data;

        --aggiorna i posti per la corsa principale
        update navigazione.corsaspecifica
        set postidispvei = postidispvei - 1
        where idcorsa = v_corsa_sup and data = new.data;
    end if;

    return null;
end;
$$;

----------------------------------------------------
-- all'inserimento di uno scalo per una corsaregolare, questo trigger si occupa di generare le sottocorse e di attivarle nei periodi
--in cui è attiva la corsa principale.
create trigger generatrattescalotrigger
    after insert
    on scalo
    for each row
execute procedure aggiungicorsescalo();

create function aggiungicorsescalo() returns trigger
    language plpgsql
as
$$
declare
    v_record navigazione.corsaregolare%rowtype;
    v_ultimo_idcorsa navigazione.corsaregolare.idcorsa%type;
    --seleziona tutti i periodi in cui è attiva la corsa principale
    cur_periodi cursor for
        select idperiodo
        from navigazione.attivain
        where idcorsa = new.idcorsa;
begin
    --seleziona in corsaRegolare l'intera tupla corrispondente alla corsa principale
    select * into v_record
    from navigazione.corsaregolare
    where idcorsa = new.idcorsa;

    --inserisce in corsaRegolare la prima sottotratta che parte dal porto di partenza della corsa principale
    --e arriva al porto di scalo
    insert into navigazione.corsaregolare(portopartenza, portoarrivo, orariopartenza, orarioarrivo,
                                          costointero, scontoridotto, costobagaglio, costoprevendita,
                                          costoveicolo, compagnia, natante, corsasup)
    values
        (v_record.portopartenza, new.porto, v_record.orariopartenza, new.orarioAttracco, v_record.costointero,
         v_record.scontoridotto, v_record.costobagaglio, v_record.costoprevendita, v_record.costoveicolo,
         v_record.compagnia, v_record.natante, new.idcorsa);

    --seleziona quest'ultima corsa inserita
    select idcorsa into v_ultimo_idcorsa
    from navigazione.corsaregolare
    order by idcorsa desc
    limit 1;

    --e l'attiva in tutti i periodi in cui era attiva la corsa principale
    for p in cur_periodi loop
        insert into navigazione.attivain
        values (v_ultimo_idcorsa, p.idperiodo);
    end loop;

    --inserisce in corsaRegolare la seconda sottotratta che parte dal porto di scalo 
    --e arriva al porto di arrivo della corsa principale
    insert into navigazione.corsaregolare(portopartenza, portoarrivo, orariopartenza, orarioarrivo,
                                          costointero, scontoridotto, costobagaglio, costoprevendita,
                                          costoveicolo, compagnia, natante, corsasup)
    values
        (new.porto, v_record.portoarrivo, new.orarioRipartenza, v_record.orarioarrivo, v_record.costointero,
         v_record.scontoridotto, v_record.costobagaglio, v_record.costoprevendita, v_record.costoveicolo,
         v_record.compagnia, v_record.natante, new.idcorsa);

    --seleziona quest'ultima corsa inserita
    select idcorsa into v_ultimo_idcorsa
    from navigazione.corsaregolare
    order by idcorsa desc
    limit 1;

    --e l'attiva in tutti i periodi in cui era attiva la corsa principale
    for p in cur_periodi loop
            insert into navigazione.attivain
            values (v_ultimo_idcorsa, p.idperiodo);
    end loop;
    
    return new;
end;
$$;

------------------------------
-- trigger per attivare nei periodi della corsa principale anche le sue sottocorse
create trigger attivasottocorsetrigger
    after insert
    on attivain
    for each row
execute procedure attivasottocorse();

create function attivasottocorse() returns trigger
    language plpgsql
as
$$
declare
    --seleziona le due sottocorse della corsa in questione
    cur_sottocorse cursor for
        select idcorsa
        from navigazione.corsaregolare
        where corsasup = new.idcorsa;
begin
    --e attiva ognuna nel nuovo periodo inserito
    for t in cur_sottocorse loop
        insert into navigazione.attivain
        values (t.idcorsa, new.idperiodo);
    end loop;

    return new;
end;
$$;

------------------------------------------
--trigger che quando viene cambiato l'orario di arrivo della corsa principale
--aggiorna l'orario di arrivo anche della sottocorsa che parte dal porto di scalo
create trigger cambiaorarioarrivoinsottocorsa
    after update
        of orarioarrivo
    on corsaregolare
    for each row
    --quando è una corsa principale
    when (new.corsasup IS NULL AND new.orarioarrivo IS NOT NULL)
execute procedure cambiaorarioarrivoinsottocorsa();

create function cambiaorarioarrivoinsottocorsa() returns trigger
    language plpgsql
as
$$
declare
    sottocorsa navigazione.corsaregolare%rowtype;
begin
    --seleziona la sottocorsa che arriva allo stesso orario della corsa principale
    select * into sottocorsa
    from navigazione.corsaregolare
    where corsasup = old.idcorsa and orarioarrivo = old.orarioarrivo;

    --aggiorna l'orario di arrivo
    update navigazione.corsaregolare
    set orarioarrivo = new.orarioarrivo
    where idcorsa = sottocorsa.idcorsa;

    return new;
end;
$$;

--------------------------------------------
--trigger che quando viene cambiato l'orario di partenza della corsa principale
--aggiorna l'orario di partenza anche della sottocorsa che parte dallo stesso porto
create trigger cambiaorariopartenzainsottocorsa
    after update
        of orariopartenza
    on corsaregolare
    for each row
    when (new.corsasup IS NULL AND new.orariopartenza IS NOT NULL)
execute procedure cambiaorariopartenzainsottocorsa();

create function cambiaorariopartenzainsottocorsa() returns trigger
    language plpgsql
as
$$
declare
    sottocorsa navigazione.corsaregolare%rowtype;

begin
    --seleziona la sottocorsa che parte allo stesso orario della corsa principale
    select * into sottocorsa
    from navigazione.corsaregolare
    where corsasup = old.idcorsa and orariopartenza = old.orariopartenza;

    --aggiorna l'orario di partenza
    update navigazione.corsaregolare
    set orariopartenza = new.orariopartenza
    where idcorsa = sottocorsa.idcorsa;

    return new;
end;
$$;

-------------------------------------
--trigger per eliminare le corse specifiche di una corsa regolare non più attiva in un periodo
--ed eliminare i periodi che non sono attaccati a nessuna corsa
create trigger cancellacorse
    after delete
    on attivain
    for each row
execute procedure cancellacorseinperiodo();

create function cancellacorseinperiodo() returns trigger
    language plpgsql
as
$$
declare
    v_data_inizio navigazione.periodo.datainizio%type;
    v_data_fine navigazione.periodo.dataFine%type;
    v_data_corrente date;
    v_giorni bit(7);
    v_day integer;
    v_offset integer;
    --trovo i periodi che non sono attaccati a nessuna corsa
    cur_periodi cursor for
        select P.idperiodo
        from navigazione.periodo as P
        where P.idperiodo not in (select idperiodo 
                                  from navigazione.attivain);
begin
    select datainizio, datafine, giorni into v_data_inizio, v_data_fine, v_giorni
    from navigazione.periodo
    where idperiodo = old.idperiodo;

    --ricava un numero da 0 a 6 corrispondente al giorno della settimana in cui inizia il periodo
    -- 0 se domenica, 1 se lunedì ... fino a 6 se sabato
    v_day := extract(dow from v_data_inizio::timestamp);
    --per ogni bit della stringa di bit che indica i giorni di attività
    for i in 0..6 loop
        --ogni iterazione del for-loop parte con v_data_corrente uguale alla data di inizio
        v_data_corrente := v_data_inizio;
        --se l'i-esimo bit è 1
        if get_bit(v_giorni, i) = 1 then
            v_offset := (i - v_day);
            if v_offset < 0 then
                while v_offset < 0 loop
                        v_offset := v_offset + 7;
                end loop;
            else
                v_offset := v_offset % 7;
            end if;
            --aggiunge l'offset alla data corrente affinchè la data corrente
            --si trovi nella prima data maggiore o uguale la data di inizio,
            --che abbia come giorno della settimana l'i-esimo giorno
            v_data_corrente := v_data_corrente + v_offset;
            --per tutte le date con quel giorno incluse nel periodo
            while v_data_corrente <= v_data_fine loop
                --elimina la corsa specifica per quella data
                delete from navigazione.corsaspecifica
                where idcorsa = old.idcorsa AND data = v_data_corrente;
                --incrementa la data alla settimana successiva
                v_data_corrente := v_data_corrente + 7;
            end loop;
        end if;
    end loop;

    --elimina i periodi che non sono attaccati a nessuna corsa
    for p in cur_periodi loop
        delete from navigazione.periodo
        where idperiodo = p.idperiodo;
    end loop;
    
    return null;
end;
$$;

------------------------------------------------
--Trigger per eliminare le sottocorse dopo che è stato eliminato uno scalo
create trigger eliminatrattescalotrigger
    after delete
    on scalo
    for each row
execute procedure eliminacorsescalo();

create function eliminacorsescalo() returns trigger
    language plpgsql
as
$$
begin
    delete from navigazione.corsaregolare
    where corsasup = old.idcorsa AND (portopartenza = old.porto OR portoarrivo = old.porto);
    
    return null;
end;
$$;

-------------------------------------------
--Trigger per eliminare l'altra sottocorsa dopo che ne è stata eliminata una, poichè non può esistere una senza l'altra. 
create trigger eliminaaltrasottocorsa
    after delete
    on corsaregolare
    for each row
    --quando la corsa eliminata è una sottocorsa
    when (old.corsasup IS NOT NULL)
execute procedure eliminasottocorse();

create function eliminasottocorse() returns trigger
    language plpgsql
as
$$
declare
    v_altra_corsa navigazione.corsaregolare.idcorsa%type;
begin
    if old.corsasup is not null then
        --seleziona la sottocorsa rimanente
        select idcorsa into v_altra_corsa
        from navigazione.corsaregolare
        where corsasup = old.corsasup;

        if v_altra_corsa is not null then
            --e la cancella
            delete from navigazione.corsaregolare
            where idcorsa = v_altra_corsa;
        end if;
        
        --elimina la tupla in scalo che aveva generato le due sottocorse
        delete from navigazione.scalo
        where idcorsa = old.corsasup;
    end if;
    return null;
end;
$$;

------------------------------------
-- trigger per la generazione delle corse specifiche in tutte le date presenti nel periodo di attivazione
create trigger generacorse
    after insert
    on attivain
    for each row
execute procedure generacorsespecifiche();

create function generacorsespecifiche() returns trigger
    language plpgsql
as
$$
declare
    v_data_inizio date;
    v_data_corrente date;
    v_data_fine date;
    v_giorni bit(7);
    v_natante navigazione.natante.nome%type;
    v_cap_pass integer;
    v_cap_veic integer;
    v_day integer;
    v_offset integer;
begin
    --seleziona le date di inizio e fine e i giorni del periodo
    select dataInizio, dataFine, giorni into v_data_inizio, v_data_fine, v_giorni
    from navigazione.periodo
    where idperiodo = new.idperiodo;

    --seleziona il natante che verrà utilizzato
    select natante into v_natante
    from navigazione.corsaRegolare
    where idcorsa = new.idcorsa;

    --seleziona la capienza passeggeri e veicoli del natante
    select capienzaPasseggeri, capienzaVeicoli into v_cap_pass, v_cap_veic
    from navigazione.natante
    where nome = v_natante;

    --ricava un numero da 0 a 6 corrispondente al giorno della settimana in cui inizia il periodo
    -- 0 se domenica, 1 se lunedì ... fino a 6 se sabato
    v_day := extract(dow from v_data_inizio::timestamp);
    --per ogni bit della stringa di bit che indica i giorni di attività
    for i in 0..6 loop
        --ogni iterazione del for-loop parte con v_data_corrente uguale alla data di inizio
        v_data_corrente := v_data_inizio;
        --se l'i-esimo bit è 1
        if get_bit(v_giorni, i) = 1 then
            v_offset := (i - v_day);
            if v_offset < 0 then
                while v_offset < 0 loop
                    v_offset := v_offset + 7;
                end loop;
            else
                v_offset := v_offset % 7;
            end if;
            --aggiunge l'offset alla data corrente affinchè la data corrente
            --si trovi nella prima data maggiore o uguale la data di inizio,
            --che abbia come giorno della settimana l'i-esimo giorno
            v_data_corrente := v_data_corrente + v_offset;
            --per tutte le date con quel giorno incluse nel periodo
            while v_data_corrente <= v_data_fine loop
                --aggiunge la corsa specifica per quella data
                insert into navigazione.corsaSpecifica
                values(new.idcorsa, v_data_corrente, v_cap_pass, v_cap_veic, 0, false);
                --incrementa la data alla settimana successiva
                v_data_corrente := v_data_corrente + 7;
            end loop;
        end if;
    end loop;
    
    return new;
end;
$$;

---------------------------
-- trigger per cancellare le sottocorse figlie (quando viene cancellata una corsa principale), e la
-- sottocorsa sorella (quando viene cancellata una sottocorsa).
create trigger propagacancellazione
    after update
        of cancellata
    on corsaspecifica
    for each row
    when (new.cancellata = true)
execute procedure propagacancellazione();

create function propagacancellazione() returns trigger
    language plpgsql
as
$$
declare
    thisData date := old.data;
    CorsaRegolareRiferita navigazione.corsaregolare%rowtype;
    isCancellata boolean;
    it1 cursor for select *
                   from  navigazione.corsaregolare
                   where corsasup = CorsaRegolareRiferita.idcorsa;
    it2 cursor for select *
                    from navigazione.corsaregolare
                    where corsasup = CorsaRegolareRiferita.corsasup and idcorsa <> CorsaRegolareRiferita.idcorsa;
begin
    --seleziona la corsa regolare della corsa in questione
    select * into CorsaRegolareRiferita from navigazione.corsaregolare where idcorsa = old.idcorsa;
    --se è una corsa principale
    if (CorsaRegolareRiferita.corsasup is null) then --potrebbe avere delle sottocorse
        --per entrambe le sottocorse
        for x in it1 loop
            select cancellata into isCancellata
            from navigazione.corsaspecifica
            where idcorsa = x.idcorsa and data = thisData;
            --se non sono già cancellate le cancella
            if isCancellata = false then
                update navigazione.corsaspecifica
                set cancellata = true
                where idcorsa = x.idcorsa and data = thisData;
            end if;
        end loop;
    else --se invece è una sottocorsa ha sicuramente una sorella
        for x in it2 loop
            --e cancella entrambe
            select cancellata into isCancellata
            from navigazione.corsaspecifica
            where idcorsa = x.idcorsa and data = thisData;
            if isCancellata = false then
                update navigazione.corsaspecifica
                set cancellata = true
                where idcorsa = x.idcorsa and data = thisData;
            end if;
        end loop;
    end if;
    return new;
end;
$$;

-----------------------------------------------------
-----------------------------------------------------

------------------------------
--Procedura per calcolare gli incassi di una corsa regolare in un determinato arco di tempo
create procedure calcolaincassicorsainperiodo(IN thisidcorsa integer, IN ini_periodo date, IN fin_periodo date, OUT incasso double precision)
    language plpgsql
as
$$
begin
    select sum(prezzo) into incasso
    from navigazione.biglietto
    where idcorsa = thisidcorsa and data between ini_periodo and fin_periodo;
end;
$$;

----------------------------
--Procedura per fare in modo che quando venga creato uno scalo in una corsa regolare, per ogni data
--in cui è attiva, le sottocorse create abbiano gli stessi posti disponibili della corsa madre.
create procedure aggiornapostisottocorse(IN idcorsa_in integer)
    language plpgsql
as
$$
declare
    --seleziona le sottocorse
    cur_sottocorse cursor for
        select CR.idcorsa
        from navigazione.corsaregolare as CR
        where CR.corsasup = idCorsa_in;

    --seleziona i dati che vanno aggiornati nelle sottocorse
    cur_postidisp cursor for
        select CS.idcorsa, CS.data, CS.postidisppass, CS.postidispvei, CS.cancellata
        from navigazione.corsaspecifica as CS
        where CS.idcorsa = idCorsa_in;
begin
    for k in cur_postidisp loop
        for i in cur_sottocorse loop
            --aggiorna i valori delle sottocorse
            update navigazione.corsaspecifica
            set postidisppass = k.postidisppass, postidispvei = k.postidispvei, cancellata = k.cancellata
            where idcorsa = i.idcorsa AND data = k.data;
        end loop;
    end loop;
end;
$$;