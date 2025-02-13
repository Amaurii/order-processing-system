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
[ Serviço Externo A ] ---> [ order-service ] + [ RabbitMQ ]  ---> [ MongoDB ] --> [ Serviço Externo B ]
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
git git@github.com:Amaurii/order-processing-system.git
cd order-processing-system

docker-compose up -d

OBS: Docker-compose para subir os containers. Kubernetes em breve.
```

Comandos Kubernets:

```bash
    --  Iniciando o POD RabbitMQ
kubectl get pods
kubectl apply -f rabbitmq-pod.yaml
kubectl port-forward pod/rabbitmq 5672:5672 15672:15672
kubectl describe pod rabbitmq

    -- Iniciando o POD MongoDB  
kubectl apply -f mongodb-pod.yaml   
kubectl port-forward pod/mongodb 27017:27017 
kubectl describe pod mongodb

    -- Iniciando o POD Redis   
kubectl apply -f redis-pod.yaml
kubectl port-forward pod/redis 6379:6379
kubectl describe pod redis

 -- Acessando o Redis dentro do Kubernets
 kubectl exec -it redis -- redis-cli

    -- Iniciando o POD Prometheus
kubectl apply -f prometheus-pod.yaml
kubectl port-forward pod/prometheus 9090:9090
kubectl describe pod prometheus

    -- Iniciando o POD Grafana
kubectl apply -f grafana-pod.yaml
kubectl port-forward pod/grafana 3000:3000
kubectl describe pod grafana

    -- Iniciando o POD Order-Service
    docker build -t amaurii/order-processing:latest .
    docker run -p 8080:8080 amaurii/order-processing:latest
    
```

Rodar a aplicação local

```bash
mvn clean install
```

```bash

```

```bash
mvn clean package
docker build -t amaurii/order-processing:latest .
docker tag amaurii/order-processing:latest amaurii/order-processing:latest
docker push amaurii/order-processing:latest
kubectl delete pod order-processing
kubectl apply -f kubernetes/pods/app-pod.yaml 
```

```bash
kubectl exec -it order-processing -- sh
kubectl exec -it rabbitmq -- sh
kubectl logs order-processing
kubectl delete pod order-processing
kubectl get pods
kubectl apply -f kubernetes/pods/app-pod.yaml
```

```bash
docker run -p 8080:8080 amaurii/order-processing:latest
```

````bash
kubectl port-forward pod/order-processing 8080:8080
````

kubectl delete deployment order-processing
