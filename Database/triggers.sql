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
    if v_corsa_sup is null then --il biglietto è acquistato per una corsa principale quindi aggiorno i posti anche per le sottocorse
        update navigazione.corsaspecifica
        set postidisppass = postidisppass - 1
        where data = new.data and idcorsa in (select CR.idcorsa
                                              from navigazione.corsaregolare as CR
                                              where CR.corsasup = new.idcorsa or CR.idcorsa = new.idcorsa);
    else -- il biglietto è acquistato per una sottotratta quindi aggiorno i posti anche per la corsa principale
        update navigazione.corsaspecifica
        set postidisppass = postidisppass - 1
        where idcorsa = new.idCorsa and data = new.data;
        
        update navigazione.corsaspecifica
        set postidisppass = postidisppass - 1
        where idcorsa = v_corsa_sup and data = new.data;
    end if;
    
    return null;
end;
$$;

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

    if v_corsa_sup is null then --il biglietto è acquistato per una corsa principale quindi aggiorno i posti anche per le sottocorse
        update navigazione.corsaspecifica
        set postidispvei = postidispvei - 1
        where data = new.data and idcorsa in (select CR.idcorsa
                                              from navigazione.corsaregolare as CR
                                              where CR.corsasup = new.idcorsa or CR.idcorsa = new.idcorsa);
    else -- il biglietto è acquistato per una sottotratta quindi aggiorno i posti anche per la corsa principale
        update navigazione.corsaspecifica
        set postidispvei = postidispvei - 1
        where idcorsa = new.idCorsa and data = new.data;

        update navigazione.corsaspecifica
        set postidispvei = postidispvei - 1
        where idcorsa = v_corsa_sup and data = new.data;
    end if;

    return null;
end;
$$;

create function aggiungicorsescalo() returns trigger
    language plpgsql
as
$$
declare
    v_record navigazione.corsaregolare%rowtype;
    v_ultimo_idcorsa navigazione.corsaregolare.idcorsa%type;
    cur_periodi cursor for
        select idperiodo
        from navigazione.attivain
        where idcorsa = new.idcorsa;
begin
    select * into v_record
    from navigazione.corsaregolare
    where idcorsa = new.idcorsa;

    insert into navigazione.corsaregolare(portopartenza, portoarrivo, orariopartenza, orarioarrivo,
                                          costointero, scontoridotto, costobagaglio, costoprevendita,
                                          costoveicolo, compagnia, natante, corsasup)
    values
        (v_record.portopartenza, new.porto, v_record.orariopartenza, new.orarioAttracco, v_record.costointero,
         v_record.scontoridotto, v_record.costobagaglio, v_record.costoprevendita, v_record.costoveicolo,
         v_record.compagnia, v_record.natante, new.idcorsa);

    select idcorsa into v_ultimo_idcorsa
    from navigazione.corsaregolare
    order by idcorsa desc
    limit 1;

    for p in cur_periodi loop
        insert into navigazione.attivain
        values (v_ultimo_idcorsa, p.idperiodo);
    end loop;

    insert into navigazione.corsaregolare(portopartenza, portoarrivo, orariopartenza, orarioarrivo,
                                          costointero, scontoridotto, costobagaglio, costoprevendita,
                                          costoveicolo, compagnia, natante, corsasup)
    values
        (new.porto, v_record.portoarrivo, new.orarioRipartenza, v_record.orarioarrivo, v_record.costointero,
         v_record.scontoridotto, v_record.costobagaglio, v_record.costoprevendita, v_record.costoveicolo,
         v_record.compagnia, v_record.natante, new.idcorsa);

    select idcorsa into v_ultimo_idcorsa
    from navigazione.corsaregolare
    order by idcorsa desc
    limit 1;

    for p in cur_periodi loop
            insert into navigazione.attivain
            values (v_ultimo_idcorsa, p.idperiodo);
    end loop;
    
    return new;
end;
$$;

create function attivasottocorse() returns trigger
    language plpgsql
as
$$
declare
    cur_sottocorse cursor for
        select idcorsa
        from navigazione.corsaregolare
        where corsasup = new.idcorsa;
begin
    for t in cur_sottocorse loop
        insert into navigazione.attivain
        values (t.idcorsa, new.idperiodo);
    end loop;

    return new;
