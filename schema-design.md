
## Tables

### Structured data
We need the following tables for structured data:
1. patients 
2. doctors
3. admin
4. appointments
5. clinic_locations
6. payments
7. users_sessions
8. system_activities
9. notifications

### Table: patients
- patient_id: INT, Primary Key, Auto Increment
- username: VARCHAR(20), Not Null
- hashed_pass: VARCHAR(255), Not Null
- email: VARCHAR(20), Not Null
- phone: VARCHAR(15), Not Null
- doctor_id: INT, Foreign Key → doctors(doctor_id)
- firstname: VARCHAR(20), Not Null
- lastname: VARCHAR(20), Not Null
- midname: VARCHAR(20)
- dob: INT, Not Null, (YYYYMMDD)
- address: VARCHAR(30), Not Null
- zip: INT, Not null
- city: VARCHAR(20), Not Null
- state: VARCHAR(20), Not Null
- appointment_id: INT, Foreign Key → appointments(appointment_id)
- status: INT (0 = Not Active, 1 = Active, -1 = Restricted)
- created_at: TIMESTAMP, Not Null
- last_update: TIMESTAMP, Not Null

### Table: doctors
- doctor_id: INT, Primary Key, Auto Increment
- username: VARCHAR(20), Not Null
- email: VARCHAR(20), Not Null
- phone: VARCHAR(15), Not Null
- hashed_pass: VARCHAR(255), Not Null
- firstname: VARCHAR(20), Not Null
- lastname: VARCHAR(20), Not Null
- midname: VARCHAR(20)
- dob: INT, Not Null, (YYYYMMDD)
- address: VARCHAR(30), Not Null
- zip: INT, Not null
- speciality: VARCHAR(30), Not Null
- weekly_availability_times: [DATETIME], Not Null
- appointments: [INT], Foreign Key -> appointements(appointment_id)
- patients: [INT], Foreign Key → patients(patient_id)
- last_update: TIMESTAMP, Not Null

### Table: admin
- admin_id: INT, Primary Key, Auto Increment
- username: VARCHAR(20), Not Null
- email: VARCHAR(20), Not Null
- hashed_pass: VARCHAR(255), Not Null
- firstname: VARCHAR(20), Not Null
- lastname: VARCHAR(20), Not Null

### Table: clinic_locations
- clinic_id: INT, Primary Key, Auto Increment
- phone: VARCHAR(15), Not Null
- address: VARCHAR(30), Not Null
- zip: INT, Not null
- city: VARCHAR(30), Not Null
- doctors: [INT], Foreign Key → doctors(doctor_id)
- created_at: TIMESTAMP, Not Null
- last_update: TIMESTAMP, Not Null

### Table: payments
- payment_id: INT, Primary Key, Auto Increment
- patient_id: [INT], Foreign Key → patents(patient_id)
- payment_date: TIMESTAMP, Not Null
- bill_id: INT, Foreign Key → bills(bill_id)
- payment_method: VARCHAR(20), Not Null
- clinic: INT, Foreign Key → clinic_locations(clinic_id)

### Table: user_sessions
- user_session_id: INT, Primary Key, Auto Increment
- user_id: INT, Foreign Key → ( patients(patient_id) /  doctors(doctor_id) / admin(admin_id) )
- date: TIMESTAMPS, Not Null
- session_location_lattitude: BIG_INTEGER, Not Null
- session_location_longitude: BIG_INTEGER, Not Null
- session_location_city: VARCHAR(30), Not Null
- session_duration_seconds: INT, Not Null
- session_ip: VARCHAR(30), Not Null
- login_attempts_count: INT, Not Null
  
### Table: system_activities
- activity_id: INT, Primary Key, Auto Increment
- user_id: INT, Foreign Key → ( patients(patient_id) / doctors(doctor_id) / admin(admin_id) )
- date: TIMESTAMPS, Not Null
- action: INT (0 = Deleted, 1 = Created, 2 = Updated)
- object_id: INT, Foreign Key → ( patients(patient_id) / doctors(doctor_id) /  appointments(appointment_id) / payments(payment_id) )
- session_id: INT, Foreign Key → user_sessions(user_session_id)
  
### Table: appointments
- id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id)
- patient_id: INT, Foreign Key → patients(id)
- appointment_time: DATETIME, Not Null
- status: INT (0 = Scheduled, 1 = Completed, 2 = Cancelled)

### Unstructure data
We need the following collections / documents for unstructured data: 
1. doctor_notes
2. patient_feedbacks
3. Prescriptions
4. attachments
5. records
6. system_logs


### Collections: doctor_notes
{
  doctorId : {
    type: Number,
    required: true
  },
  patientId : {
    type: Number,
    required: true
  },
  noteTitle:{
      type: String,
      required: true
  },
  note:{
      type: String,
      required: true
  },
  createdAt: {
    ype: Date,
    required: true,
  },
  lastUpdate: {
    type: Date,
    required: true,
  }
}

### Collections: patient_feedbacks
{
  patientId : {
    type: Number,
    required: true
  },
  appointmentId : {
    type: Number,
    required: true
  },
  doctorId : {
    type: Number,
    required: true
  },
  feedbackTitle:{
      type: String,
      required: true
  },
  feedback:{
      type: String,
      required: true
  },
  createdAt: {
    type: Date,
    required: true,
  },
  lastUpdate: {
    type: Date,
    required: true,
  }

}

### Collections: prescriptions
{
  patientId : {
    type: Number,
    required: true
  },
  appointmentId : {
    type: Number,
    required: true
  },
  doctorId : {
    type: Number,
    required: true
  },
  prescriptions: {
    medecines:{
      type: Array
    }
    interval: { 
      type: String,
      required: true
    }
  }
  createdAt: {
    type: Date,
    required: true,
  },
  lastUpdate: {
    type: Date,
    required: true,
  }
}

### Collections: attachments
{
  attachmentId : {
    type: Number,
    required: true
  },
  attachmentName: {
    type: String,
    required: true
  },
  attachmentLocation: {
    type: String,
    required: true
  },
  appointmentId : {
    type: Number,
    required: true
  },
  doctorId : {
    type: Number,
    required: true
  },
  createdAt: {
    type: Date,
    required: true,
  },
  lastUpdate: {
    type: Date,
    required: true,
  }
}

