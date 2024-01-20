# Interview Booking System

The Interview Booking System is a Java Spring Boot application designed to facilitate interview scheduling. It provides a set of APIs to create, retrieve, update, and cancel interview slots.

## Table of Contents
1. [APIs](#apis)
2. [Setup](#setup)
3. [Usage](#usage)
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

## Setup
1. Clone the repository: `git clone https://github.com/your-username/interview-booking-system.git`
2. Navigate to the project directory: `cd interview-booking-system`
3. Install dependencies: `./mvnw clean install`

## Usage
1. Run the application: `./mvnw spring-boot:run`
2. Access Swagger UI documentation: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Contributing
Contributions are welcome! Please follow the guidelines in [CONTRIBUTING.md](CONTRIBUTING.md).

## License
This project is licensed under the [MIT License](LICENSE).
