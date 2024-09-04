# ClothesMarket - Second Hand Clothes Selling Application

Welcome to ClothesMarket! This project is a fully functional second-hand clothes selling application that demonstrates my skills in Android development, using modern libraries and architectures to build a scalable, maintainable, and user-friendly mobile application.

## Key Features

- **User Authentication**: Secure login and registration using Firebase Authentication, with support for both email/password and Google authentication.
- **Real-time Data Storage**: Seamless data management using Firebase Firestore for storing user details, clothes listings, and real-time updates.
- **Efficient Data Binding**: Utilizes Android's View Binding and Data Binding libraries for efficient UI updates, reducing boilerplate code.
- **MVVM Architecture**: Implements the MVVM (Model-View-ViewModel) architectural pattern, ensuring separation of concerns and maintainable code.
- **Room Database**: Local data storage using Room for offline capabilities, allowing users to access cached data when they are not connected to the internet.
- **Custom Recyclerviews with CardView**: Designed beautiful and responsive recyclerviews to display items dynamically, with custom card views for enhanced user experience.
- **Real-time Messaging**: LiveData and Firebase are used for real-time user-to-user messaging within the app.

---

## Table of Contents

- [Installation](#installation)
- [Technologies Used](#technologies-used)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Contributions](#contributions)
---

## Installation

Follow these steps to set up the project locally:

1. Clone the repository:
   ```bash
   git clone https://github.com/Eminemvarun/clothesmarket.git
2. Open the project in Android Studio.
3. Sync the Gradle files and ensure all dependencies are installed.
4. Set up your Firebase project and integrate Firebase Authentication and Firestore.
5. Build and run the app on your emulator or Android device.

## Technologies Used
This project makes use of several modern libraries and tools, demonstrating proficiency in building a production-ready Android app. Below are the key technologies used:

- Java: Core language used for Android development.
- Firebase Authentication: Secure authentication system supporting Google sign-in and email/password.
- Firebase Firestore: NoSQL cloud database for real-time data syncing.
- Firebase Storage: For storing user-uploaded images related to clothes items.
- Room Database: Local database for offline data storage and caching.
- LiveData: Observable data holder used for real-time updates between the UI and ViewModel.
- View Binding and Data Binding: For efficient UI updates and reducing boilerplate code.
- MVVM Architecture: Ensures clear separation between UI and business logic.
- Custom Recyclerview: Advanced use of recyclerview for dynamic UI rendering of clothes items.
- Gradle: Build automation system.

## Architecture
ClothesMarket follows the MVVM (Model-View-ViewModel) architecture pattern, which allows for better separation of concerns, making the app easier to test, maintain, and extend.

- Model: Responsible for handling the data, including Room database, Firebase Firestore, and Firebase Authentication interactions.
- View: The user interface components, including activities and fragments, which display the data.
- ViewModel: Acts as a bridge between the Model and View, using LiveData to provide data updates and business logic to the View.

## Project Structure
The project is organized into well-structured packages following Android best practices:

```
app/
├── java/
│   └── com.clothesmarket/
│       ├── activities/           # Contains all Activity classes
│       ├── adapters/             # Custom adapters for RecyclerViews
│       ├── database/             # Room database classes and DAO interfaces
│       ├── models/               # Data models (User, ClothesItem, etc.)
│       ├── repositories/         # Data repositories (Firebase, Room)
│       ├── utils/                # Utility classes and helper functions
│       └── viewmodels/           # ViewModel classes for each activity/fragment
├── res/
│   └── layout/                   # XML layout files for UI
├── build.gradle                  # Project-level Gradle config
├── settings.gradle               # Settings for the Gradle build system
```



## Contributions
Feel free to fork the repository and submit pull requests. If you encounter any bugs or have suggestions for new features, please open an issue.

## Skills Demonstrated
By working on this project, I have demonstrated proficiency in:

- Full-Stack Android Development: Developed a complete mobile application using Firebase for backend services and Room for local storage.
- Cloud Integration: Integrated Firebase Authentication, Firestore, and Storage for cloud-based services.
- Efficient UI/UX Design: Implemented custom UI components like Recyclerviews with CardViews and Data Binding for smooth user experience.
- Version Control: Managed the project using Git and GitHub, showcasing collaboration and version control best practices.
- Real-time Data Handling: Utilized LiveData to handle real-time user interactions and data updates seamlessly.

## Contact
Feel free to reach out if you have any questions or suggestions about the project! You can contact me through GitHub or via email at [varunprasad717@gmail.com].
