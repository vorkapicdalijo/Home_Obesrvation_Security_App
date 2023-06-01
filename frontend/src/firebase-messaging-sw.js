importScripts('https://www.gstatic.com/firebasejs/8.0.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.0.2/firebase-messaging.js')

// The object we pass as an argument is the same object we copied into the environment files
firebase.initializeApp({
  projectId: 'hosapp-338fb',
  appId: '1:975876020999:web:fafe8dac3661c3de1c83a2',
  storageBucket: 'hosapp-338fb.appspot.com',
  apiKey: 'AIzaSyDkUgPok5NxOMogufWGbZmpqoBdGjMBAyE',
  authDomain: 'hosapp-338fb.firebaseapp.com',
  messagingSenderId: '975876020999',
})

const messaging = firebase.messaging();
