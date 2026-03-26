# Travel-Companion-App

The Travel Companion App is a mobile application developed using Java and XML in Android Studio to assist users with quick and convenient travel-related conversions. It allows users to convert values across multiple categories such as Currency, Fuel Efficiency, Temperature, and Distance through a simple and user-friendly interface.

## Overview

The application is designed to make everyday travel calculations easy and accessible. Users can select a conversion category, choose source and destination units from dropdown menus, enter a value, and instantly view the converted result.

## How It Works

- The user selects a **conversion category**
- The app dynamically updates available **units** based on the selected category
- The user enters a **numeric value**
- On clicking the **Convert** button, the app processes the input using predefined formulas
- The result is displayed instantly on the screen

## Implementation

- **Frontend (XML):**  
  The user interface is built using layouts and components such as Spinner, EditText, Button, and TextView.

- **Backend (Java):**  
  The application logic is handled in `MainActivity.java`, including user interaction handling, dynamic spinner updates using ArrayAdapter, and conversion calculations.

- **Resources (strings.xml):**  
  All categories and unit options are stored in string arrays for better organization and reusability.

## Features

- Multiple conversion categories in a single app  
- Dynamic dropdown updates based on user selection  
- Simple and clean user interface  
- Accurate conversions using fixed formulas  
- Input validation and error handling to prevent crashes  

## Conclusion

This project demonstrates the fundamentals of Android development, including UI design, event handling, adapter usage, and logical processing. It provides a practical example of building a functional and interactive mobile application.
