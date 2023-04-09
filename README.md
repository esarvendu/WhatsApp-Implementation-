# WhatsApp-Implementation-
WhatsApp is a messaging service that allows users to create and manage groups, users, and messages. The application is implemented using the User, Message, and Group models.
ote that all-argument constructors should be manually written for User and Group. For Message, the timestamp should be set as the current date.

It has several functionalities that allow users to:

Create a user by providing a name and mobile number. If the mobile number already exists in the database, the application will throw an exception.

Create a group by providing a list of users. The list should contain at least 2 users, where the first user is the admin.

A group has exactly one admin.
A user can belong to exactly one group and has a unique name.
If there are only 2 users, the group is a personal chat, and the group name should be kept as the name of the second user (other than the admin).
If there are 2 or more users, the name of the group will be "Group count".
Note that a personal chat is not considered a group and the count is not updated for personal chats.
For example: Consider userList1 = {Alex, Bob, Charlie}, userList2 = {Dan, Evan}, userList3 = {Felix, Graham, Hugh}. If createGroup is called for these userLists in the same order, their group names would be "Group 1", "Evan", and "Group 2" respectively.
Create a message by providing the message content. The i^{th} created message has message id 'i'. For example if three messages are created, then the IDs of the messages would be 1, 2, and 3, irrespective of their sender and the groups they are sent to.

Send a message by providing the message, sender, and group.

If the mentioned group does not exist, the application will throw an exception.
If the sender is not a member of the group, the application will throw an exception.
If the message is sent successfully, the application will return the final number of messages in that group.
Change the admin of a group by providing the approver, user, and group.

If the mentioned group does not exist, the application will throw an exception.
If the approver is not the current admin of the group, the application will throw an exception.
If the user is not a part of the group, the application will throw an exception.
If all the conditions are met, it will change the admin of the group to "user" and return "SUCCESS". Note that the admin rights are transferred from the approver to the user in this case.
Remove a user from a group by providing the user.

If the user is not found in any group, the application will throw an exception.
If the user is found in a group and is the admin, the application will throw an exception.
If the user is not the admin, the application will remove the user from the group, remove all its messages from all the databases, and update relevant attributes accordingly.
If the user is removed successfully, the application will return (the updated number of users in the group + the updated number of messages in the group + the updated number of overall messages across all groups).
Find messages by providing a start date, end date, and K.

It will find the Kth latest message between the start and end (excluding start and end).
If the number of messages between the given time is less than K, the application will throw an exception.
The controller and models have been defined for the application. 
implemented the service layer and repositories (using hashmaps).
