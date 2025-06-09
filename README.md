# Jedi API ⚔️

Projeto desenvolvido por **Gabriel Resende Spirlandelli** para a disciplina de **Integração e Entrega Contínua**.

Esta é uma API RESTful desenvolvida em **Java** com o framework Spring Boot. O projeto implementa um CRUD completo para gerenciar "Jedis" e "Padawans", com persistência de dados em um banco de dados **PostgreSQL**. A documentação e a interface para testes da API estão disponíveis e acessíveis via **Swagger**.

## 🚀 URL de Acesso

A aplicação está implantada e pode ser acessada através da seguinte URL. A interface do Swagger permite visualizar e interagir com todos os endpoints da API.

**[http://201.23.3.86:8108/swagger-ui/index.html](http://201.23.3.86:8108/swagger-ui/index.html)**

---

## 🎯 Objetivo

O objetivo principal deste projeto é desenvolver uma aplicação web completa, demonstrando um fluxo de trabalho de desenvolvimento moderno e automatizado. Isso inclui a persistência de dados com um banco de dados relacional, containerização de serviços com Docker, versionamento de código com Git/GitHub e a implementação de um pipeline de CI/CD (Integração e Entrega Contínua) com GitHub Actions para automação de deploy e verificação de qualidade de código com SonarQube.

---

## 🛠️ Requisitos Técnicos

### 1. Aplicação

* **Backend**: Desenvolvido em Java utilizando o framework Spring Boot.
* **Funcionalidades**: CRUD completo (Create, Read, Update, Delete) para a entidade Jedi.
* **Interface Gráfica**: A documentação e a interface para interação e testes da API são fornecidas pelo **Swagger**.
* **Banco de Dados**: **PostgreSQL** relacional, configurado para rodar em uma porta específica para evitar conflitos.
* **Containerização**: A aplicação e o banco de dados rodam em containers Docker distintos, comunicando-se através de uma rede Docker customizada.

### 2. Repositório GitHub

O código-fonte está versionado neste repositório e inclui:

* `Dockerfile`: Arquivo de configuração para construir a imagem Docker da aplicação Java.
* `.github/workflows/deploy.yml`: Define o pipeline de CI/CD utilizando GitHub Actions.
* `docker-compose.yml`: (Opcional, mas recomendado) Orquestra a inicialização dos contêineres da aplicação e do banco de dados para desenvolvimento local.

### 3. CI/CD com GitHub Actions ⚙️

O processo de integração e entrega contínua é acionado automaticamente a cada `merge` na branch `main`. O fluxo de trabalho executa os seguintes passos:

1.  **Build da Imagem Docker**: Compila a aplicação Java e gera uma nova imagem Docker.
2.  **Push para o Docker Hub**: Envia a imagem recém-criada para um repositório no Docker Hub.
3.  **Análise de Qualidade com SonarQube**:
    * O pipeline se conecta ao servidor remoto (`201.23.3.86`) via SSH.
    * Um container do SonarQube é iniciado temporariamente no servidor para a análise.
    * A análise do código-fonte é executada.
    * **Gate de Qualidade**: O pipeline **falhará** se o código não atender aos critérios de qualidade definidos no SonarQube.
    * Após a análise, o container do SonarQube é finalizado e removido para liberar recursos.
4.  **Deploy Remoto Automatizado**:
    * **Condicional**: Este passo só é executado se a análise do SonarQube for bem-sucedida.
    * O pipeline se conecta novamente ao servidor remoto (`201.23.3.86`).
    * O container da versão antiga da aplicação é parado e removido.
    * A nova imagem da aplicação é baixada do Docker Hub.
    * Um novo container é iniciado com a versão atualizada da aplicação, tornando-a publicamente acessível na porta especificada.
