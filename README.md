# Cake-Scheduler

**Cake-Scheduler** é um aplicativo desenvolvido em **Vitejs** e **Java Spring** para cadastro e agendamento de bolos. A plataforma permite que funcionários cadastrem bolos com diversos recheios e métodos de pagamento, enquanto os clientes podem fazer pedidos de maneira fácil e intuitiva.

## Tecnologias Utilizadas
- **Java Spring Boot**: Framework java para construção de APIs robustas e escaláveis.
- **PostgreSQL** Sistema de gerenciamento de banco de dados relacional.

## Funcionalidades
- **Cadastro de bolos com opções de recheios e complementos.**
- **Gerenciamento de agendamentos para pedidos de bolos.**
- **Interface de cliente para visualizar e fazer pedidos.**
- **Rota de agendamentos para funcionários gerenciarem pedidos.**

## Como Rodar o Projeto

### Pré-requisitos

- Java 17+, Maven e PostgreSQL instalados.

### Instalação

1. Clone o repositório do backend:

 ```bash
git clone https://github.com/WesleydaCunha/CakeScheduler-backend
cd CakeScheduler-backend
 ```

2. Configure as variáveis de ambiente:

   Renomeie o arquivo `.env.exemple`  na raiz do projeto para `.env` com as seguintes variáveis:

   ```bash
   DEFAULT_USER_EMPLOYEE_EMAIL=your_employee_email_here
   DEFAULT_USER_EMPLOYEE_PASSWORD=your_employee_password_here
   DEFAULT_USER_EMPLOYEE_NAME=your_employee_name_here
   DEFAULT_USER_EMPLOYEE_PHONE=your_employee_phone_here

   DATABASE_URL=your_database_url_here
   SECRET_ENCRYPTION_KEY=your_secret_encryption_key_here
   ```
3. Instale as dependências:

   ```bash
   mvn clean install
   ```

4. Inicie o servidor Spring Boot:

   ```bash
   mvn spring-boot:run
   ```
5. O backend estará rodando em `http://localhost:8080`.
   
7. Configure o Front-end:
   - Acesse e configure o [Front-end](https://github.com/WesleydaCunha/CakeScheduler-frontend/)
     
## Contribuição

Se você deseja contribuir com o desenvolvimento do CakeScheduler, siga as etapas abaixo:

1. Faça um fork deste repositório.
2. Crie uma nova branch para a sua feature/bugfix (`git checkout -b feature/nome-da-feature`).
3. Commit suas alterações (`git commit -m 'Add some feature'`).
4. Faça o push para a branch (`git push origin feature/nome-da-feature`).
5. Abra um Pull Request.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

---   



