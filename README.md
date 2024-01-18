# Interview Booking System

The Interview Booking System is a Java Spring Boot application designed to facilitate interview scheduling. It provides a set of APIs to create, retrieve, update, and cancel interview slots.

## Table of Contents
1. [APIs](#apis)
2. [Setup](#setup)
3. [Usage](#usage)
4. [Contributing](#contributing)
5. [License](#license)

## APIs
- **CreateBookingSlot():**
  - Endpoint to create a new interview slot.
  - **Request:**
    - Method: `POST`
    - URL: `/api/booking/create`
    - Body: `BookingRequestDTO`
  - **Response:**
    - `BookingResponseDTO`

- **getAllSlotWithDateTime():**
  - Endpoint to retrieve all available slots for a specific date and time.
  - **Request:**
    - Method: `GET`
    - URL: `/api/booking/slots`
    - Query Param: `dateTime` (ISO8601 format)
  - **Response:**
    - List of `BookingResponseDTO`

- **BookASlot():**
  - Endpoint to book an available slot.
  - **Request:**
    - Method: `POST`
    - URL: `/api/booking/book/{slotId}`
    - Path Variable: `slotId`
  - **Response:**
    - Success or error message

- **CancelBooking():**
  - Endpoint to cancel a booked slot.
  - **Request:**
    - Method: `POST`
    - URL: `/api/booking/cancel/{slotId}`
    - Path Variable: `slotId`
  - **Response:**
    - Success or error message

- **UpdateBooking():**
  - Endpoint to update the date and time of a booked slot.
  - **Request:**
    - Method: `POST`
    - URL: `/api/booking/update/{slotId}`
    - Path Variable: `slotId`
    - Body: `BookingRequestDTO`
  - **Response:**
    - Success or error message

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
