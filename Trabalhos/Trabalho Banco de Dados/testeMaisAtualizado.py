import psycopg2
import psycopg2.extensions
from psycopg2 import Error, OperationalError
import sys
import requests
import matplotlib.pyplot as plt
import pandas as pd
from textwrap import wrap

# Variáveis
# Valores para criação de tabelas do Banco de Dados
tables = {'departamento': (
    """CREATE TABLE "departamento" (
      "id_departamento" integer PRIMARY KEY NOT NULL,
      "nome" varchar(255) NOT NULL
    )"""),
    'documento': (
        """CREATE TABLE "documento" (
            "id_documento" integer PRIMARY KEY NOT NULL,
            "caminho_arquivo" varchar(255) NOT NULL,
            "formato" varchar(100) NOT NULL,
            "descricao" varchar(255),
            "data_upload" timestamp NOT NULL
        )"""),
    'disciplina': (
        """CREATE TABLE "disciplina" (
        "id_disciplina" integer PRIMARY KEY NOT NULL,
        "nome" varchar(255) NOT NULL,
        "codigo" integer NOT NULL,
        "carga_horaria" varchar(25) NOT NULL,
        "fk_departamento_id_departamento_" integer NOT NULL,
        FOREIGN KEY("fk_departamento_id_departamento_") REFERENCES "departamento" ("id_departamento") ON DELETE CASCADE
        )"""),
    'usuario': (
        """CREATE TABLE "usuario" (
        "id_usuario" integer PRIMARY KEY NOT NULL,
        "nome" varchar(255) NOT NULL,
        "email" varchar(255) NOT NULL,
        "login" varchar(255) NOT NULL,
        "senha" varchar(50) NOT NULL,
        "area_atuacao" varchar(75),
        "matricula" integer,
        "ano_ingresso" integer,
        "especialidade" varchar(50),
        "fk_departamento_id_departamento" integer NOT NULL,
        FOREIGN KEY("fk_departamento_id_departamento") REFERENCES "departamento" ("id_departamento") ON DELETE CASCADE,
        UNIQUE("email"),
        UNIQUE("login")
        )"""),
    'projeto': (
        """CREATE TABLE "projeto" (
        "id_projeto" integer PRIMARY KEY NOT NULL,
        "titulo" varchar(255) NOT NULL,
        "data_inicio" timestamp NOT NULL,
        "descricao" varchar(500),
        "status" varchar(50) NOT NULL,
        "fk_documento_id_documento" integer,
        "fk_disciplina_id_disciplina" integer,
        FOREIGN KEY("fk_documento_id_documento") REFERENCES "documento" ("id_documento") ON DELETE RESTRICT,
        FOREIGN KEY("fk_disciplina_id_disciplina") REFERENCES "disciplina" ("id_disciplina") ON DELETE RESTRICT,
        CHECK ("status" in ('Planejamento','Em Andamento','Concluído','Cancelado'))
        )"""),
    'equipamento': (
        """CREATE TABLE "equipamento" (
        "id_equipamento" integer PRIMARY KEY NOT NULL,
        "nome" varchar(255) NOT NULL,
        "especificacoes" varchar(255),
        "status" varchar(50) NOT NULL,
        CHECK ("status" in ('Disponível','Em Uso','Manutenção','Indisponível'))
        )"""),
    'Tarefa': (
        """CREATE TABLE "tarefa" (
        "id_tarefa" integer PRIMARY KEY NOT NULL,
        "descricao" varchar(255) NOT NULL,
        "prazo" timestamp NOT NULL,
        "status" varchar(50) NOT NULL,
        "fk_projeto_id_projeto" integer NOT NULL,
        FOREIGN KEY("fk_projeto_id_projeto") REFERENCES "projeto" ("id_projeto") ON DELETE RESTRICT,
        CHECK ("status" in ('Pendente','Em Andamento','Concluída','Atrasada'))
        )"""),
    'Relatorio': (
        """CREATE TABLE "relatorio" (
        "id_relatorio" integer PRIMARY KEY NOT NULL,
        "titulo" varchar(255) NOT NULL,
        "conteudo" text NOT NULL,
        "data_geracao" timestamp NOT NULL,
        "fk_projeto_id_projeto" integer NOT NULL,
        FOREIGN KEY("fk_projeto_id_projeto") REFERENCES "projeto" ("id_projeto") ON DELETE CASCADE
        )"""),
    'Reuniao': (
        """CREATE TABLE "reuniao" (
        "id_reuniao" integer PRIMARY KEY NOT NULL,
        "data_hora" timestamp NOT NULL,
        "local" varchar(255) NOT NULL,
        "pauta" varchar(255) NOT NULL,
        "fk_projeto_id_projeto" integer NOT NULL,
        FOREIGN KEY("fk_projeto_id_projeto") REFERENCES "projeto" ("id_projeto") ON DELETE CASCADE
        )"""),
    'Avaliacao': (
        """CREATE TABLE "avaliacao" (
        "id_avaliacao" integer PRIMARY KEY NOT NULL,
        "tipo" varchar(30) NOT NULL,
        "nota" numeric(4,2) NOT NULL,
        "feedback" varchar(255),
        "fk_projeto_id_projeto" integer NOT NULL,
        FOREIGN KEY("fk_projeto_id_projeto") REFERENCES "projeto" ("id_projeto") ON DELETE CASCADE,
        CHECK ("tipo" in ('Parcial','Final','Apresentação')),
        CHECK ("nota" between 0 and 10)
        )"""),
    'proj_usu': (
        """CREATE TABLE "proj_usu" (
        "fk_projeto_id_projeto" integer NOT NULL,
        "fk_usuario_id_usuario" integer NOT NULL,
        PRIMARY KEY("fk_projeto_id_projeto","fk_usuario_id_usuario"),
        FOREIGN KEY("fk_projeto_id_projeto") REFERENCES "projeto" ("id_projeto") ON DELETE RESTRICT,
        FOREIGN KEY("fk_usuario_id_usuario") REFERENCES "usuario" ("id_usuario") ON DELETE SET NULL
        )"""),
    'proj_equip': (
        """CREATE TABLE "proj_equip" (
        "fk_equipamento_id_equipamento" integer NOT NULL,
        "fk_projeto_id_projeto" integer NOT NULL,
        PRIMARY KEY("fk_equipamento_id_equipamento","fk_projeto_id_projeto"),
        FOREIGN KEY("fk_equipamento_id_equipamento") REFERENCES "equipamento" ("id_equipamento") ON DELETE RESTRICT,
        FOREIGN KEY("fk_projeto_id_projeto") REFERENCES "projeto" ("id_projeto") ON DELETE SET NULL
        )"""),
}

