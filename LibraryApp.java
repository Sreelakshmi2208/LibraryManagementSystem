package library;
import java.sql.*;
import java.util.Scanner;

class Book {
    private int id;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(int id, String title, String author, boolean isIssued) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = isIssued;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return isIssued; }
}

public class LibraryApp {

    // 🔗 XAMPP MySQL Settings
    private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
    private static final String USER = "root";      // default XAMPP username
    private static final String PASS = "";          // default XAMPP password = empty

    private static Connection conn;

    public static void main(String[] args) {

        try {
            // CONNECT TO DATABASE
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Connected to MySQL Successfully!");

            Scanner sc = new Scanner(System.in);
            int choice;

            while (true) {
                System.out.println("\n--- Library Menu ---");
                System.out.println("1. Add Book");
                System.out.println("2. Issue Book");
                System.out.println("3. Return Book");
                System.out.println("4. View All Books");
                System.out.println("5. View Issued Books");
                System.out.println("6. Exit");
                System.out.print("Enter choice: ");

                while (!sc.hasNextInt()) {
                    System.out.println("❌ Enter a valid number!");
                    sc.next();
                }
                choice = sc.nextInt();
                sc.nextLine(); // clear buffer

                switch (choice) {
                    case 1: addBook(sc); break;
                    case 2: issueBook(sc); break;
                    case 3: returnBook(sc); break;
                    case 4: viewAllBooks(); break;
                    case 5: viewIssuedBooks(); break;
                    case 6:
                        System.out.println("👋 Exiting...");
                        conn.close();
                        return;
                    default:
                        System.out.println("❌ Invalid Choice!");
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Database Error: " + e.getMessage());
        }
    }

    // ----------------------- Add Book -----------------------
    private static void addBook(Scanner sc) {
        try {
            System.out.print("Enter Book Title: ");
            String title = sc.nextLine();

            System.out.print("Enter Book Author: ");
            String author = sc.nextLine();

            if (title.isEmpty() || author.isEmpty()) {
                System.out.println("❌ Title/Author cannot be empty!");
                return;
            }

            String sql = "INSERT INTO books (title, author, isIssued) VALUES (?, ?, false)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, author);
            ps.executeUpdate();

            System.out.println("📘 Book Added Successfully!");

        } catch (Exception e) {
            System.out.println("❌ Error Adding Book: " + e.getMessage());
        }
    }

    // ----------------------- Issue Book -----------------------
    private static void issueBook(Scanner sc) {
        try {
            System.out.print("Enter Book ID to Issue: ");

            while (!sc.hasNextInt()) {
                System.out.println("❌ Enter a valid number!");
                sc.next();
            }
            int id = sc.nextInt();

            String checkSql = "SELECT isIssued FROM books WHERE id=?";
            PreparedStatement ps1 = conn.prepareStatement(checkSql);
            ps1.setInt(1, id);
            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) {
                System.out.println("❌ Book Not Found!");
                return;
            }

            if (rs.getBoolean("isIssued")) {
                System.out.println("❌ Book Already Issued!");
                return;
            }

            String sql = "UPDATE books SET isIssued=true WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("📕 Book Issued Successfully!");

        } catch (Exception e) {
            System.out.println("❌ Error Issuing Book: " + e.getMessage());
        }
    }

    // ----------------------- Return Book -----------------------
    private static void returnBook(Scanner sc) {
        try {
            System.out.print("Enter Book ID to Return: ");

            while (!sc.hasNextInt()) {
                System.out.println("❌ Enter a valid number!");
                sc.next();
            }
            int id = sc.nextInt();

            String checkSql = "SELECT isIssued FROM books WHERE id=?";
            PreparedStatement ps1 = conn.prepareStatement(checkSql);
            ps1.setInt(1, id);
            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) {
                System.out.println("❌ Book Not Found!");
                return;
            }

            if (!rs.getBoolean("isIssued")) {
                System.out.println("❌ Book is NOT Issued!");
                return;
            }

            String sql = "UPDATE books SET isIssued=false WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("📗 Book Returned Successfully!");

        } catch (Exception e) {
            System.out.println("❌ Error Returning Book: " + e.getMessage());
        }
    }

    // ----------------------- View All Books -----------------------
    private static void viewAllBooks() {
        try {
            String sql = "SELECT * FROM books ORDER BY id ASC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\n--- All Books ---");
            while (rs.next()) {
                System.out.println(
                    "ID: " + rs.getInt("id") +
                    " | Title: " + rs.getString("title") +
                    " | Author: " + rs.getString("author") +
                    " | Issued: " + rs.getBoolean("isIssued")
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Error Viewing Books: " + e.getMessage());
        }
    }

    // ----------------------- View Issued Books -----------------------
    private static void viewIssuedBooks() {
        try {
            String sql = "SELECT * FROM books WHERE isIssued=true";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\n--- Issued Books ---");
            while (rs.next()) {
                System.out.println(
                    "ID: " + rs.getInt("id") +
                    " | Title: " + rs.getString("title") +
                    " | Author: " + rs.getString("author")
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Error Viewing Issued Books: " + e.getMessage());
        }
    }
}
