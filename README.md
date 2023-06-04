# Home Security Application

This project consists of a frontend and backend application for a comprehensive home security system. The frontend app is developed with Angular, while the backend app is a Spring Boot application. The application seamlessly communicates with an IoT platform to receive real-time images of any potential intruders on the property. Intruders are efficiently detected through a network of devices equipped with high-quality cameras and ESP32 modules, which establish a secure connection with the IoT platform.

## Features

- **Real-time Image Monitoring**: Stay connected to your home security system with instant access to images captured by the surveillance devices.
- **Intruder Detection**: Utilize advanced image processing algorithms to identify potential intrusions on your property, providing peace of mind and enhanced security.
- **Firebase Push Notifications**: Enhance user engagement and security by utilizing Firebase Messaging Service to send push notifications to users. To enable this feature, users will need to provide their Firebase tokens.

## Getting Started

To run the backend application, follow these steps:

1. Clone this repository to your local machine.
2. Navigate to the backend directory.
3. Open the `application.properties` file and provide the necessary values to configure the application according to your environment, including the Firebase tokens for push notifications.
4. Run the backend application using your preferred Java development environment or build tools.

To run the frontend application, follow these steps:

1. Navigate to the frontend directory.
2. Install the required dependencies by running the command `npm install`.
3. Open the `src/environments/environment.ts` file and provide the necessary values, such as the backend API endpoint.
4. Build the Angular app by running the command `ng build`.
5. Serve the app locally by running the command `ng serve`.

Please note that the backend application is developed using **Java 8** with Spring Boot, and the frontend application is developed using **Angular**.

Feel free to explore the frontend and backend directories for more detailed instructions and additional resources.

## Contributing

Contributions are welcome! If you have any ideas or suggestions to enhance this project, please feel free to submit a pull request or open an issue.

## License

This project is licensed under the [MIT License](link-to-your-license-file).

Enjoy a secure and reliable home security system powered by cutting-edge technology!

[Add any relevant badges or shields here, such as build status, version, etc.]

Feel free to customize this README to your liking and add any additional sections or information that you find relevant.
