CREATE TABLE analfabetismo (
    granularidade_geografica text,
    localidade text,
    uf text,
    ano bigint,
    valor double precision,
    PRIMARY KEY (granularidade_geografica, ano)
);

CREATE TABLE resultados_enem (
    NU_INSCRICAO bigint PRIMARY KEY,
    NU_ANO bigint,
    SG_UF_PROVA text,
    NU_NOTA_CN double precision,
    NU_NOTA_CH double precision,
    NU_NOTA_LC double precision,
    NU_NOTA_MT double precision,
    NU_NOTA_REDACAO double precision
);

CREATE TABLE medias_enem (
    SG_UF_PROVA text,
    NU_NOTA_CN double precision,
    NU_NOTA_CH double precision,
    NU_NOTA_LC double precision,
    NU_NOTA_MT double precision,
    NU_NOTA_REDACAO double precision,
    NU_ANO integer,
    PRIMARY KEY (SG_UF_PROVA, NU_ANO)
);