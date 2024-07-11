## IntelliCards Architecture

### Sketch

![Alt text](<3350 Architecture Design - Iteration 2.png>)

### Domain Specific Objects

- Flashcard: Item to create, view, and store flashcards (includes question, answer, and hint fields). 
- FlashcardSet: Item to create, view, and store sets of flashcards.

### UI/Presentation

- CardRecoverAdapter: A display for individual cards to be recovered.
- CardViewAdapter: A display for individual cards to be edited and deleted.
- EditFlashcardActivity: Display for users to edit an existing card.
- FlashcardSetActivity: Display to browse all flashcards in an individual set.
- MainActivity: Creation display for new cards, and main page for all sets.
- ProfileActivity: Account viewer which allows users to access certain features, like deleted card recovery.
- RecoverFlashcardActivity: List display for all deleted cards.

### Business/Logic

- FlashcardManager: Manages insertion of new cards into persistence data, and retrieval of stored cards to display.
- FlashcardSetManager: Manages insertion of new sets into persistence, insertion of individual cards into sets, and retrieval of sets to display.
- StubManager: Temporary card and set manager for stub persistence data.

### Data/Persistence

- FlashcardPersistence: Interface for future flascard database.
- FlashcardPersistenceStub: Temporary stub database for initial flashcards.
- FlashcardSetPersistence: Interface for future flashcard set database.
- FlashcardSetPersistenceStub: Temporary stub database for initial flashcard sets.

*Note: Persistence stubs are for Iteration 1 only.*
