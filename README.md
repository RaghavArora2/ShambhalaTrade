# Cryptocurrency Trading Web App

## Overview

This is a personal project for a cryptocurrency trading web application. The application uses:

- **Spring Boot** for the backend.
- **MySQL** for the database.
- **React** and **Tailwind CSS** for the frontend.
- **Redux** for state management.

The app fetches real-time cryptocurrency data and prices from CoinGecko and Gemini APIs. It also includes functionality to buy cryptocurrencies.

## Features

- **Real-Time Data**: Displays current cryptocurrency data and prices using CoinGecko and Gemini APIs.
- **Buy Functionality**: Allows users to purchase cryptocurrencies.
- **Responsive Design**: Built with React and Tailwind CSS for a modern and responsive user interface.
- **State Management**: Utilizes Redux for managing application state.

## Installation

### Backend (Spring Boot)

1. **Clone the repository:**

   ```bash
   git clone https://github.com/RaghavArora2/ShambhalaTrade
   ```

2. **Navigate to the backend directory:**

   ```bash
   cd backend
   ```

3. **Install dependencies:**

   ```bash
   ./mvnw install
   ```

4. **Run the Spring Boot application:**

   ```bash
   ./mvnw spring-boot:run
   ```

### Frontend (React)

1. **Navigate to the frontend directory:**

   ```bash
   cd frontend
   ```

2. **Install dependencies:**

   ```bash
   npm install
   ```

3. **Run the React application:**

   ```bash
   npm start
   ```

## Configuration

- **Backend**: Configure your application properties in `src/main/resources/application.properties` for database connection and API keys.
- **Frontend**: Update environment variables in the application properties file for API endpoints and other settings.

## Contributing

This is a personal project. However, contributions and feedback are welcome. Please feel free to open issues or submit pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any questions or feedback, you can reach out to me at [raghavarora419@gmail.com](mailto:raghavarora419@gmail.com).
