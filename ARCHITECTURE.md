## IntelliCards Architecture

### Sketch

![Alt text](images/Architecture_Diagram.png)

### Domain Specific Object Layer

- Flashcard: Object to create, view, and store flashcards (includes question, answer, and hint fields). 
- FlashcardSet: Object to create, view, and store sets of flashcards.
- User: Object to create and view accounts, each with their own username, password, sets of flashcards, and test statistics.

### Application Layer

- Configuration: Object which configures the type of the database being used.
- Services: Object which instantiates all databases (HSQLDB).
- UserSession: Object which instantiates the user session type (either a specific account or guest).

### Presentation Layer

- AboutMeActivity: A page displaying a summary of the flashcards and flashcard sets owned by the user, and their accuracy report based on completed tests. 
- AuthActivity: Login page for users to create an account, enter existing credentials, or continue as a guest.
- CardRecoverAdapter: A display for individual cards to be recovered.
- CardViewAdapter: A display for individual cards to be edited and deleted.
- CreateFlashcardActivity: Flashcard creation page, where a user can input a question, answer, and optional hint for a new card.
- EditFlashcardActivity: Flashcard editing page, where a card's existing data can be changed, including its set.
- FlashcardSetActivity: Flashcard set display page, where a user can view all the cards in a set, and initiate a "test" of all cards.
- FlashcardTestActivity: Testing page to review all flashcards in a set. The user must mark a card as answered correctly or incorrectly to continue, but testing can be stopped at any time by selecting "finish".
- FlashcardUtils: Manages the flaschard utility.
- MainActivity: Home page for viewing all sets and accessing the profile page.
- NotificationReceiver: Uses the NotificationManager to show a notification. 
- ProfileActivity: Profile page which allows users to recover deleted cards and to log out.
- RecoverFlashcardActivity: Display page for all deleted cards.

### Logic Layer

- FlashcardManager: Manages insertion of new cards into persistence data, and retrieval of stored cards to display.
- FlashcardSetManager: Manages insertion of new sets into persistence, insertion of individual cards into sets, and retrieval of sets to display.
- NotificationManager: Manages creation of notifications and display them on set day and time. 
- ReportCalculator: Gathers the "all time" statistics of the total tests for a particular flashcard set.
- TempTestResult: Temporarily stores the test results of one test session. 
- UpdateFlashcardService: Updates edited flashcards
- UserManager: Manages insertion of new accounts into persistence data, and retrieval of existing accounts.

### Persistence Layer

- FlashcardPersistence: Interface for the flashcard database.
- FlashcardPersistenceHSQLDB: Implementation of the flashcard database, using HSQLDB.
- FlashcardSetPersistence: Interface for the flashcard set database.
- FlashcardSetPersistenceHSQLDB: Implementation of the flashcard set database, using HSQLDB.
- PersistenceException: The exception to throw when an error occurs in HSQLDB.
- UserPersistence: Interface for the account database.
- UserPersistenceHSQLDB: Implementation of the account database, using HSQLDB.
