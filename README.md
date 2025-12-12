📚 Library Management System – Java Console Application

A simple and clean Library Management System built using Core Java, following OOP concepts.
This version is console-based and does not use any database connections, making it easy to run anywhere.
You can later extend it by replacing the in-memory list with MySQL JDBC code.

🚀 Features
✔ Book Management

Add new books

Issue a book

Return an issued book

✔ Viewing Options

View all books

View only issued books

✔ Clean Console Menu
--- Library Menu ---
1. Add Book
2. Issue Book
3. Return Book
4. View All Books
5. View Issued Books
6. Exit

🧱 Project Structure
LibraryManagementSystem/
│── Book.java
│── LibraryManagementSystem.java
│── README.md

📌 How It Works
Book Class

Represents a single book with:

id

title

author

isIssued

Library System

Uses an ArrayList<Book> to store all books in memory.
All operations are handled through a menu-driven console interface.

🛠 Technologies Used

Java (JDK 8+)

Core OOP Concepts

ArrayList for storage

No database is used in this version.

▶️ How to Run
1. Compile the program
javac LibraryManagementSystem.java

2. Run the program
java LibraryManagementSystem

🧩 Future Enhancements

You can extend this system by adding:

🔧 MySQL Database Support

Replace ArrayList with JDBC connectivity.
Example features you can add:

Save book details to database

Issue/return status stored permanently

🌐 Web-Based UI (HTML/Servlets/JSP)

Turn the console app into a browser-based dashboard.

🖥 GUI Version

Using JavaFX / Swing.

🤝 Contribution

Feel free to fork this repository and submit pull requests with improvements.

📄 License

This project is open-source and free to use under the MIT License.
