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

All of the actions require basic authorization, except registration. If you try to send a request without being authorizied, you will receive a response with status code 401 (Unauthorized).


**Registration**

Send a POST request to **/api/register** with JSON with username (email) and password (email field must contain '@' and '.', and  password must have at least 5 characters):
```
{
  "email": "test@gmail.com",
  "password": "test123"
}
```
Don't worry, there will be saved only encrypted version of your password!


**Creating new quizzes**

Send POST request to **/api/quizzes** with JSON with title, text, options (at least two) and answer (an index of correct option; it can be one, more than one or zero correct options):
```
{
  "title": "The apple",
  "text": "What color can an apple be?",
  "options": ["Green","Blue","Red","Orange"],
  "answer": [0,2]
}
```
then you will receive a response-Json with generated ID of your quiz, and other options you created, except the answer. Now, your quiz is saved in the database.
```
{
  "id": 3,
  "title": "The apple",
  "text": "What color can an apple be?",
  "options": ["Green","Blue","Red","Orange"]
}
```


**Get all quizzes**

Send GET request to **/api/quizzes**, and you will receive JSON with a list of existing quizzes. Each quiz contains fields id, title, text and options:
```
{
    "content": [
        {
            "id": 1,
            "title": "The banana",
            "text": "What color is the banana?",
            "options": ["Green", "Yellow", "Red", "Blue"]
        },
        {
            "id": 2,
            "title": "The orange",
            "text": "What color is the orange?",
            "options": ["Green", "Blue", "Red", "Black"]
        },
        ...
    ]
}
```
However, the response contains the maximum of 10 quizzes. That is because if there is a lot of quizzes, they are divided into pages, and on one page there are max 10 quizzes. By default you receive page no.0, so if you want to see other quizzes (other page), you need to add path variable **page**, eg. http://localhost:8889/api/quizzes?page=2.


**Get quiz by id**

Send GET request to **/api/quizzes/[ID]**, where [ID] is the ID of quiz you want to get (eg. http://localhost:8889/api/quizzes/3). You will receive JSON with fields id, title, text and options. Now you can try to solve it!
```
{
  "id": 3,
  "title": "The apple",
  "text": "What color the apple can be?",
  "options": ["Green", "Blue", "Red", "Orange"]
}
```
If such quiz doesn't exist you will receive a response with status code 404 (Not found).


**Solve quiz**

Send a POST request to **/api/quizzes/[ID]/solve** and pass the **answer** parameter in the content (eg. http://localhost:8889/api/quizzes/3/solve?answer=2). This parameter is the index of a chosen option from options array. If the answer is correct you will receive following message:
```
{"success":true,"feedback":"Congratulations, you're right!"}
```
But if the answer is wrong (wrong answer or wrong amount of correct options) you will receive:
```
{"success":false,"feedback":"Wrong answer! Please, try again."}
```
If such quiz doesn't exist you will receive a response with status code 404 (Not found).


**Delete quiz**

Send DELETE request to **/api/quizzes/[ID]**. If quiz was deleted successfully, you will receive an empty response with status code 204 (No content), if such quiz doesn't exist status code 404 (Not found), or if you are trying to delete a quiz that you are not the author of, status code 403 (Forbidden).


**Get completed quizzes**

Send GET request to **/api/quizzes/completed** to get all the quizzes you've successfully completed. You will receive a response - JSON with field *content* and some more additional information. In *content* there is a list of completed quizzes, but only the IDs and completion date:
```
{
  "totalPages":1,
  "totalElements":5,
  "last":true,
  "first":true,
  "empty":false,
  "content":[
    {"id":103,"completedAt":"2019-10-29T21:13:53.779542"},
    {"id":102,"completedAt":"2019-10-29T21:13:52.324993"},
    {"id":101,"completedAt":"2019-10-29T18:59:58.387267"},
    {"id":101,"completedAt":"2019-10-29T18:59:55.303268"},
    {"id":202,"completedAt":"2019-10-29T18:59:54.033801"}
  ]
}
```
As in the getting all quizzes (*Get all quizzes* section) there are the maximum of 10 quizzes in one response (page), so if you want to see other page, just add **page** parameter in the path (eg. http://localhost:8889/api/quizzes/completed?page=2).