# Valores para serem inseridos no Banco de Dados
inserts = {'departamento': (
    """insert into departamento (id_departamento, nome) values 
        (1, 'Ciência da Computação'),
        (2, 'Engenharia Elétrica'),
        (3, 'Matemática'),
        (4, 'Física'),
        (5, 'Engenharia Civil')"""),
    'documento': (
        """insert into documento (id_documento, caminho_arquivo, formato, descricao, data_upload) values
        (1, '/docs/projeto1_proposta.pdf', 'PDF', 'Proposta inicial do projeto de IA', '2024-01-15 10:30:00'),
        (2, '/docs/relatorio_parcial.docx', 'DOCX', 'Relatório parcial de desenvolvimento', '2024-02-20 14:15:00'),
        (3, '/docs/dataset_analysis.xlsx', 'XLSX', 'Análise estatística do dataset', '2024-03-10 09:45:00'),
        (4, '/docs/apresentacao_final.pptx', 'PPTX', 'Apresentação final do projeto', '2024-04-05 16:20:00'),
        (5, '/docs/codigo_fonte.zip', 'ZIP', 'Código fonte completo do sistema', '2024-04-10 11:30:00')"""),
    'disciplina': (
        """insert into disciplina (id_disciplina, nome, codigo, carga_horaria, fk_departamento_id_departamento_) values
        (101, 'Inteligência Artificial', 2001, '60h', 1),
        (102, 'Banco de Dados', 2002, '80h', 1),
        (103, 'Sistemas Embarcados', 3001, '60h', 2),
        (104, 'Processamento de Sinais', 3002, '80h', 2),
        (105, 'Cálculo Numérico', 4001, '60h', 3),
        (106, 'Física Experimental', 5001, '40h', 4),
        (107, 'Estruturas de Concreto', 6001, '80h', 5)"""),
    'usuario': (
        """insert into usuario (id_usuario, nome, email, login, senha, area_atuacao, matricula, ano_ingresso, especialidade, fk_departamento_id_departamento) values
        (1, 'Dr. João Silva', 'joao.silva@universidade.edu.br', 'jsilva', 'T7#kP9mX$2zL', 'Machine Learning', null, null, 'Inteligência Artificial', 1),
        (2, 'Ana Santos', 'ana.santos@estudante.edu.br', 'asantos', 'N5@vQ8rY!1wS', null, 202201001, 2022, null, 1),
        (3, 'Dr. Maria Oliveira', 'maria.oliveira@universidade.edu.br', 'moliveira', 'B4%pF6hZ*3tK', 'Sistemas Digitais', null, null, 'Microeletrônica', 2),
        (4, 'Pedro Costa', 'pedro.costa@estudante.edu.br', 'pcosta', 'M9$dR2nL#5jP', null, 202201002, 2022, null, 1),
        (5, 'Lucas Ferreira', 'lucas.ferreira@estudante.edu.br', 'lferreira', 'K6@wE3qT!8vX', null, 202101003, 2021, null, 2),
        (6, 'Dr. Carlos Mendes', 'carlos.mendes@universidade.edu.br', 'cmendes', 'P2#sV7mY$4zH', 'Análise Numérica', null, null, 'Matemática Aplicada', 3),
        (7, 'Julia Rocha', 'julia.rocha@estudante.edu.br', 'jrocha', 'Q9%tL5rK*1nM', null, 202201004, 2022, null, 4),
        (8, 'Dr. Roberto Lima', 'roberto.lima@universidade.edu.br', 'rlima', 'X4$jW8pF#6bN', 'Estruturas', null, null, 'Engenharia Estrutural', 5)"""),
    'projeto': (
        """insert into projeto (id_projeto, titulo, data_inicio, descricao, status, fk_documento_id_documento, fk_disciplina_id_disciplina) values
        (1, 'Sistema de Reconhecimento Facial', '2024-01-15', 'Desenvolvimento de algoritmo para reconhecimento facial usando deep learning', 'Em Andamento', 1, 101),
        (2, 'Banco de Dados Distribuído', '2024-02-01', 'Implementação de sistema de banco de dados distribuído para aplicações web', 'Em Andamento', 2, 102),
        (3, 'IoT para Monitoramento Ambiental', '2024-01-20', 'Sistema de sensores IoT para monitoramento de qualidade do ar', 'Planejamento', 3, 103),
        (4, 'Processamento de Áudio Digital', '2024-03-01', 'Algoritmos para processamento e análise de sinais de áudio', 'Em Andamento', null, 104),
        (5, 'Simulação Numérica de Fluidos', '2024-02-15', 'Modelagem computacional de dinâmica dos fluidos', 'Concluído', 4, 105),
        (6, 'Análise de Materiais Nanoestruturados', '2024-01-10', 'Estudo experimental de propriedades de nanomateriais', 'Em Andamento', null, 106),
        (7, 'Otimização de Estruturas de Concreto', '2024-03-15', 'Métodos de otimização para design de estruturas', 'Planejamento', null, 107)"""),
    'equipamento': (
        """insert into equipamento (id_equipamento, nome, especificacoes, status) values
        (1, 'Servidor GPU Tesla V100', '32GB VRAM, CUDA 12.0', 'Disponível'),
        (2, 'Cluster de Processamento', '64 cores, 256GB RAM', 'Em Uso'),
        (3, 'Kit Arduino Mega', 'Microcontrolador + sensores diversos', 'Disponível'),
        (4, 'Osciloscópio Digital', 'Tektronix MSO64, 1GHz, 4 canais', 'Disponível'),
        (5, 'Estação de Trabalho CAD', 'Workstation Dell Precision, RTX 4080', 'Em Uso'),
        (6, 'Microscópio Eletrônico', 'SEM JEOL JSM-6390LV', 'Manutenção'),
        (7, 'Máquina de Ensaio Universal', 'Instron 5982, 100kN', 'Disponível')"""),
    'Tarefa': (
        """insert into Tarefa (id_tarefa, descricao, prazo, status, fk_projeto_id_projeto) values
        (1, 'Coleta e preparação do dataset de faces', '2024-02-15', 'Concluída', 1),
        (2, 'Implementação da rede neural convolucional', '2024-03-30', 'Em Andamento', 1),
        (3, 'Testes de acurácia do modelo', '2024-04-15', 'Pendente', 1),
        (4, 'Design da arquitetura distribuída', '2024-03-01', 'Concluída', 2),
        (5, 'Implementação do protocolo de sincronização', '2024-04-30', 'Em Andamento', 2),
        (6, 'Seleção e configuração dos sensores', '2024-03-15', 'Pendente', 3),
        (7, 'Desenvolvimento da interface web', '2024-05-01', 'Pendente', 3),
        (8, 'Implementação do filtro digital', '2024-04-20', 'Em Andamento', 4),
        (9, 'Modelagem matemática do problema', '2024-03-30', 'Concluída', 5),
        (10, 'Preparação das amostras', '2024-03-20', 'Em Andamento', 6)"""),
    'Relatorio': (
        """insert into Relatorio (id_relatorio, titulo, conteudo, data_geracao, fk_projeto_id_projeto) values
        (1, 'Relatório Parcial - Reconhecimento Facial', 'Progresso no desenvolvimento do algoritmo de reconhecimento facial. Dataset coletado com 10.000 imagens...', '2024-03-01', 1),
        (2, 'Análise de Performance - BD Distribuído', 'Testes de performance realizados mostram throughput de 5000 transações/segundo...', '2024-03-15', 2),
        (3, 'Relatório Final - Simulação de Fluidos', 'Simulação completa da dinâmica de fluidos em cavidade quadrada. Resultados convergem com literatura...', '2024-04-10', 5)"""),
    'Reuniao': (
        """insert into Reuniao (id_reuniao, data_hora, local, pauta, fk_projeto_id_projeto) values
        (1, '2024-02-20 14:00:00', 'Sala 205 - Bloco A', 'Revisão do progresso e definição de próximas etapas', 1),
        (2, '2024-03-05 10:30:00', 'Laboratório de BD', 'Discussão sobre arquitetura do sistema distribuído', 2),
        (3, '2024-03-18 16:00:00', 'Sala de Reuniões - Pós-graduação', 'Apresentação dos resultados preliminares', 5),
        (4, '2024-04-01 09:00:00', 'Laboratório de Eletrônica', 'Planejamento da implementação dos sensores IoT', 3)"""),
    'Avaliacao': (
        """insert into Avaliacao (id_avaliacao, tipo, nota, feedback, fk_projeto_id_projeto) values
        (1, 'Parcial', 8.5, 'Bom progresso no desenvolvimento. Sugestão: melhorar documentação do código.', 1),
        (2, 'Parcial', 9.0, 'Excelente trabalho na modelagem da arquitetura distribuída.', 2),
        (3, 'Final', 9.5, 'projeto completo e bem executado. Resultados muito satisfatórios.', 5),
        (4, 'Apresentação', 8.0, 'Boa apresentação, mas poderia detalhar mais a metodologia utilizada.', 5)"""),
    'proj_usu': (
        """insert into proj_usu (fk_projeto_id_projeto, fk_usuario_id_usuario) values
        (1, 1),
        (1, 2),
        (2, 1),
        (2, 4),
        (3, 3),
        (3, 5),
        (4, 3),
        (4, 5),
        (5, 6),
        (5, 7),
        (6, 7),
        (7, 8)"""),
    'proj_equip': (
        """insert into proj_equip (fk_equipamento_id_equipamento, fk_projeto_id_projeto) values
        (1, 1),
        (2, 2),
        (3, 3),
        (4, 4),
        (5, 7),
        (6, 6),
        (1, 5),
        (2, 1)""")
}

