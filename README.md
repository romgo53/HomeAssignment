# Home assignment

---
## Part 1:


### Backend application:

#### Details:
- Chosen database: MongoDB (Runs on docker)

### Build & Run 🏗️ & ▶️
 In the root directory of the project run the following command:
```bash
sh run.sh
```

### Interactive API documentation 📖
After running the application, you can access the: __[Interactive API Docs](http://localhost:8080/swagger-ui])__ 

### Requirements 📝
- Docker and docker-compose installed
- Java 17 installed
- JAVA_HOME environment variable set to the Java 17 installation directory

## Part 2:

### Dynamic provider with Pulumi 🚀

### Details:
- For this part I've chosen the Product as the resource to be managed by the Pulumi provider.
- The provider is implemented in TypeScript.

### Installation 🔽️
From the root directory of the project navigate to the `infrastructure` directory and run the following command:
```bash
npm i
```

### Run ▶️
Before running the provider, make sure that the backend application is running.

In the `infrastructure` directory run the following command:
- To run the create method run:
```bash
pulumi up
```
This will run the provider locally and will create the product that was defined in the `index.ts` file.

- To run the delete method run:
```bash
pulumi destroy
```
This will run the provider locally and will delete the product that was defined in the `index.ts` file.

### Requirements 📝
- Node.js installed
- Pulumi installed
- NPM installed
