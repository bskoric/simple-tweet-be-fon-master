# Simple-tweet

**Simple tweet** is application created like student's project at **University of Belgrade, Faculty of Organizational Sciences**, 
for exam **Alati i metode softverskog inzenjerstva**.

Professor: Dragan Đurić

Student: Branko Skorić 

## Description
**Simple tweet** is very simple version of Twitter application, social networking service on which users post and 
interact with messages known as "tweets". 

Users can see only post from people who are their friends, namely from the people whom they mark as friend (follow).
User can search for friends, add new friends, remove existing friends.

Regarding tweets, user can read friend's tweets, insert his own tweets, change existing ones.
Each tweet can be liked, by clicking on the heart button on the tweet. Also by clicking second time, tweet can be unliked.
Each tweet will display number of likes.

Last but not least, user have to register, login, in order to use application.
Also, user can change his data and passoword, through appication.

## Architecture

Application using MySQL database.

Simple tweet uses 'headless' architecture, that means that has two side of application, Frontend part and Backend part.

Backend part of application has written in **Clojure**, API calls are created, so many FE client can use those API calls.
For creating APIs, with Clojure are used compojure, http-kit and ring middleware. Timbre library is used for logging purpose.
JDBC is used for conneting to database, as well as crypto-password and Base64 for decode and encode passwords.  

Frontend part is impleneted in JavaScript library, **React.js**.

