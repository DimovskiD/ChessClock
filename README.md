## ChessClock

**Note: The project requires Java 11 to run**

This sample project implements a simple chess clock application that can be used when playing different types of chess games.  To speed things up in chess, players use chess clocks. Earlier it was common to use analog clocks, but now you can easily turn your phone into a chess clock. Before starting, the players choose the game they want to play. Each game has a specific duration and possibly an increment. The duration (per player) indicates how much time each player will have to make his/her moves. The increment (if set) increases the total time of the user for every move, adding to the total time. 
Chess Clock app is utilizing the MVVM architecture pattern, combined with clean code principles. What follows is a short walkthrough to explain the project in more detail:

The project consists of two modules:
- app 
	- Contains the framework and presentation logic
- core
	- Contains the business logic

**Core**

It is conisted of three packages, data, repository and usecases. Under **data** we define the models needed for the game. A ChessGame consists of two players, it has several states and defines the duration of the game and the increment (if any). The behaviors for starting, switching between players, pausing, resuming and stopping the game are defined in the model. The **repository** provides a way to communicate with the data, while the **use cases** provide a nice way to invoke different operations over the data.


**App**

Separated in two main packages - **framework** and **presentation**, this module is responsible for handling everything Android-related and presenting the data from the core module. This module in turn is dependend on the core module, while the core itself is stand-alone.

The **data** package implements the corresponding data package from the core module, providing an implementation of the ChessDataSource (in this case the data is stored in a local Room database). The ChessGameEntity is the corresponding entity class that helps us transform the ChessGame objects in order to be able to store them locally.  AddChessGame and ChessGamePlaceholders are merely dummy classes that are used for presentation purposes. In order to provide the user with a starting point, the application's database is pre-populated with popular chess game formats, by using an asset file.
**DI** contains all the necessery elements for performing dependency injection using Dagger2 (modules + components). 
**ChessViewModel** is the 'connector' between the view and the data layer. It resides within the viewmodel package. The **UseCases** class is a utility class for providing a simpler way of injecting the use cases defined in the core module.

Over to the **presentation** package, where we present the data to the user. This represents our view layer of the MVVM structure.  You can find your adapters, fragments and activities here, as well as a few custom widgets, listeners and utility functions. Due to the simple nature of this app, it only contains one activity, which in turn presents three different fragments (one for listing the chess games, one for adding/editing and one for the in-game mode). The navigation between these fragments is defined in a nav_graph, by utilizing Android Jetpack's **navigation component**.  
I'd like to point out the ChessGameAdapter. For the purpose of creating a more unique UI, the adapter is a bit (unnecessarily) complicated, by adding the two extra view holders for adding a new game and the placeholder to 'finish' the chess UI layout if needed. By using the dummy classes mentioned earlier, the adapter can add these custom views, making it more useful than a regular adapter (but also giving the adapter more responsibilities, which we usually want to avoid).

The XML resources are inflated by using view binding, while at times we also make use of the data binding feature (i.e. to show/hide/change buttons based on the game state in ChessGameFragment).
