## IntelliCards Architecture

### Sketch

![Intellicards architecture image](blob:https://web.telegram.org/cd321987-9b5b-4f14-ab06-9246b3d628c7)

### UI/Presentation

- CardRecoverActivity
- CardViewAdapter
- EditFlashcardActivity
- FlashcardSetActivity
- MainActivity
- ProfileActivity
- RecoverFlashcardActivity

### Business/Logic

- FlashcardManager
- FlashcardSetManager
- StubManager 

### Data/Persistence

- FlashcardPersistence
- FlashcardPersistenceStub
- FlashcardSetPersistence
- FlashcardSetPersistenceStub

### Domain Specific Objects

- Flashcard
- FlashcardSet

*Note: Stubs are temporary for Iteration 1.*
