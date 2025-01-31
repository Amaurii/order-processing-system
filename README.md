# 🛒 Order Processing System 🚀
**Gerenciamento de pedidos com arquitetura escalável, mensageria e banco NoSQL**

![Java](https://img.shields.io/badge/Java-17-blue?style=flat&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen?style=flat&logo=spring)
![MongoDB](https://img.shields.io/badge/MongoDB-NoSQL-brightgreen?style=flat&logo=mongodb)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Message%20Queue-orange?style=flat&logo=rabbitmq)
![Redis](https://img.shields.io/badge/Redis-Cache-red?style=flat&logo=redis)
---

## 📖 Sobre o Projeto
O **Order Processing System** é um **microserviço** para gerenciamento de pedidos com um fluxo assíncrono e escalável. Ele recebe pedidos, armazena no **MongoDB** e publica no **RabbitMQ** para processamento. Também utiliza **Redis** para evitar duplicidade de pedidos.

---

## ⚙️ **Arquitetura do Sistema**
- **API REST (Spring Boot + Java 17)** para receber pedidos.
- **MongoDB** como banco NoSQL para armazenar pedidos.
- **RabbitMQ** para comunicação assíncrona via filas de mensagens.
- **Redis** para controle de duplicidade e caching.

🛠️ **Diagrama da Arquitetura**
```
[ Serviço Externo A ] ---> [ RabbitMQ ] ---> [ order-service ] ---> [ MongoDB ] --> [ Serviço Externo B ]
```

## 🚀 **Tecnologias Utilizadas**
- **Linguagem**: Java 17
- **Framework**: Spring Boot 3
- **Banco de Dados**: MongoDB
- **Mensageria**: RabbitMQ
- **Cache**: Redis
- **Monitoramento**: Prometheus + Grafana
---

## 🛠 **Como Rodar o Projeto?**
### 🔹 **1. Clonar o Repositório**
```bash
git clone https://github.com/seu-usuario/order-processing-system.git
cd order-processing-system

docker-compose up -d

OBS: Docker-compose para subir os containers. Kubernetes em breve.
```