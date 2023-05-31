import { environment } from './environments/environment';

importScripts('https://www.gstatic.com/Firebasejs/8.7.0/Firebase-app.js')
importScripts('https://www.gstatic.com/Firebasejs/8.7.0/Firebase-messaging.js')

// The object we pass as an argument is the same object we copied into the environment files
Firebase.initializeApp(environment.firebase)

const messaging = Firebase.messaging();
