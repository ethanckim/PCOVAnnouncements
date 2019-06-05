const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

/**
 * Triggers when a new document has been write in the 
 * Firestore Announcements collection.
 */
exports.sendNewAnnouncementNotification = functions.firestore
    .document('Announcements/{date}')
    .onCreate(async (snap, context) => {
        const notcontext = snap.data.context;
        const nottype = snap.data.type;
        const notdate = snap.data.date;

        // Get the token from the database/firestore
        const getDeviceTokensPromise = admin.firestore().collection("Tokens").get();
        // The snapshot to the user's tokens.
        let tokensSnapshot;
        // The array containing all the user's tokens.
        let tokens;

        const results = await Promise.all([getDeviceTokensPromise]);
        tokensSnapshot = results[0];

        // Check if there are any device tokens.
        if (!tokensSnapshot.hasChildren()) {
            return console.log('There are no notification tokens to send to.');
        }
        console.log('There are', tokensSnapshot.numChildren(), 'tokens to send notifications to.');

        // Listing all tokens as an array.
        tokens = Object.keys(tokensSnapshot.val());

        //Message Details
        const message;
        if (nottype === 'announce') {
            message = {
                title: '주일 광고 ' + notdate,
                body: notcontext
            }
        } else if (nottype === 'emergency') {
            message = {
                title: '비상 광고: ' + notcontext,
                body: notcontext
            } 
        } else {
            message = {
                title: '광고: ' + notdate,
                body: notcontext
            }
        }

        // Send notifications to all tokens.
        const response = await admin.messaging().sendToDevice(tokens, message);
        // For each message check if there was an error.
        const tokensToRemove = [];
        response.results.forEach((result, index) => {
            const error = result.error;
            if (error) {
                console.error('Failure sending notification to', tokens[index], error);
                // Cleanup the tokens who are not registered anymore.
                if (error.code === 'messaging/invalid-registration-token' ||
                    error.code === 'messaging/registration-token-not-registered') {
                    tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
                }
            }
        });
        return Promise.all(tokensToRemove);

    });
