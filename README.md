# 🩺 Health Heroes (HH) - Doctor Drug Assistance Tool

## 📌 Overview
Health Heroes is a comprehensive tool designed for doctors to manage, monitor, and suggest medications for their patients. By leveraging the Chat GPT API, Health Heroes offers advanced conflict and side-effect detection, as well as valuable insights for unique circumstances.

For a quick overview of this project:
https://docs.google.com/presentation/d/1QXVTlIKvhEGb74_8joQ4K3Q-6lx0xEQx7bAUkNJo6fc/edit?usp=sharing 


## 🌐 Domain

**Doctor Drug Assistance Tool**

### 🛠 Software Specification

This tool enables doctors to:
- 📋 Manage medications of their patients.
- ⚠️ Identify and avoid medication conflicts.
- 🔍 Monitor side effects and pinpoint their causes.
- 💡 Match medications with patient symptoms.
- 🤖 Utilize Chat GPT API for advanced warnings in unique situations.

## 📖 User Stories

1. 👩‍⚕️ Doctors can identify conflicts when prescribing new medication to patients already on multiple medications.
2. 💵 Patients in the US can find affordable medicine alternatives, like generics.
3. 🤔 Patients can check if any of their symptoms arise from a specific medication.
4. 📥 Patients can submit symptoms to find matching medications, filtering out prescription-only options.
5. 🍺 Doctors can identify conflicts between a patient's recreational drug use (like alcohol) and their medication.
6. 🚨 Alerts for doctors about newly approved medications that match their patients' needs.

## 🚀 Team Story Example

Input:
- 🙋‍♂️ Patient Name: William Zhang
- 📏 Height: 160 cm
- ⚖️ Weight: 190 pounds
- 🎂 Age: 22
- 📝 Lifestyle Information: (To be filled by doctor during interview)
- 🍻 Recreational Drugs Taken: Alcohol (Beer, Daily, 6 units)

Output:
- 📊 BMI: 33.6 (Obese)
- ⛔ [WARNING] Patient has a history of substance abuse and may abuse the prescribed drug(s)

## 📑 Entities

### 🙍‍♂️ Patient Data
- 💊 Current medications
- 🔒 Account and password

### 👩‍⚕️ Doctor Account
- 🆔 Username
- 🔐 Hashed password
- 📜 Current patients (list of Patient objects)

### 📋 Medical Information
- 📜 Medical history
- 🚫 Known allergies

### 🌡 Medical Condition
- 💊 Associated medications (list)
- 📅 Start date

### 💊 Medication
- 🆔 Medication ID
- 📏 Dosage
- 📅 Start and end dates
- 🔄 Similar medications pending approval
- 🤒 Symptoms
- 🍻 Potential lifestyle conflicts
- ❗ Prominent side effects
- 📝 Doctor comments

### 🙋‍♂️ Patient Instance
- 🆔 Patient ID
- 📛 Name
- 🎂 Age
- ♂️ Gender
- 📋 Medical information

## 📞 Communication & Meetings

- 📅 Meeting Time Outside of Lab: Friday 4:00pm / Sunday 11:00am
- 💬 Mode of Communication: Discord Group Chat
