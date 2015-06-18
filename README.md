# SimpleReminder
A simple reminder application for android.
Basically the application lets the user insert a reminder which reminds him in a specific date and time (which the user inserted)
about it.
The application takes a headline, date and time, description, and whether it is one-time reminder or on a daily basis.
It saves it in a database through SQLITE by the "insert" command. And simultaneously opens a schedule task thats builds and shows
a notification according to the date and time provided by the user.
The application also has a ui which shows all the reminders that are scheduled in the future ordered by the closest reminder to
be scheduled to the far-more reminder. It lets the user update or delete a specific reminder (By the 'delete' and 'update'
commands in SQL) and schuedule the notification accordingly.


*In the 'src' folder you can find all the java code divided by packages.
*In the 'res' folder you can find all the xml layouts that build the UI, Including all the icons and custom buttons.
