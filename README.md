# Interview Booking System

The Interview Booking System is a Java Spring Boot application designed to facilitate interview scheduling. It provides a set of APIs to create, retrieve, update, and cancel interview slots.

# Table of Contents
1. [Introduction](#interview-slot-booking-api)
2. [API Endpoints](#api-endpoints)
  - [Create Booking Slot](#1-create-booking-slot)
  - [Book a Slot](#2-book-a-slot)
  - [Cancel Booking](#3-cancel-booking)
  - [Update Booking](#4-update-booking)
  - [Get All Slots with Date-Time](#5-get-all-slots-with-date-time)
3. [Run this Project](#run-this-project)
  - [Prerequisites](#prerequisites)
  - [Maven](#maven)
  - [Dockerization](#dockerization)
  - [Docker Compose](#docker-compose)
  - [Docker Hub](#docker-hub)
4. [Contributing](#contributing)
5. [License](#license)

# Interview Slot Booking API
- **Create Booking Slot:** Create interview slots for a specified start time and end time.
- **Book a Slot:** Reserve an available slot for a specific interviewer with an agenda.
- **Cancel Booking:** Cancel a booked interview slot.
- **Update Booking:** Modify the details of a booked interview slot.
- **Get All Slots with Date-Time:** Retrieve all slots within a specified date-time range, along with their availability status and booked details.

## API Endpoints

### 1. Create Booking Slot

- **Endpoint:** `/api/interview/create-slot`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
      "startTime": "2024-01-01T10:00:00",
      "endTime": "2024-01-01T12:00:00"
  }
- **Response:**
  ```json
  {
      "success": true,
      "data": {
          "id": 1,
          "startTime": "2024-01-01T10:00:00",
          "endTime": "2024-01-01T12:00:00",
          "status": "AVAILABLE"
      },
      "message": "Booking slot created successfully"
  }
### 2. Book a Slot

- **Endpoint:** `/api/interview/book-slot`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
      "interviewerId": 1,
      "slotId": 1,
      "agenda": "Interview for Software Engineer",
      "city": "london"
  }
- **Response:**
  ```json
  {
      "success": true,
      "message": "Slot booked successfully"
  }

### 3. Cancel Booking

- **Endpoint:** `/api/interview/cancel-booking`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
      "interviewBookingSlotId": 1
  }
- **Response:**
  ```json
  {
      "success": true,
      "message": "Booking canceled successfully"
  }

### 4. Update Booking

- **Endpoint:** `/api/interview/update-booking`
- **Method:** `POST`
  - **Request Body:**
    ```json
    {
        "interviewerId": 1,
        "slotId": 1,
        "agenda": "Updated Interview Agenda",
        "city": "london",
        "interviewBookingSlotId": 1
    }
- **Response:**
  ```json
  {
      "success": true,
      "data": null,
      "message": "Booking updated successfully"
  }

### 5. Get All Slots with Date-Time

- **Endpoint:** `/api/interview/all-slots`
- **Method:** `GET`
- **Request Parameters:**
  - `startTime` (DateTime): Start time of the date-time range
  - `endTime` (DateTime): End time of the date-time range
- **Response:**
  ```json
  {
      "success": true,
      "data": [
          {
              "slotId": 1,
              "startTime": "2024-01-01T10:00:00",
              "endTime": "2024-01-01T12:00:00",
              "status": "BOOKED",
              "interviewerId": 1,
              "interviewerName": "John Doe"
          },
          {
              "slotId": 2,
              "startTime": "2024-01-01T14:00:00",
              "endTime": "2024-01-01T16:00:00",
              "status": "AVAILABLE"
          }
      ],
      "message": "Operation successful"
  }
## Run this Project
## Prerequisites
Make sure you have the following installed:

1. Java 11 or later
2. Maven
3. Docker (if you want to use Docker)
## Maven
1. Clone the Repository:
```
git clone https://github.com/dipmazumder02/interview-slot-booking.git
cd interview-booking-system
```
2. Build the Application:
```
mvn clean install
```
3. Run the Application:
```
java -jar target/interview-booking-system.jar
```
The application will start on http://localhost:8080.

4. Access Swagger UI for API Documentation:
```
http://localhost:8080/swagger-ui.html
```
## Dockerization
If you prefer running the application in a Docker container:

1. Build the Docker Image:
```agsl
docker build -t interview-booking-system .
```
2. Run the Docker Container:
```agsl
docker run -p 8080:8080 interview-booking-system
```
The application will be accessible at http://localhost:8080.
## Docker Compose
To use Docker Compose for an easy setup:

1. Build and Run with Docker Compose:
```agsl
docker-compose up -d
```
The application will be accessible at http://localhost:8080.
## Docker Hub
Docker image for this project is available on Docker Hub: [interview-slot-booking](https://hub.docker.com/repository/docker/dipmazumder/interview-slot-booking/general)

## Contributing
Contributions are welcome! Please follow the guidelines in [CONTRIBUTING.md](CONTRIBUTING.md).

## License
This project is licensed under the [MIT License](LICENSE).