# Valores para deletar as tabelas
drop = {'proj_equip': (
    "drop table proj_equip"),
    'proj_usu': (
        "drop table proj_usu"),
    'Avaliacao': (
        "drop table Avaliacao"),
    'Reuniao': (
        "drop table Reuniao"),
    'Relatorio': (
        "drop table Relatorio"),
    'Tarefa': (
        "drop table Tarefa"),
    'equipamento': (
        "drop table equipamento"),
    'projeto': (
        "drop table projeto"),
    'usuario': (
        "drop table usuario"),
    'disciplina': (
        "drop table disciplina"),
    'documento': (
        "drop table documento"),
    'departamento': (
        "drop table departamento")
}

# Valores para teste de update
update = {'usuario': (
    """update usuario
        SET email = 'ana.santos.novo@estudante.edu.br',
        area_atuacao = 'Deep Learning'
        where id_usuario = 2"""),
    'projeto': (
        """update projeto
        SET status = 'Concluído'
        where id_projeto = 1"""),
    'Tarefa': (
        """update Tarefa
        SET status = 'Concluída',
        prazo = '2024-04-10 23:59:59'
        where id_tarefa = 3"""),
    'equipamento': (
        """update equipamento
        SET status = 'Disponível'
        where id_equipamento = 6"""),
}

