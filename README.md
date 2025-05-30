# ✈️ Airline Reservation System

A desktop-based Airline Reservation System developed using **Java Swing & AWT**, designed to simulate the core functionalities of booking and managing flight tickets. The application supports user registration, login, flight search, booking, fare viewing, payment, and ticket generation — all within an interactive GUI.

## 🛠️ Tech Stack

- **Java Swing / AWT** – GUI Framework
- **JDBC** – Database Connectivity (MySQL or SQLite)
- **IntelliJ IDEA** – IDE used
- **Java SE** – JDK 8+

---

## 📂 Project Structure


---

## ✅ Features

- 🛫 **Flight Search**: Search available flights.
- 📝 **User Registration/Login**: Register or log in with credentials.
- 💳 **Booking & Payment**: Select flight, enter details, and make a simulated payment.
- 🎫 **Ticket Generation**: View and confirm ticket with details.
- 💰 **Fare Calculator**: Displays the breakdown of pricing based on selection.
- 🔐 **Secure DB Connection**: Handled using JDBC.

---

## 🖼️ Screenshots

<img width="350" alt="Screenshot 2025-05-30 at 11 08 25 PM" src="https://github.com/user-attachments/assets/90880767-6639-4714-bca6-e9cc94349bd3" />
<img width="344" alt="Screenshot 2025-05-30 at 11 08 40 PM" src="https://github.com/user-attachments/assets/1d384221-e065-4dde-9a1b-f1661420f7c1" />
<img width="294" alt="Screenshot 2025-05-30 at 11 08 51 PM" src="https://github.com/user-attachments/assets/17ce9614-eea5-44bf-b534-8b7476fe2e0d" />
<img width="293" alt="Screenshot 2025-05-30 at 11 08 57 PM" src="https://github.com/user-attachments/assets/1da7fb24-00d8-4f1d-a153-751a1b469866" />
<img width="348" alt="Screenshot 2025-05-30 at 11 10 07 PM" src="https://github.com/user-attachments/assets/4d1fa5b5-1bfc-498d-8214-6b10bc72fc14" />
<img width="596" alt="Screenshot 2025-05-30 at 11 09 51 PM" src="https://github.com/user-attachments/assets/ec747297-6cc2-4902-aaac-56635aff39e1" />
<img width="399" alt="Screenshot 2025-05-30 at 11 10 54 PM" src="https://github.com/user-attachments/assets/5524e1cc-b8b4-479b-92de-4dee4cbf8fbb" />
<img width="343" alt="Screenshot 2025-05-30 at 11 10 21 PM" src="https://github.com/user-attachments/assets/82558950-09f0-41a0-a691-64cfa40021bd" />
<img width="396" alt="Screenshot 2025-05-30 at 11 10 44 PM" src="https://github.com/user-attachments/assets/4312c770-b8aa-493e-af0c-891641673f6d" />
<img width="263" alt="Screenshot 2025-05-30 at 11 10 49 PM" src="https://github.com/user-attachments/assets/a72d4f10-a5d3-401f-91ac-79ec7a472ee5" />


---

## 🗄️ **Database Schema: `airline_db`**

---

### 🔹 **1. `users` Table**

📌 *Stores user credentials and contact information for login and identification.*

| Field Name | Data Type    | Constraints                  |
| ---------- | ------------ | ---------------------------- |
| user_id    | INT          | PRIMARY KEY, AUTO_INCREMENT  |
| username   | VARCHAR(50)  | NOT NULL, UNIQUE             |
| password   | VARCHAR(255) | NOT NULL                     |
| email      | VARCHAR(100) | NOT NULL, UNIQUE             |

- ✅ Stores registered users  
- ✅ Used during login and ticket generation

---

### 🔹 **2. `flights` Table**

📌 *Holds details of available flights for booking and display purposes.*

| Field Name      | Data Type   | Constraints                  |
| --------------- | ----------- | ---------------------------- |
| flight_id       | INT         | PRIMARY KEY, AUTO_INCREMENT  |
| airline         | VARCHAR(50) | NOT NULL                     |
| departure       | VARCHAR(50) | NOT NULL                     |
| destination     | VARCHAR(50) | NOT NULL                     |
| departure_time  | DATETIME    | NOT NULL                     |
| price           | DOUBLE      | NOT NULL                     |

- ✅ Stores all flight information  
- ✅ Used during flight search

---

### 🔹 **3. `bookings` Table**

📌 *Tracks which user booked which flight along with the assigned seat.*

| Field Name   | Data Type  | Constraints                                 |
| ------------ | ---------- | ------------------------------------------- |
| booking_id   | INT        | PRIMARY KEY, AUTO_INCREMENT                 |
| user_id      | INT        | FOREIGN KEY REFERENCES `users(user_id)`     |
| flight_id    | INT        | FOREIGN KEY REFERENCES `flights(flight_id)` |
| seat_number  | VARCHAR(5) | NOT NULL                                    |

- ✅ Stores which user booked which flight and seat  
- ✅ `seat_number` is used to check availability

---

### 🔹 **4. `payments` Table**

📌 *Logs payment transactions made for flight bookings.*

| Field Name      | Data Type   | Constraints                                   |
| --------------- | ----------- | --------------------------------------------- |
| payment_id      | INT         | PRIMARY KEY, AUTO_INCREMENT                   |
| booking_id      | INT         | FOREIGN KEY REFERENCES `bookings(booking_id)` |
| amount          | DOUBLE      | NOT NULL                                      |
| payment_status  | VARCHAR(20) | DEFAULT 'Completed'                           |

- ✅ Links payment with a booking  
- ✅ In your project, status is always inserted as `'Completed'`

---

## 🔗 **Entity Relationship (ER) Summary**

- `users` → `bookings`: One-to-Many  
- `flights` → `bookings`: One-to-Many  
- `bookings` → `payments`: One-to-One  

---
## ⚙️ How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/AirplaneBookingSystem.git
2. Open the project in IntelliJ IDEA or any Java IDE.
3. Configure your database credentials in DBConnection.java.
4. Run the Main.java file.
