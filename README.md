**Web Quiz Engine**

Simple web quiz engine, made under the supervision of JetBrains Academy (https://hyperskill.org/projects/91?track=1). To see how it works you can use e.g. Postman application.


Key Words : JPA, H2 database, REST API, Spring framework (Boot, Security, Web, MVC), Gradle, JSON


The quiz engine supports following operations:
* Registration
* Creating new quizzes
* Displaying all quizzes (with paging)
* Displaying quiz by its ID
* Deleting quizzes
* Solving quizzes
* Displaying completed quizzes

All of the actions require basic authorization, except registration.

**Registration**
Send a POST request to /api/register and JSON with username (email) and password:
```
{
  "email": "test@gmail.com",
  "password": "test123"
}
```

**Creating new quizzes**
Send POST request to /api/quizzes and JSON with title, text
```
{
  "title": "The apple",
  "text": "What color can an apple be?",
  "options": ["Green","Blue","Red","Orange"],
  "answer": [0,2]
}
```