# Valores para teste de delete
delete = {'proj_equip': (
    """delete from proj_equip
        where fk_equipamento_id_equipamento = 6"""),
    'Avaliacao': (
        """delete from Avaliacao
        where tipo = 'Apresentação'"""),
    'Reuniao': (
        """delete from Reuniao
        where data_hora < '2024-03-01'""")
}


# Funções
def connect_projetos_academicos():
    try:
        # Configuração de conexão com tratamento de encoding
        conn = psycopg2.connect(
            host="localhost",
            database="tbfinalbanco",
            user="postgres",
            password="master"    
        )
        
        # Verificação da conexão
        conn.autocommit = True
        cursor = conn.cursor()
        cursor.execute("SELECT current_database();")
        db_name = cursor.fetchone()[0]
        print(f"Conectado ao PostgreSQL - Banco: {db_name}")
        cursor.close()
        conn.autocommit = False
        
        return conn
        
    except OperationalError as e:
        print(f"Falha na conexão com o PostgreSQL: {str(e)}", file=sys.stderr)
        return None
    except Exception as e:
        print(f"Erro inesperado: {str(e)}", file=sys.stderr)
        return None


def drop_all_tables(connect):
    print("\n---DROP DB---")
    # Esvazia o Banco de Dados
    cursor = connect.cursor()
    for drop_name in drop:
        drop_description = drop[drop_name]
        try:
            print("Drop {}: ".format(drop_name), end='')
            cursor.execute(drop_description)
        except Error as err:
            print(err.msg)
        else:
            print("OK")
    connect.commit()
    cursor.close()


