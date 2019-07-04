const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

/**
 * Triggers when a new document has been write in the 
 * Firestore Announcements collection.
 */
exports.sendNewAnnouncementNotification = functions.firestore
    .document('Announcements/{date}')
    .onCreate(document => {

        //Get the new document's data.
        const announcement = document.data();
        var payload;
        
        if (announcement.type === 'announce') {
            payload = {
                notification: {
                    title: '주일 광고: ' + announcement.date,
                    body: announcement.context
                }
            };
        } else if (announcement.type === 'emergency') {
            payload = {
                notification: {
                    title: '비상 광고: ' + announcement.date,
                    body: announcement.context
                }
            };
        } else {
            payload = {
                notification: {
                    title: '광고: ' + announcement.date,
                    body: announcement.context
                }
            };
        }
        console.log('Announcement Notification sent successfully:', payload);
        return admin.messaging().sendToTopic("Announcements", payload);
    });
