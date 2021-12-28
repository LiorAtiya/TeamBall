<h1> TeamBall 🏆</h1>

<h3> TeamBall is a sports team communication and an open-source app for Android devices.</h3>

<br>

<p align="left">
<img src="https://i.postimg.cc/SQtq0FLD/pp.jpg" />
</p>

<br>

<h2> Why should you use our app? :iphone:</h2>

:star: Make new friends and expand your social circle.

:star: Playing team sports is a great way to boost your health and longevity by increasing your physical activity.

:star: Organizing and managing sports team games has never been easier.

<br>

<h2> Architecture & implementaion 👷 </h2>

**It is built with the Android Studio work environment in Java and uses Firebase as its database.**

**The project follows Model-View-Controller design pattern**

<h3> Model: </h3>

The Mode interacts with the database and is responsible for the CRUD (Create, Read, Update and Delete) operations.
It is responsible for holding data, loading it intelligently from an appropriate source, be it disk or network, monitoring changes and notifying the Controller about those, being self-sufficient. As the Model is responsible for loading and syncing the data (like network connectivity, failed updates, scheduling jobs, etc), the Controller should not worry about these conditions.

The Model contains the following classes:
1. PlayerDAL. 
2. RoomDAL.
3. ChatDAL.
4. Player.
5. Room.

<h3> View: </h3>

The view contains the UI and all functionality that directly interacts with the user, like clicking a button, or an enter event.
In the View, there is no logic to be applied, only the ability to display data provided by the Controller.
This makes it easy to unit test and removes the dependence on Android APIs.

The View contains all the activities.

<h3> Controller: </h3>

Manages the system processes by connecting the Model to the View.
Most of the business logic, data manipulation, and display of the data are handled by this component. All logic for the app is present here, making it 100% unit testable. A Controller's responsibilities include fetching data from external sources via the Model, observing changes, and updating the View. In addition, it needs to handle any View interactions that require logic, such as UI triggers causing complex interactions. One notable exception is the ability to launch an Activity by clicking on its button. The action relies completely on Android APIs and does not require any logic. 

The Controller contains the following classes:

1. Game Management

2. Switch Activities.

<br>

<h2> Screenshots </h2>

|                                                                Login Page                                                                 |                                                                   Create Account                                                                   |                                                                    My Profile                                                                    |                                                                  Edit Profile                                                                  |
| :---------------------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------------------------------------------------------------: | :--------------------------------------------------------------------------------------------------------------------------------------------: |
| <img width="186" alt="login" src="https://user-images.githubusercontent.com/73709686/147588110-4de4ad72-d1d0-49d7-b0d9-6d4497d16cb1.PNG"> | <img width="186" alt="create account" src="https://user-images.githubusercontent.com/73709686/147588285-770dc5ed-14fe-4d0f-8169-641d8a31d7ad.PNG"> | <img width="186" alt="edit profile" src="https://user-images.githubusercontent.com/73709686/147591698-84ebd88d-8aef-4189-9d49-3b7bf7711211.PNG"> | <img width="186" alt="my profile" src="https://user-images.githubusercontent.com/73709686/147591564-9cd4ff06-c13e-4fcc-865a-d4ebf5934dd4.PNG"> |

|                                                                  Sport Menu                                                                   |                                                                   Slide Menu                                                                   |
| :-------------------------------------------------------------------------------------------------------------------------------------------: | :--------------------------------------------------------------------------------------------------------------------------------------------: |
| <img width="186" alt="game menu" src="https://user-images.githubusercontent.com/73709686/147591591-7b9a85cd-f308-4094-a9fd-30afb0cf2be1.PNG"> | <img width="186" alt="slide menu" src="https://user-images.githubusercontent.com/73709686/147594707-aa243ffd-65f3-4519-a94c-7e806f284b12.PNG"> |

|                                                                Game Rooms                                                                 |                                                                   Create Room                                                                   |                                                                   My Room                                                                   |                                                                   Chat                                                                   |
| :---------------------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------------------------------: | :--------------------------------------------------------------------------------------------------------------------------------------: |
| <img width="186" alt="rooms" src="https://user-images.githubusercontent.com/73709686/147591633-0d330aef-c27e-4fa8-8e6f-cbfbd5ba40c5.PNG"> | <img width="186" alt="create room" src="https://user-images.githubusercontent.com/73709686/147591615-dcff6b37-59d0-45cc-9222-63a3b78667ee.PNG"> | <img width="186" alt="my room" src="https://user-images.githubusercontent.com/73709686/147591662-66b6f98a-655c-4bd6-b7f1-0c3cb48be35d.PNG"> | <img width="186" alt="chat" src="https://user-images.githubusercontent.com/73709686/147591714-0d4e0e7c-5784-4a01-8608-79b24e6c3077.PNG"> |

<br>

<h2> Run the project and contribute :arrow_down: </h2>
Please let us know if you have any feedback or comments about our Android app.  
Those who want to dive even deeper and do some work on our code are more than welcome to submit a pull request through our Github.

<br>

To get the code running just follow the following steps:

1. [Clone our GitHub repository to get the code](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository)
2. [Install your Android Development Environment](https://developer.android.com/topic/google-play-instant/getting-started/instant-enabled-app-bundle)
3. [Import the TeamBall Android Open Source Project into your IDE](https://developer.android.com/studio/projects/create-project.html#ImportAProject)
4. [Use Gradle Task: install_debug to run the code](https://developer.android.com/studio/run/index.html#gradle-console)

<br>

<h2> Developers :computer: </h2>

|                                                                 Lior Atia                                                                  |                                                      Ofir Ovadia                                                      |                                                      Lioz Akirav                                                      |
| :----------------------------------------------------------------------------------------------------------------------------------------: | :-------------------------------------------------------------------------------------------------------------------: | :-------------------------------------------------------------------------------------------------------------------: |
| <img width="230px" alt="lior" src="https://i.ibb.co/518851r/147595067-1e24da2c-60e1-4cd4-9b8d-c5fc42bf550b-1-1.png"> | ![ofir ovadia](https://i.ibb.co/82QfLMW/147595172-f46d6c55-3a23-46bc-9f2b-a023e8f691d6.png) | ![Lioz Akirav](https://i.ibb.co/8DcPJcp/147595777-5e237203-7eee-4c11-b680-edda12b83979.png) |
