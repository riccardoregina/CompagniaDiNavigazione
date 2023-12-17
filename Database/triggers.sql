-- trigger per la generazione delle corse specifiche

create or replace function navigazione.generacorsespecifiche() returns trigger
    language plpgsql
as
$$
declare
    startdate date;
    enddate date;
    thisPeriodo navigazione.periodo%rowtype;
    capienzap navigazione.natante.capienzapasseggeri%type;
    capienzav navigazione.natante.capienzaveicoli%type;
    query text;
    day integer;
begin

    -- inizializzo start e end
    select * into thisPeriodo
    from navigazione.periodo
    where idperiodo = new.idperiodo;

    -- prendo le informazioni di corsaregolare
    select capienzapasseggeri, capienzaveicoli into capienzap, capienzav
    from navigazione.corsaregolare join navigazione.natante on natante = nome
    where idcorsa = new.idcorsa;

    query := 'insert into navigazione.corsaspecifica' ||
             'values (new.idCorsa, ?, capienzap, capienzav, 0, 0)';
    loop
        startdate := startdate + 1; --vado alla data successiva
        day := EXTRACT(DOW FROM startdate::DATE); -- restituisce 0 se domenica, 1 se lunedi etc.
        -- shiftare a dx di i e confrontare con 1 restituisce il valore del bit in posizione i
        if ((thisPeriodo.giorni >> day) & 1) then
            execute query using startdate;
        end if;
        exit when startdate = enddate;
    end loop;
end;
$$

create trigger triggergeneracorsespecifiche
    after insert
    on navigazione.attivain
    for each row
execute function navigazione.generacorsespecifiche();

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