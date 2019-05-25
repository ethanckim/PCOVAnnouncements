const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

/**
 * 
 */
exports.helloWorld = functions.https.onRequest((request, response) => {
    console.log("Hello World!")
    response.send("Hello World!");
});

/**
 * Triggers when a new document has been write in the 
 * Firestore Announcements collection.
 *
 * Followers add a flag to `/followers/{followedUid}/{followerUid}`.
 * Users save their device notification tokens to `/users/{followedUid}/notificationTokens/{notificationToken}`.
 */
// Listen for changes in all documents in the 'Announcements' collection
exports.sendNewAnnouncementNotification = functions.firestore
    .document('Announcements/{date}')
    .onCreate((snap, context) => {
        const context = snap.data.context;
        const type = snap.data.type;
        const date = snap.data.date;
        const position = snap.data.position;

        //TODO Get the token from the database/firestore
        const token =
        //TODO send FCM message

    });
