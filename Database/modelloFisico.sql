create schema Navigazione;

create type tipoNatante as enum ('traghetto', 'motonave', 'aliscafo', 'altro');

create type tipoVeicolo as enum ('automobile', 'motociclo', 'mezzo pesante', 'altro');

create table Navigazione.Compagnia(
    login text primary key,
    password text not null,
    nome text not null,
    sitoWeb text not null
);

create table Navigazione.Corsa(
    idCorsa serial primary key,
    PortoPartenza integer not null,
    PortoArrivo integer not null,
    dataOraPartenza timestamp not null,
    dataOraArrivo timestamp not null,
    costoIntero numeric not null check(costoIntero >= 0),
    costoRidotto numeric not null check(costoRidotto >= 0),
    minutiRitardo integer not null default 0,
    cancellata boolean not null default 'false',
    postiDispPass integer not null check(postiDispPass >= 0),
    postiDispVei integer check(postiDispVei >= 0 or postiDispVei is null),
    Compagnia text not null,
    Natante text not null,
    incrementoBagaglio numeric default 0 check(incrementoBagaglio >= 0),
    incrementoPrevendita numeric default 0 check(incrementoPrevendita >= 0),
    incrementoVeicolo numeric default 0 check(incrementoVeicolo >= 0),

    check (costoIntero > costoRidotto AND PortoArrivo <> PortoPartenza AND dataOraPartenza < dataOraArrivo)
);

create table Navigazione.Periodo(
    idPeriodo serial primary key,
    dataInizio date not null,
    dataFine date not null,
    giorni bit(7) not null,

    check (dataInizio < dataFine)
);

create table Navigazione.Porto(
    idPorto serial primary key,
    comune text not null,
    indirizzo text not null,
    numeroTelefono text not null
);

create table Navigazione.Scalo(
    Corsa integer primary key,
    Porto integer not null,
    dataOraAttracco time not null,
    dataOraRipartenza time not null,

    check(dataOraAttracco < dataOraRipartenza)
);

create table Navigazione.Natante(
    nome text primary key,
    Compagnia text not null,
    capienzaPasseggeri integer not null,
    capienzaVeicoli integer,
    tipo navigazione.tipoNatante not null default 'altro'
);

create table Navigazione.Cliente(
    login text primary key,
    password text not null,
    nome text not null,
    cognome text not null
);

create table Navigazione.Biglietto(
    idBiglietto serial primary key,
    Corsa integer not null,
    Cliente text not null,
    Veicolo text,
    prevendita boolean not null default 'false',
    bagaglio boolean not null default 'false',
    prezzo numeric not null check(prezzo >= 0),
    dataAcquisto date not null,
    etaPasseggero integer not null check (etaPasseggero >= 0)
);

create table Navigazione.Veicolo(
    targa text primary key,
    tipo Navigazione.tipoVeicolo not null default 'altro',
    Proprietario text not null
);

create table Navigazione.AccountSocial(
    nomeSocial text,
    tag text,
    Compagnia text not null,

    primary key(nomeSocial, tag)
);

create table Navigazione.Email(
    indirizzo text primary key,
    Compagnia text not null
);

create table Navigazione.Telefono(
    numero text primary key,
    Compagnia text not null
);

create table Navigazione.AttivaIn (
    idCorsa integer,
    idPeriodo integer,

    primary key(idCorsa, idPeriodo),
    foreign key(idCorsa) references Corsa(idCorsa),
    foreign key(idPeriodo) references Corsa(idPeriodo)
);

/*alter Corsa*/
alter table Corsa
    add constraint corsaFKcompagnia 
        foreign key (Compagnia) references Compagnia(login)
            on delete cascade       on update cascade;

alter table Corsa
    add constraint corsaFKnatante
        foreign key (Natante) references Natante(nome)
            on delete cascade   on update cascade;

alter table Corsa
    add constraint corsaFKportoPartenza
        foreign key (PortoPartenza) references Porto(idPorto)
            on delete cascade       on update cascade;

alter table Corsa
    add constraint corsaFKportoArrivo
        foreign key (PortoArrivo) references Porto(idPorto)
            on delete cascade       on update cascade;

/*alter Scalo*/
alter table Scalo
    add constraint scaloFKcorsa
        foreign key (Corsa) references Corsa(idCorsa)
            on delete cascade       on update cascade;

alter table Scalo
    add constraint scaloFKporto
        foreign key (Porto) references Porto(idPorto)
            on delete cascade       on update cascade;

/*alter Natante*/
alter table Natante
    add constraint natanteFKcompagnia
        foreign key (Compagnia) references Compagnia(login)
            on delete cascade       on update cascade;

/*alter Biglietto*/
alter table Biglietto
    add constraint bigliettoFKcorsa
        foreign key (Corsa) references Corsa(idCorsa)
            on delete cascade       on update cascade;

alter table Biglietto
    add constraint bigliettoFKcliente
        foreign key (Cliente) references Cliente(login)
            on delete cascade       on update cascade;

alter table Biglietto
    add constraint bigliettoFKveicolo
        foreign key (Veicolo) references Veicolo(targa)
            on delete set null      on update cascade;

/*alter Veicolo*/
alter table Veicolo
    add constraint veicoloFKproprietario
        foreign key (Proprietario) references Cliente(login)
            on delete cascade       on update cascade;

/*alter AccountSocial*/
alter table AccountSocial
    add constraint accountFKcompagnia
        foreign key (Compagnia) references Compagnia(login)
            on delete cascade       on update cascade;

/*alter Email*/
alter table Email
    add constraint emailFKcompagnia
        foreign key (Compagnia) references Compagnia(login)
            on delete cascade       on update cascade;

/*alter Telefono*/
alter table Telefono
    add constraint telefonoFKcompagnia
        foreign key (Compagnia) references Compagnia(login)
            on delete cascade       on update cascade;
