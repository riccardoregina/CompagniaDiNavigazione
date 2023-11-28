create domain money as float
	check (value >= 0);

create domain age as integer
	check (value >= 0);

create domain tipoNatante as varchar(20)
	check (value like 'traghetto' or value like 'motonave' or value like 'aliscafo' or value like 'altro');

create domain tipoVeicolo as varchar(20)
	check (value like 'automobile' or value like 'motociclo' or value like 'mezzo pesante' or value like 'altro');

create table Compagnia(
    login varchar(20) primary key,
    password varchar(20) not null,
    nome varchar(20) not null,
    sitoWeb varchar(50) not null
);

create table Corsa(
    idCorsa serial primary key,
    PortoPartenza integer not null,
    PortoArrivo integer not null,
    orarioPartenza time not null,
    orarioArrivo time not null,
    costoIntero money not null,
    costoRidotto money,
    minutiRitardo integer,
    cancellata boolean,
    postiDispPass integer not null,
    postiDispVei integer,
    Compagnia varchar(20) not null,
    Natante varchar(20) not null
);

create table Periodo(
    idPeriodo serial primary key,
    dataInizio date not null,
    dataFine date not null,
    giorni char(7) not null
);

create table Porto(
    idPorto serial primary key,
    comune varchar(20) not null,
    indirizzo varchar(50) not null,
    numeroTelefono varchar(20) not null
);

create table Scalo(
    Corsa integer primary key,
    Porto integer not null,
    orarioAttracco time not null,
    orarioRipartenza time not null
);

create table Natante(
    nome varchar(20) primary key,
    capienzaPasseggeri integer not null,
    capienzaVeicoli integer not null,
    tipo tipoNatante not null
);

create table Cliente(
    login varchar(20) primary key,
    password varchar(20) not null,
    nome varchar(20) not null,
    cognome varchar(20) not null
);

create table Biglietto(
    idBiglietto serial primary key,
    Corsa integer not null,
    Cliente varchar(20) not null,
    Veicolo varchar(20) not null,
    prevendita boolean not null,
    bagaglio boolean not null,
    prezzo money not null,
    dataAcquisto date not null,
    etaPasseggero age not null
);

create table Veicolo(
    targa varchar(20) primary key,
    tipo tipoVeicolo not null,
    Proprietario varchar(20) not null
);

create table AccountSocial(
    nomeSocial varchar(20),
    tag varchar(20),
    Proprietario varchar(20) not null,

    primary key(nomeSocial, tag)
);
/*non so se si possa fare cosi' il vincolo di primary key per piu di un attributo*/

create table Email(
    indirizzo varchar(20) not null,
    Compagnia varchar(20) not null
);

create table Telefono(
    numero varchar(20) primary key,
    Compagnia varchar(20) not null
);

create table AttivaIn (
    idCorsa integer,
    idPeriodo integer,

foreign key(idCorsa) references Corsa(idCorsa),
foreign key(idPeriodo) references Corsa(idPeriodo),

primary key(idCorsa, idPeriodo)
);

/*alter Corsa*/

alter table Corsa
    add constraint corsaFKcompagnia foreign key(Compagnia) references Compagnia(login);

alter table Corsa
    add constraint corsaFKnatante foreign key(Natante) references Natante(nome);

alter table Corsa
    add constraint corsaFKportoPartenza foreign key(PortoPartenza) references Porto(idPorto);

alter table Corsa
    add constraint corsaFKportoArrivo foreign key(PortoArrivo) references Porto(idPorto);

/*alter Scalo*/
alter table Scalo
    add constraint scaloFKcorsa foreign key(Corsa) references Corsa(idCorsa);

alter table Scalo
    add constraint scaloFKporto foreign key(Porto) references Porto(idPorto);

/*alter Natante*/
alter table Natante
    add constraint natanteFKcompagnia foreign key(Compagnia) references Compagnia(idCompagnia);

/*alter Biglietto*/
alter table Biglietto
    add constraint bigliettoFKcorsa foreign key(Corsa) references Corsa(idCorsa);

alter table Biglietto
    add constraint bigliettoFKcliente foreign key(Cliente) references Cliente(login);

alter table Biglietto
    add constraint bigliettoFKveicolo foreign key(Veicolo) references Veicolo(targa);

/*alter Veicolo*/
alter table Veicolo
    add constraint veicoloFKproprietario foreign key(Proprietario) references Cliente(idCliente);

/*alter AccountSocial*/
alter table AccountSocial
    add constraint accountFKcompagnia foreign key(Compagnia) references Compagnia(login);

/*alter Email*/
alter table Email
    add constraint emailFKcompagnia foreign key(Compagnia) references Compagnia(login);

/*alter Telefono*/
alter table Telefono
    add constraint telefonoFKcompagnia foreign key(Compagnia) references Compagnia(login);