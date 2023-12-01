--Trigger per aggiornare i posti disponibili per i PASSEGGERI di una corsa per ogni biglietto venduto

/*Eventi che possono causare un cambiamento sono:
    -inserimento di un nuovo biglietto
    -eliminazione di un biglietto
    -il cambiamento da una corsa ad un'altra di un biglietto
    */

--Inserimento di un biglietto
create trigger aggiornaPostiDispPass1
after insert on Biglietto
for each row
begin 
    update Corsa
    set postiDispPass = postiDispPass - 1
    where idCorsa = new.Corsa
end

--Eliminazione di un biglietto
create trigger aggiornaPostiDispPass2
after delete on Biglietto
for each row
begin 
    update Corsa
    set postiDispPass = postiDispPass + 1
    where idCorsa = old.Corsa
end

--Cambio corsa in biglietto
create trigger aggiornaPostiDispPass3
after update of Corsa on Biglietto
for each row
when (old.Corsa <> new.Corsa)
begin 
    update Corsa
    set postiDispPass = postiDispPass + 1
    where idCorsa = old.Corsa

    update Corsa
    set postiDispPass = postiDispPass - 1
    where idCorsa = new.Corsa
end

--Trigger per aggiornare i posti disponibili per i VEICOLI di una corsa per ogni biglietto venduto

/*Eventi che possono causare un cambiamento sono:
    -inserimento di un nuovo biglietto
    -eliminazione di un biglietto
    -il cambiamento da una corsa ad un'altra di un biglietto
    -l'aggiunta di un veicolo in biglietto
    -la rimozione di un veicolo in biglietto
    */

--Trigger per inizializzare i posti disponibili di una corsa