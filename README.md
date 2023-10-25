# TrueML Backend Scala

This project is a Scala-based implementation designed to replicate the customer endpoints of a specified API using GraphQL. It's containerized with Docker for easy deployment and scalability.

## Project Structure

- `src/main/scala/domain/models`: Contains domain model definitions.
- `src/main/scala/domain/services`: Contains service interfaces and implementations.
- `src/main/scala/infrastructure/repository`: Contains an in-memory repository for simplicity.
- `src/main/scala/api/graphql`: Contains GraphQL schema definitions.
- `src/main/scala/api/routes`: Contains Akka HTTP route definitions.
- `src/main/scala`: Contains the main application entry point.
- `src/test/scala`: Contains the test suite for the project.

## Setup and Running

### Local Setup

1. Ensure you have Scala and SBT installed on your machine.
2. Clone this repository and navigate to the project directory.

```bash
git clone <repository-url>
cd trueml-backend-scala
```

Run the application using SBT.

```bash
sbt run
```
The server will start and listen on http://localhost:8080. You can send GraphQL queries to http://localhost:8080/graphql.

## Docker Setup

Build the Docker image
```bash
docker run -d -p 8080:8080 trueml-backend-scala
```

Now the application is running in a Docker container, and you can access it at http://localhost:8080/graphql.

## Testing
Run the test suite using SBT:

```bash
sbt test
```

## Deployment

The application is containerized using Docker, making it easy to deploy in any environment that supports Docker containers.

## Deployment on AWS

Deploying on AWS can be done using Amazon Elastic Kubernetes Service (EKS).

### Prerequisites:

1. Install and configure the [AWS CLI](https://aws.amazon.com/cli/).
3. Install [eksctl](https://eksctl.io/) and [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/).

### Steps:

1. **Build a Docker Image**:

    First, build a Docker image of your application.

    ```bash
    docker build -t trueml-backend-scala:latest .
    ```

2. **Push the Docker Image to Amazon Elastic Container Registry (ECR)**:

    Create a repository in ECR and push your Docker image to it.

    ```bash
    aws ecr create-repository --repository-name trueml-backend-scala
    aws ecr get-login-password --region region | docker login --username AWS --password-stdin <account-id>.dkr.ecr.<region>.amazonaws.com
    docker tag trueml-backend-scala:latest <account-id>.dkr.ecr.<region>.amazonaws.com/trueml-backend-scala:latest
    docker push <account-id>.dkr.ecr.<region>.amazonaws.com/trueml-backend-scala:latest
    ```

3. **Create a Kubernetes Cluster on EKS**:

    ```bash
    eksctl create cluster --name trueml-backend-scala-cluster --nodes 3
    ```

4. **Deploy the Application to EKS**:

    Apply the configuration to deploy the application and expose it via a LoadBalancer service:

    ```bash
    kubectl apply -f deployment.yaml
    ```

5. **Access the Application**:

    After a few minutes, you should be able to access the application via the external IP address provided by the LoadBalancer service. You can find this IP address by running:

    ```bash
    kubectl get svc trueml-backend-scala-service
    ```

    Your application should now be accessible at `http://<external-ip>:80/graphql`.