def create_all_tables(connect):
    print("\n---CREATE ALL TABLES---")
    cursor = connect.cursor()
    for table_name in tables:
        table_description = tables[table_name]
        try:
            print(f"Criando tabela {table_name}: ", end='')
            cursor.execute(table_description)
        except Error as err:
            if err.pgcode == '42P07':  # Código de erro "duplicate_table"
                print("Tabela já existe.")
            else:
                print(f"Erro ao criar tabela: {err}")
        else:
            print("OK")
    connect.commit()
    cursor.close()


def show_table(connect):
    print("\n---SELECIONAR TABELA---")
    # Consulta de tabelas
    cursor = connect.cursor()
    for table_name in tables:
        print("Nome: {}".format(table_name))
    try:
        name = input(str("\nDigite o nome da tabela que deseja consultar. "))
        select = "select * from " + name
        cursor.execute(select)
    except Error as err:
        print(err.msg)
    else:
        print("TABELA {}".format(name))
        myresult = cursor.fetchall()
        for x in myresult:
            print(x)
    cursor.close()


def update_value(connect):
    print("\n---SELECIONAR TABELA PARA ATUALIZAÇÃO---")
    # Atualização de valores
    cursor = connect.cursor()
    for table_name in tables:
        print("Nome: {}".format(table_name))
    try:
        name = input(str("\nDigite o nome da tabela que deseja atualizar. "))
        for table_name in tables:
            table_description = tables[table_name]
            if table_name == name:
                print("Para criar a tabela: {}, foi utilizado o seguinte código {}".format(table_name,
                                                                                           table_description))
        atributo = input("Digite o atributo a ser alterado: ")
        valor = input("Digite o valor a ser atribuído: ")
        codigo_f = input("Digite a coluna da chave primária: ")
        codigo = input("Digite o valor numérico do campo da chave primária: ")
        query = ['UPDATE ', name, ' SET ', atributo, ' = ', valor, ' WHERE ', codigo_f, '= ', codigo]
        sql = ''.join(query)
        cursor.execute(sql)
    except Error as err:
        print(err.msg)
    else:
        print("Atributo atualizado")
    connect.commit()
    cursor.close()


def insert_test(connect):
    print("\n---INSERT TEST---")
    # Inserção dos valores nas tabelas
    cursor = connect.cursor()
    for insert_name in inserts:
        insert_description = inserts[insert_name]
        try:
            print("Inserindo valores para {}: ".format(insert_name), end='')
            cursor.execute(insert_description)
        except Error as err:
            print(err.msg)
        else:
            print("OK")
    connect.commit()
    cursor.close()


