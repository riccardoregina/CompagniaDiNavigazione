create schema Navigazione;

create domain money as numeric
	check (value >= 0);

create domain age as integer
	check (value >= 0);

create domain tipoNatante as text
	check (value like 'traghetto' or value like 'motonave' or value like 'aliscafo' or value like 'altro');

create domain tipoVeicolo as text
	check (value like 'automobile' or value like 'motociclo' or value like 'mezzo pesante' or value like 'altro');

create table Compagnia(
    login text primary key,
    password text not null,
    nome text not null,
    sitoWeb text not null
);

create table Corsa(
    idCorsa serial primary key,
    PortoPartenza integer not null,
    PortoArrivo integer not null,
    DataOraPartenza timestamp not null,
    DataOraArrivo timestamp not null,
    data date not null,
    costoIntero money not null,
    costoRidotto money not null,
    minutiRitardo integer,
    cancellata boolean,
    postiDispPass integer not null,
    postiDispVei integer,
    Compagnia text not null,
    Natante text not null,

    check (costoIntero > costoRidotto AND PortoArrivo <> PortoPartenza AND orarioPartenza < orarioArrivo)
);

create table Periodo(
    idPeriodo serial primary key,
    dataInizio date not null,
    dataFine date not null,
    giorni bit(7) not null,

    check (dataInizio < dataFine)
);

create table Porto(
    idPorto serial primary key,
    comune text not null,
    indirizzo text not null,
    numeroTelefono text not null
);

create table Scalo(
    Corsa integer primary key,
    Porto integer not null,
    DataOraAttracco timestamp not null,
    DataOraRipartenza timestamp not null,

    check(orarioAttracco < orarioRipartenza)
);

create table Natante(
    nome text primary key,
    capienzaPasseggeri integer not null,
    capienzaVeicoli integer not null,
    tipo tipoNatante not null
);

create table Cliente(
    login text primary key,
    password text not null,
    nome text not null,
    cognome text not null
);

create table Biglietto(
    idBiglietto serial primary key,
    Corsa integer not null,
    Cliente text not null,
    Veicolo text not null,
    prevendita boolean not null,
    bagaglio boolean not null,
    prezzo money not null,
    dataAcquisto date not null,
    etaPasseggero age not null
);

create table Veicolo(
    targa text primary key,
    tipo tipoVeicolo not null,
    Proprietario text not null
);

create table AccountSocial(
    nomeSocial text,
    tag text,
    Proprietario text not null,

    primary key(nomeSocial, tag)
);

create table Email(
    indirizzo text not null,
    Compagnia text not null
);

create table Telefono(
    numero text primary key,
    Compagnia text not null
);

create table AttivaIn (
    idCorsa integer,
    idPeriodo integer,

    primary key(idCorsa, idPeriodo),
    foreign key(idCorsa) references Corsa(idCorsa),
    foreign key(idPeriodo) references Corsa(idPeriodo)
);

/*alter Corsa*/
alter table Corsa
    add constraint corsaFKcompagnia foreign key Compagnia references Compagnia(login);

alter table Corsa
    add constraint corsaFKnatante foreign key Natante references Natante(nome);

alter table Corsa
    add constraint corsaFKportoPartenza foreign key PortoPartenza references Porto(idPorto);

alter table Corsa
    add constraint corsaFKportoArrivo foreign key PortoArrivo references Porto(idPorto);

/*alter Scalo*/
alter table Scalo
    add constraint scaloFKcorsa foreign key Corsa references Corsa(idCorsa);

alter table Scalo
    add constraint scaloFKporto foreign key Porto references Porto(idPorto);

/*alter Natante*/
alter table Natante
    add constraint natanteFKcompagnia foreign key Compagnia references Compagnia(idCompagnia);

/*alter Biglietto*/
alter table Biglietto
    add constraint bigliettoFKcorsa foreign key Corsa references Corsa(idCorsa);

alter table Biglietto
    add constraint bigliettoFKcliente foreign key Cliente references Cliente(login);

alter table Biglietto
    add constraint bigliettoFKveicolo foreign key Veicolo references Veicolo(targa);

/*alter Veicolo*/
alter table Veicolo
    add constraint veicoloFKproprietario foreign key Proprietario references Cliente(idCliente);

/*alter AccountSocial*/
alter table AccountSocial
    add constraint accountFKcompagnia foreign key Compagnia references Compagnia(login);

/*alter Email*/
alter table Email
    add constraint emailFKcompagnia foreign key Compagnia references Compagnia(login);

/*alter Telefono*/
alter table Telefono
    add constraint telefonoFKcompagnia foreign key Compagnia references Compagnia(login);
