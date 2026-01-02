
## Tables

### Structured data
We need the following tables for structured data:
*patients 
*doctors
*admin
*appointments
*clinic_locations
*payments.

### Table: patients
- patient_id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id)
- firstname: VARCHAR(20), Not Null
- lastname: VARCHAR(20), Not Null
- midname: VARCHAR(20)
- dob: INT, Not Null, (YYYYMMDD)
- address: VARCHAR(30), Not Null
- zip: INT, Not null
- city: VARCHAR(20), Not Null
- state: VARCHAR(20), Not Null
- appointment_id: INT, Foreign Key → appointments(id)
- status: INT (0 = Not Active, 1 = Active, -1 = Restricted)

### Table: doctors
- doctor_id: INT, Foreign Key → doctors(id)
- firstname: VARCHAR(20), Not Null
- lastname: VARCHAR(20), Not Null
- midname: VARCHAR(20)
- dob: INT, Not Null, (YYYYMMDD)
- address: VARCHAR(30), Not Null
- zip: INT, Not null
- speciality: VARCHAR(30), Not Null
- weekly_availability_times: [DATETIME], Not Null
- appointments: [INT], Foreign Key -> appointements(id)
- patients: [INT], Foreign Key → patients(id)

### Table: appointments
- id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id)
- patient_id: INT, Foreign Key → patients(id)
- appointment_time: DATETIME, Not Null
- status: INT (0 = Scheduled, 1 = Completed, 2 = Cancelled)

### Unstructure data
We need the following collections / documents for unstructured data: 
*Free-form doctor notes
*Optional patient feedback
*Prescription metadata
*File attachments
*Log records 