def update_test(connect):
    print("\n---UPDATE TEST---")
    # Teste de atualização
    cursor = connect.cursor()
    for update_name in update:
        update_description = update[update_name]
        try:
            print("Teste de atualização de valores para {}: ".format(update_name), end='')
            cursor.execute(update_description)
        except Error as err:
            print(err.msg)
        else:
            print("OK")
    connect.commit()
    cursor.close()


def delete_test(connect):
    print("\n---DELETE TEST---")
    # Teste de deleção
    cursor = connect.cursor()
    for delete_name in delete:
        delete_description = delete[delete_name]
        try:
            print("Teste de deleção de valores para {}: ".format(delete_name), end='')
            cursor.execute(delete_description)
        except Error as err:
            print(err.msg)
        else:
            print("OK")
    connect.commit()
    cursor.close()

def plot_bar_chart(df, x_col, y_col, title, xlabel, ylabel, rotation=0):
    plt.figure(figsize=(10, 6))
    bars = plt.bar(df[x_col], df[y_col])
    plt.title('\n'.join(wrap(title, 60)))
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.xticks(rotation=rotation)
    
    # Adicionar valores nas barras
    for bar in bars:
        height = bar.get_height()
        plt.text(bar.get_x() + bar.get_width()/2., height,
                 f'{height:.2f}' if isinstance(height, float) else f'{height}',
                 ha='center', va='bottom')
    
    plt.tight_layout()
    plt.show()

def plot_pie_chart(df, labels_col, values_col, title):
    plt.figure(figsize=(8, 8))
    plt.pie(df[values_col], labels=df[labels_col], autopct='%1.1f%%', startangle=140)
    plt.title('\n'.join(wrap(title, 60)))
    plt.axis('equal')
    plt.tight_layout()
    plt.show()

def consulta1(connect):
    select_query = """
    select dep.nome as departamento, count(proj.id_projeto) as total_projetos, 
           avg(aval.nota) as media_avaliacao
    from departamento as dep
        inner join usuario as usr on dep.id_departamento = usr.fk_departamento_id_departamento
        inner join proj_usu as pu on usr.id_usuario = pu.fk_usuario_id_usuario
        inner join projeto as proj on pu.fk_projeto_id_projeto = proj.id_projeto
        left join Avaliacao as aval on proj.id_projeto = aval.fk_projeto_id_projeto
    where proj.data_inicio between '2024-01-01' and '2024-03-31'
    group by dep.nome, dep.id_departamento
    order by total_projetos desc
    """
    print("Primeira Consulta: Projeta o número total de projetos por departamento e a média das avaliações "
          "recebidas pelos projetos iniciados no primeiro trimestre de 2024.")
    
    cursor = connect.cursor()
    cursor.execute(select_query)
    columns = [desc[0] for desc in cursor.description]
    myresult = cursor.fetchall()
    
    # Exibir resultados tabulares
    print("\nResultados Tabulares:")
    df = pd.DataFrame(myresult, columns=columns)
    df['media_avaliacao'] = df['media_avaliacao'].astype(float).round(2)
    print(df.to_string(index=False))
    
    # Gráfico 1: Número de projetos por departamento
    plot_bar_chart(df, 'departamento', 'total_projetos', 
                  'Número de Projetos por Departamento (1º Trimestre 2024)', 
                  'Departamento', 'Total de Projetos', rotation=45)
    
    # Gráfico 2: Média de avaliações por departamento
    plot_bar_chart(df, 'departamento', 'media_avaliacao', 
                  'Média de Avaliações por Departamento (1º Trimestre 2024)', 
                  'Departamento', 'Média de Avaliações', rotation=45)

