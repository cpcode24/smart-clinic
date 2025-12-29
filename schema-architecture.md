### The Smart Clinic Management System follows a three-tier web architecture, which separates the system into three distinct layers:

**Presentation Tier** – The user interface, consisting of Thymeleaf templates and REST API consumers
**Application Tier** – The Spring Boot backend that contains the controllers, services, and business logic
**Data Tier** – The databases: MySQL for structured data and MongoDB for flexible, document-based data
This structure improves scalability, maintainability, and deployment flexibility. Each tier can be independently developed, tested, or scaled without impacting the others directly.

### Tech Stack
_Spring MVC for server-rendered admin and doctor dashboards_
_REST APIs for modular and scalable client-server communication_
_Spring Data JPA for interacting with a MySQL database_
_Spring Data MongoDB for storing flexible prescription records_
_Spring Boot is developer-friendly, production-ready, and integrates easily with testing, validation, and containerization tools._

### REST APIs for scalable integration
For modules like appointments, patient dashboards, and patient records, we expose RESTful APIs instead of using server-side views. REST allows external clients—such as mobile apps or future web apps—to communicate with the backend via lightweight HTTP and JSON.

This makes the system more extensible and interoperable, supporting real-time client applications, third-party integrations, and cross-platform access.

<img width="1405" height="813" alt="image" src="https://github.com/user-attachments/assets/988bfe38-1d06-4aec-9efe-e1b9044aa1b4" />

