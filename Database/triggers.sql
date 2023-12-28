-- trigger per la generazione delle corse specifiche
create function navigazione.generacorsespecifiche() returns trigger
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

create trigger triggergeneracorsespecifiche
    after insert
    on navigazione.attivain
    for each row
execute function navigazione.generacorsespecifiche();

--trigger per cancellare le corse specifiche di una corsa regolare non più attiva in un periodo
create function cancellacorseinperiodo() returns trigger
    language plpgsql
as
$$
declare
    v_data_inizio navigazione.periodo.datainizio%type;
    v_data_fine navigazione.periodo.dataFine%type;
begin
    select datainizio, datafine into v_data_inizio, v_data_fine
    from navigazione.periodo
    where idperiodo = old.idperiodo;

    delete from navigazione.corsaspecifica
    where idcorsa = old.idcorsa AND data between v_data_inizio and v_data_fine;
end;
$$;

create trigger cancellacorse
    after delete
    on navigazione.attivain
    for each row
execute procedure navigazione.cancellacorseinperiodo();

-- trigger per aggiornare i posti (per passeggeri) disponibili per una corsa specifica
create function navigazione.aggiornapostipasseggero() returns trigger
    language plpgsql
as
$$
begin
    update navigazione.corsaspecifica
    set postidisppass = postidisppass - 1
    where idcorsa = new.idCorsa and data = new.data;
end;
$$;

create trigger triggeraggiornapostipasseggero
    after insert
    on navigazione.biglietto
    for each row
execute function navigazione.aggiornapostipasseggero();

-- trigger per aggiornare i posti (per veicoli) disponibili per una corsa specifica

create function navigazione.aggiornapostiveicolo() returns trigger
    language plpgsql
as
$$
begin
    update navigazione.corsaspecifica
    set postidispvei = postidispvei - 1
    where idcorsa = new.idCorsa and data = new.data;
end;
$$;

create or replace trigger triggeraggiornapostiveicolo
    after insert
    on navigazione.biglietto
    for each row
    when (new.veicolo is not null)
execute function navigazione.aggiornapostiveicolo();