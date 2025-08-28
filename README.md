# Library Management System

This is a Grails-based Library Management System developed by **Amer Eid**. The application allows users to manage books, libraries, students, and borrowing activities. It supports both physical and digital books, including PDF upload and download for digital books.

## Features

- **Book Management:** Add, edit, delete, and view books.
- **Digital Book Support:** Upload and download PDF files for digital books.
- **Library Management:** Manage multiple libraries.
- **Student Management:** Register and manage students.
- **Borrowing System:** Borrow and return books.
- **Role-Based Access:** Secure actions for students and librarians.
- **REST API:** API endpoints for book operations.
- **Reports:** Generate reports on library usage.
- **Internationalization:** Multi-language support.

## Technology Stack

- **Framework:** [Grails](https://grails.org/)
- **Database:** MySQL (configurable)
- **Frontend:** GSP (Groovy Server Pages), Bootstrap 5
- **Security:** Spring Security

## Project Structure

```
grails-app/
  assets/         # Static assets (images, JS, CSS)
  conf/           # Configuration files
  controllers/    # MVC Controllers
  domain/         # Domain classes (Book, Library, Student, etc.)
  i18n/           # Internationalization files
  init/           # Application bootstrap
  jobs/           # Scheduled jobs
  services/       # Business logic
  taglib/         # Custom tag libraries
  utils/          # Utility classes
  views/          # GSP views
```

## Setup Instructions

1. **Clone the repository:**
   ```sh
   git clone <repo-url>
   cd library
   ```

2. **Configure the database:**
   - Edit `grails-app/conf/application.yml` or `application.groovy` with your MySQL settings.
   - Ensure the `book.file` column is of type `LONGBLOB` for digital book uploads:
     ```sql
     ALTER TABLE book MODIFY COLUMN file LONGBLOB;
     ```

3. **Run the application:**
   ```sh
   ./grailsw run-app
   ```
   or
   ```sh
   grails run-app
   ```

4. **Access the app:**
   - Open [http://localhost:8080](http://localhost:8080) in your browser.

## Usage

- **Add Books:** Go to the "Add New Book" page. For digital books, upload a PDF file.
- **Download Digital Books:** On the book details page, click "Download PDF" for digital books.
- **Borrow/Return:** Use the buttons on the book details page.
- **Admin Features:** Log in as a librarian for management features.

## Notes

- Only PDF files are accepted for digital books.
- File uploads are limited to 10MB by default (configurable in `Book.groovy`).
- Make sure your MySQL `book.file` column is `LONGBLOB` to avoid upload errors.

## License

This project is for educational purposes.

---

**Developed by Amer Eid with Grails.**