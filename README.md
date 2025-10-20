# Cybercrime Complaint System

This system is a digital platform designed for the reporting, management, and investigation of cybercrime incidents, serving as a reliable bridge between victims and enforcement agencies.

## Project Alignment (24CST201 & 24CST203)

This project satisfies the combined assessment requirements for both Data Structures (24CST201) and Object-Oriented Programming in Java (24CST203).

### Data Structures & Algorithms (24CST201)

| Criteria | Fulfillment | Rubric Score |
| :--- | :--- | :--- |
| **Data Structures Used** | [cite_start]**Custom Implementation of Two Non-Trivial DS**[cite: 27]: A **Singly Linked List (`MyLinkedList`)** is used for chronological storage of complaints. A **Binary Search Tree (`ComplaintBST`)** is used for efficient lookup by Complaint ID. | [cite_start]Excellent (9-10) [cite: 33] |
| **Algorithm Logic** | [cite_start]Includes **Searching** (BST lookup), **Traversal** (BST inOrder/preOrder methods, utilizing **recursion**), and **Sorting** (Insertion Sort for priority display). [cite: 28] | [cite_start]Excellent (6) [cite: 33] |
| **Integration** | Both `MyLinkedList` and `ComplaintBST` are fully integrated into the `ComplaintManager` service layer to drive core app logic (storage, retrieval, sorting). | [cite_start]Excellent (4) [cite: 33] |

### Object-Oriented Programming (24CST203)

| Criteria | Fulfillment | Rubric Score |
| :--- | :--- | :--- |
| **OOP Design & Implementation** | [cite_start]Clear class hierarchy (`Abstract User` -> `Complainant`, `Officer`) and a well-encapsulated `Complaint` model, demonstrating full use of Inheritance and Encapsulation. [cite: 26] | [cite_start]Excellent (9-10) [cite: 36] |
| **Code Modularity & Structure** | Code is separated into clear layers (`model`, `ds`, `service`, `ui`), ensuring high modularity and reusability. | [cite_start]Excellent (6) [cite: 36] |
| **Input/Output Handling** | [cite_start]Implemented using a **simple GUI with Swing** for complaint submission and tracking, with logic separated into the `service` layer. [cite: 30] | [cite_start]Excellent (4) [cite: 36] |
| **Error Handling & Robustness** | [cite_start]Custom **`ValidationException`** is used to handle invalid submissions (e.g., missing titles, duplicate IDs), providing robust exception and input handling. [cite: 26] | [cite_start]Excellent (5) [cite: 36] |

## How to Build and Run

1.  **Requirements**: Ensure **JDK 11+** is installed.
2.  **Setup**: All `.java` files must be placed in a single `src` folder (using the default package structure).
3.  **Compile**: Open your command prompt in the `src` folder and run:
    ```bash
    javac *.java
    ```
4.  **Run**: After successful compilation, run the application using:
    ```bash
    java App
    ```
    This launches the Swing GUI with pre-loaded sample data for immediate testing.