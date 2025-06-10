# Projeto Jedi - API & Frontend ⚔️

Projeto desenvolvido por **Gabriel Resende Spirlandelli** para a disciplina de **Integração e Entrega Contínua**.

Esta é uma aplicação **full-stack** composta por uma API RESTful desenvolvida em **Java** com Spring Boot e uma interface de usuário criada com **HTML, CSS e JavaScript**. O projeto implementa um CRUD completo para gerenciar "Jedis", com persistência de dados em um banco de dados **PostgreSQL**. A interface do frontend é servida por um servidor **NGINX**, e a documentação da API está acessível via **Swagger**.

## 🚀 URLs de Acesso

A aplicação e a documentação da API estão implantadas e podem ser acessadas através das seguintes URLs:

* **Aplicação Frontend:** **[http://201.23.3.86:8120/](http://201.23.3.86:8120/)**
* **Documentação da API (Swagger):** **[http://201.23.3.86:8119/](http://201.23.3.86:8119/)**

---

## 🎯 Objetivo

O objetivo principal deste projeto é desenvolver uma aplicação web completa, demonstrando um fluxo de trabalho de desenvolvimento moderno e automatizado. Isso inclui a criação de uma interface de usuário interativa, o desenvolvimento de um backend robusto, a persistência de dados com um banco de dados relacional, containerização de serviços com Docker, versionamento de código com Git/GitHub e a implementação de um pipeline de CI/CD com GitHub Actions para automação de deploy e verificação de qualidade de código com SonarQube.

---

## 🛠️ Requisitos Técnicos

### 1. Aplicação

* **Frontend**:
    * Desenvolvido com **HTML5, CSS3 e JavaScript**.
    * Interface reativa que consome a API backend para gerenciar os Jedis.
    * Servido por um container **NGINX** para alta performance de entrega dos arquivos estáticos.

* **Backend**:
    * Desenvolvido em **Java** utilizando o framework **Spring Boot**.
    * Funcionalidades: CRUD completo (Create, Read, Update, Delete) para a entidade Jedi.

* **Documentação da API**:
    * A interface para interação e testes da API é fornecida pelo **Swagger**.

* **Banco de Dados**:
    * **PostgreSQL** relacional, configurado para rodar em uma porta específica.

* **Containerização**:
    * A arquitetura completa (Frontend, Backend e Banco de Dados) roda em contêineres Docker distintos, comunicando-se através de uma rede Docker customizada.

### 2. Repositório GitHub

O código-fonte está versionado neste repositório e inclui:
* `jedi-api/`: Diretório contendo o código-fonte da API Java.
* `jedi-front/`: Diretório contendo os arquivos HTML, CSS e JS do frontend.
* `Dockerfile`: Arquivos de configuração para construir as imagens Docker da aplicação Java e do servidor NGINX.
* `.github/workflows/deploy.yml`: Define o pipeline de CI/CD utilizando GitHub Actions.

### 3. CI/CD com GitHub Actions ⚙️

O processo de integração e entrega contínua é acionado automaticamente a cada `merge` na branch `main`. O fluxo de trabalho executa os seguintes passos:

1.  **Build das Imagens Docker**: Compila a aplicação Java e prepara os arquivos do frontend, gerando novas imagens Docker para a API e para o NGINX.
2.  **Push para o Docker Hub**: Envia as imagens recém-criadas para um repositório no Docker Hub.
3.  **Análise de Qualidade com SonarQube**:
    * O pipeline se conecta ao servidor remoto (`201.23.3.86`) via SSH.
    * Um container do SonarQube é iniciado temporariamente no servidor.
    * A análise do código-fonte é executada tanto para o **backend Java** quanto para o **frontend (HTML, CSS, JS)**.
    * **Gate de Qualidade**: O pipeline **falhará** se o código não atender aos critérios de qualidade definidos no SonarQube.
    * Após a análise, o container do SonarQube é finalizado e removido.
4.  **Deploy Remoto Automatizado**:
    * **Condicional**: Este passo só é executado se a análise do SonarQube for bem-sucedida.
    * O pipeline se conecta novamente ao servidor remoto (`201.23.3.86`).
    * Os contêineres das versões antigas da aplicação (**API e Frontend**) são parados e removidos.
    * As novas imagens são baixadas do Docker Hub.
    * Novos contêineres são iniciados com as versões atualizadas, tornando a aplicação publicamente acessível nas portas especificadas.