def consulta2(connect):
    select_query = """
    select proj.titulo, usr.nome as responsavel, count(tar.id_tarefa) as total_tarefas,
           sum(case when tar.status = 'Concluída' then 1 else 0 end) as tarefas_concluidas,
           round((sum(case when tar.status = 'Concluída' then 1 else 0 end) * 100.0 / count(tar.id_tarefa)), 2) as percentual_conclusao
    from projeto as proj
        inner join proj_usu as pu on proj.id_projeto = pu.fk_projeto_id_projeto
        inner join usuario as usr on pu.fk_usuario_id_usuario = usr.id_usuario
        inner join Tarefa as tar on proj.id_projeto = tar.fk_projeto_id_projeto
    where proj.status = 'Em Andamento' and usr.matricula is null
    group by proj.id_projeto, proj.titulo, usr.nome
    having count(tar.id_tarefa) > 1
    order by percentual_conclusao desc
    """
    print("\nSegunda Consulta: Projeta o progresso dos projetos em andamento supervisionados por professores, "
          "mostrando o percentual de conclusão das tarefas.")
    
    cursor = connect.cursor()
    cursor.execute(select_query)
    columns = [desc[0] for desc in cursor.description]
    myresult = cursor.fetchall()
    
    # Exibir resultados tabulares
    print("\nResultados Tabulares:")
    df = pd.DataFrame(myresult, columns=columns)
    print(df.to_string(index=False))
    
    # Gráfico 1: Percentual de conclusão por projeto
    plot_bar_chart(df, 'titulo', 'percentual_conclusao', 
                  'Percentual de Conclusão por Projeto', 
                  'Projeto', 'Percentual de Conclusão (%)', rotation=45)
    
    # Gráfico 2: Tarefas concluídas vs totais (para o projeto com maior percentual)
    if len(df) > 0:
        sample_project = df.iloc[0]
        plt.figure(figsize=(8, 6))
        plt.bar(['Concluídas', 'Pendentes'], 
                [sample_project['tarefas_concluidas'], 
                 sample_project['total_tarefas'] - sample_project['tarefas_concluidas']],
                color=['green', 'orange'])
        plt.title(f"Distribuição de Tarefas - {sample_project['titulo']}")
        plt.ylabel('Número de Tarefas')
        plt.show()

def consulta3(connect):
    select_query = """
    select equip.nome as equipamento, equip.status, count(pe.fk_projeto_id_projeto) as projetos_utilizando,
           STRING_AGG(proj.titulo, '; ') as projetos
    from equipamento as equip
        left join proj_equip as pe on equip.id_equipamento = pe.fk_equipamento_id_equipamento
        left join projeto as proj on pe.fk_projeto_id_projeto = proj.id_projeto
    group by equip.id_equipamento, equip.nome, equip.status
    order by projetos_utilizando desc, equip.nome
    """
    print("\nTerceira Consulta: Apresenta o uso dos equipamentos pelos projetos, incluindo equipamentos "
          "não utilizados atualmente.")
    
    cursor = connect.cursor()
    cursor.execute(select_query)
    columns = [desc[0] for desc in cursor.description]
    myresult = cursor.fetchall()
    
    # Exibir resultados tabulares
    print("\nResultados Tabulares:")
    df = pd.DataFrame(myresult, columns=columns)
    print(df.to_string(index=False))
    
    # Gráfico 1: Equipamentos mais utilizados
    plot_bar_chart(df[df['projetos_utilizando'] > 0], 'equipamento', 'projetos_utilizando', 
                  'Equipamentos Mais Utilizados em Projetos', 
                  'Equipamento', 'Número de Projetos', rotation=45)
    
    # Gráfico 2: Status dos equipamentos (pie chart)
    status_counts = df['status'].value_counts()
    plt.figure(figsize=(8, 8))
    plt.pie(status_counts, labels=status_counts.index, autopct='%1.1f%%', startangle=140)
    plt.title('Distribuição do Status dos Equipamentos')
    plt.axis('equal')
    plt.show()

