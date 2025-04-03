
# Arcade Rank Project

## Description
This project is a simple management system for arcade games and players. It allows users to add new players, manage scores, and view rankings.

## Features
- Add and manage players.
- Add scores for players in different games.
- Display a ranking of the top scores.
- Option to delete players and scores.

## Technologies Used
- **Backend**: Java, Spring Framework, JPA (Hibernate)
- **Frontend**: Thymeleaf, TailwindCSS
- **Database**: H2 Database (or another database can be configured)
- **Build Tool**: Maven
- **Version Control**: GitHub

## How to Run the Project

### Prerequisites
- Java 11 or newer
- Maven
- An IDE (e.g., IntelliJ IDEA)

### Steps to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/arcade-rank.git
   ```
2. Navigate to the project directory:
   ```bash
   cd arcade-rank
   ```
3. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Open your browser and go to `http://localhost:8080`.

## Directory Structure

```
arcade-rank/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   └── infobasic/
│   │   │   │       └── arcade/
│   │   │   │           └── arcade_rank/
│   │   │   │               ├── controller_view/
│   │   │   │               ├── model/
│   │   │   │               ├── repository/
│   │   │   │               └── service/
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
├── pom.xml
└── README.md
```

## License
This project is licensed under the MIT License.