end;
$$;

create function cambiaorarioarrivoinsottocorsa() returns trigger
    language plpgsql
as
$$
declare
    sottocorsa navigazione.corsaregolare%rowtype;

begin
    select * into sottocorsa
    from navigazione.corsaregolare
    where corsasup = old.idcorsa and orarioarrivo = old.orarioarrivo;

    update navigazione.corsaregolare
    set orarioarrivo = new.orarioarrivo
    where idcorsa = sottocorsa.idcorsa;

    return new;
end;
$$;

create function cambiaorariopartenzainsottocorsa() returns trigger
    language plpgsql
as
$$
declare
    sottocorsa navigazione.corsaregolare%rowtype;

begin
    select * into sottocorsa
    from navigazione.corsaregolare
    where corsasup = old.idcorsa and orariopartenza = old.orariopartenza;

    update navigazione.corsaregolare
    set orariopartenza = new.orariopartenza
    where idcorsa = sottocorsa.idcorsa;

    return new;
end;
$$;

create function cancellacorseinperiodo() returns trigger
    language plpgsql
as
$$
declare
    v_data_inizio navigazione.periodo.datainizio%type;
    v_data_fine navigazione.periodo.dataFine%type;
    --trovo i periodi che non sono attaccati a nessuna corsa
    cur_periodi cursor for
        select P.idperiodo
        from navigazione.periodo as P
        where P.idperiodo not in (select idperiodo 
                                  from navigazione.attivain);

begin
    select datainizio, datafine into v_data_inizio, v_data_fine
    from navigazione.periodo
    where idperiodo = old.idperiodo;

    delete from navigazione.corsaspecifica
    where idcorsa = old.idcorsa AND data between v_data_inizio and v_data_fine;

    for p in cur_periodi loop
        delete from navigazione.periodo
        where idperiodo = p.idperiodo;
    end loop;
    
    return null;
end;
$$;

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

create function eliminasottocorse() returns trigger
    language plpgsql
as
$$
declare
    v_altra_corsa navigazione.corsaregolare.idcorsa%type;
begin
    if old.corsasup is not null then
        select idcorsa into v_altra_corsa
        from navigazione.corsaregolare
        where corsasup = old.corsasup;

        if v_altra_corsa is not null then
            delete from navigazione.corsaregolare
            where idcorsa = v_altra_corsa;
        end if;
        
        delete from navigazione.scalo
        where idcorsa = old.corsasup;
    end if;
    return null;
end;
$$;

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
    select dataInizio, dataFine, giorni into v_data_inizio, v_data_fine, v_giorni
    from navigazione.periodo
    where idperiodo = new.idperiodo;

    select natante into v_natante
    from navigazione.corsaRegolare
    where idcorsa = new.idcorsa;

    select capienzaPasseggeri, capienzaVeicoli into v_cap_pass, v_cap_veic
    from navigazione.natante
    where nome = v_natante;

    v_day := extract(dow from v_data_inizio::timestamp);
    --raise notice 'v_day = %', v_day;
    for i in 0..6 loop
        v_data_corrente := v_data_inizio;
        if get_bit(v_giorni, i) = 1 then
            v_offset := (i - v_day) % 7;
            v_data_corrente := v_data_corrente + v_offset;
            while v_data_corrente <= v_data_fine loop
                insert into navigazione.corsaSpecifica
                values(new.idcorsa, v_data_corrente, v_cap_pass, v_cap_veic, 0, false);
                v_data_corrente := v_data_corrente + 7;
            end loop;
        end if;
    end loop;
    
    return new;
end;
$$;

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
    select * into CorsaRegolareRiferita from navigazione.corsaregolare where idcorsa = old.idcorsa;
    if (CorsaRegolareRiferita.corsasup is null) then --potrebbe avere delle sottocorse
        for x in it1 loop
            select cancellata into isCancellata
            from navigazione.corsaspecifica
            where idcorsa = x.idcorsa and data = thisData;
            if isCancellata = false then
                update navigazione.corsaspecifica
                set cancellata = true
                where idcorsa = x.idcorsa and data = thisData;
            end if;
        end loop;
    else --ha sicuramente una sorella
        for x in it2 loop
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