def consulta_extra(connect, groq):
    select_query = """
    SELECT disc.nome AS disciplina, 
        COUNT(proj.id_projeto) AS projetos_associados,
        COUNT(DISTINCT usr.id_usuario) AS usuarios_envolvidos,
        AVG(EXTRACT(EPOCH FROM CURRENT_DATE - proj.data_inicio) / 86400) AS dias_medio_projeto
    FROM disciplina AS disc
        LEFT JOIN projeto AS proj ON disc.id_disciplina = proj.fk_disciplina_id_disciplina
        LEFT JOIN proj_usu AS pu ON proj.id_projeto = pu.fk_projeto_id_projeto  
        LEFT JOIN usuario AS usr ON pu.fk_usuario_id_usuario = usr.id_usuario
    GROUP BY disc.id_disciplina, disc.nome
    HAVING COUNT(proj.id_projeto) > 0
    ORDER BY projetos_associados DESC;
    """
    print("\nConsulta Extra: Apresenta estatísticas dos projetos por disciplina, incluindo número de usuários "
          "envolvidos e tempo médio dos projetos.")
    
    cursor = connect.cursor()
    cursor.execute(select_query)
    columns = [desc[0] for desc in cursor.description]
    myresult = cursor.fetchall()
    
    # Exibir resultados tabulares
    print("\nResultados Tabulares:")
    df = pd.DataFrame(myresult, columns=columns)
    # Converter a coluna de Decimal para float e arredondar
    df['dias_medio_projeto'] = df['dias_medio_projeto'].astype(float).round(2)

    # Exibir com formatação
    print(df.to_string(index=False))

    if groq:
        return df
    
    # Gráfico 1: Projetos por disciplina
    plot_bar_chart(df, 'disciplina', 'projetos_associados', 
                  'Número de Projetos por Disciplina', 
                  'Disciplina', 'Número de Projetos', rotation=45)
    
    # Gráfico 2: Dias médios por projeto
    plot_bar_chart(df, 'disciplina', 'dias_medio_projeto', 
                  'Duração Média dos Projetos por Disciplina (dias)', 
                  'Disciplina', 'Dias Médios', rotation=45)
    
    return df

def consultar_groq(pergunta):
    url = "https://api.groq.com/openai/v1/chat/completions"

    headers = {
        "Content-Type": "application/json",
        "Authorization": "Bearer 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" #AQUI VAI A CHAVE 
    }

    data = {
        "model": "meta-llama/llama-4-scout-17b-16e-instruct",   
        "messages": [
            {
                "role": "user",
                "content": pergunta
            }
        ],
        "temperature": 0.7
    }

    response = requests.post(url, headers=headers, json=data)

    if response.status_code == 200:
        return response.json()["choices"][0]["message"]["content"]
    else:
        raise Exception(f"Erro {response.status_code}: {response.text}")

def exit_db(connect):
    print("\n---EXIT DB---")
    connect.close()
    print("Conexão com o banco de dados foi encerrada!")

def crud_projetos(connect):
    drop_all_tables(connect)
    create_all_tables(connect)
    insert_test(connect)

    print("\n---CONSULTAS BEFORE---")
    consulta1(connect)
    consulta2(connect)
    consulta3(connect)
    consulta_extra(connect, False)

    update_test(connect)
    delete_test(connect)

    print("\n---CONSULTAS AFTER---")
    consulta1(connect)
    consulta2(connect)
    consulta3(connect)
    consulta_extra(connect, False)

# Main
try:
    # Estabelece Conexão com o DB
    con = connect_projetos_academicos()

    power_up = 1
    while power_up == 1:
        interface = """\n       ---MENU---
        1.  CRUD projetoS
        2.  TESTE - Create all tables
        3.  TESTE - Insert all values
        4.  TESTE - Update
        5.  TESTE - Delete
        6.  CONSULTA 01
        7.  CONSULTA 02
        8.  CONSULTA 03
        9.  CONSULTA EXTRA
        10. CONSULTA TABELAS INDIVIDUAIS
        11. UPDATE VALUES
        12. CLEAR ALL projetoS
        13. CONSULTAR GROQ
        0.  DISCONNECT DB\n """
        print(interface)

        choice = int(input("Opção: "))
        if choice < 0 or choice > 13:
            print("Erro tente novamente!")
            choice = int(input())

        if choice == 0:
            if con and con.closed == 0:
                exit_db(con)
                print("Muito obrigada(o).")
                break
            else:
                break

        if choice == 1:
            crud_projetos(con)

        if choice == 2:
            create_all_tables(con)

        if choice == 3:
            insert_test(con)

        if choice == 4:
            update_test(con)

        if choice == 5:
            delete_test(con)

        if choice == 6:
            consulta1(con)

        if choice == 7:
            consulta2(con)

        if choice == 8:
            consulta3(con)

        if choice == 9:
            consulta_extra(con, False)

        if choice == 10:
            show_table(con)

        if choice == 11:
            update_value(con)

        if choice == 12:
            drop_all_tables(con)

        if choice == 13:
            resposta = consultar_groq(f"Com base nesses dados, que recomendações você daria para melhorar a distribuição de projetos e a participação dos alunos?: {consulta_extra(con, True).to_dict(orient='records')}")
            print("-----" * 20)
            print(resposta)

    con.close()

except Error as err:
    print("Erro na conexão com o banco de dados!", err.msg)
