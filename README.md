# 🎓 SVNIT University Management System

A terminal-based University Course Registration System built from scratch using core Java and advanced Object-Oriented Programming (OOP) principles. 

This project simulates a real-world enterprise academic management system supporting four distinct user roles—**Students**, **Professors**, **Administrators**, and **Teaching Assistants (TAs)**—each equipped with dedicated dashboards and role-specific permissions.

---

## 🌟 Key Features & Architectural Design

Beyond standard assignment requirements, this system incorporates enterprise-grade software patterns and design choices:

1. **Singleton Pattern for Repositories**
   * Uses thread-safe double-checked locking across all repositories (`UserRepository`, `CourseRepository`, `CourseRecordRepository`, `ComplaintRepository`).
   * Guarantees a single, memory-safe data store throughout the application lifecycle while preventing race conditions and data duplication.
2. **Strict 4-Layer Architecture**
   * Clear separation of concerns making the system UI-agnostic:
     * `model/`: Pure data objects.
     * `repository/`: In-memory HashMap storage.
     * `service/`: All business logic and validation rules.
     * `ui/`: Terminal-based interaction layer.
3. **Generic Feedback System**
   * Leverages **Java Generics** (`Feedback<T>`) to handle both numeric ratings (`Feedback<Integer>`) and textual reviews (`Feedback<String>`) within a single, type-safe framework using wildcards (`Feedback<?>`).
4. **Custom Exception Hierarchy**
   * Domain-specific exception handling for precise control and explicit method signatures:
     * `CourseFullException`: Triggered when course enrollment hits capacity.
     * `InvalidLoginException`: Thrown on bad credentials instead of ambiguous null returns.
     * `DropDeadlinePassedException`: Raised when a student attempts to drop a course past the deadline.
5. **Role Extension via Multi-Level Inheritance**
   * The `TA` class extends `Student` rather than `User`, illustrating the **Open/Closed Principle**. TAs inherit full student functionality (enrolling, filing complaints, GPA tracking) while gaining specialized grading assistance capabilities.
6. **Automated Admin Provisioning**
   * The system auto-initializes a default Administrator account upon startup, mirroring real-world application deployment standards.

---

## 🧩 OOP Concepts Applied

| Concept | Implementation Details |
| :--- | :--- |
| **Abstract Class** | `User.java` serves as a non-instantiable base template for all roles. |
| **Inheritance** | `Student`, `Professor`, and `Administrator` extend `User`; `TA` extends `Student`. |
| **Polymorphism** | `displayDashboard()` overridden across subclasses for dynamic role dispatch. |
| **Encapsulation** | Private fields paired with explicit, validated accessors and mutators. |
| **Abstraction** | Business logic completely encapsulated in `service/` components. |
| **Generics** | Flexible, reusable `Feedback<T>` data model. |
| **Design Patterns** | Thread-safe Singleton pattern for repository management. |
| **Exception Handling** | Custom exception hierarchy propagated and handled gracefully in the UI. |

---

## 📁 Package Structure

```text
com.svnit/
├── model/           → User, Student, Professor, Administrator, TA,
│                      Course, CourseRecord, Complaint, Feedback<T>
├── enums/           → Role, ComplaintStatus
├── exceptions/      → CourseFullException, InvalidLoginException,
│                      DropDeadlinePassedException
├── repository/      → UserRepository, CourseRepository,
│                      CourseRecordRepository, ComplaintRepository,
│                      FeedbackRepository
├── service/         → AuthService, EnrollmentService, GradeService,
│                      ComplaintService, FeedbackService
├── ui/              → LoginPanel, StudentMenu, ProfessorMenu,
│                      AdminMenu, TAMenu
└── Main.java        → Main application entry point

⚙️ Core System Rules

    Credit Limits: Hard maximum of 20 credits per semester. Valid course credit weights are strictly 2 or 4.

    Automatic Progression: Students start at Semester 1 and advance automatically once all enrolled courses are fully graded.

    Privilege Hierarchy: TAs suggest grades; final grade publishing rights reside exclusively with Administrators.

    Storage: Volatile in-memory state store optimized for rapid local evaluation.
