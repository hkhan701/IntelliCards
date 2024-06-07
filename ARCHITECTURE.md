## IntelliCards Architecture

### Sketch

![Intellicards architecture image](https://media.discordapp.net/attachments/1245156917194788969/1248630534700007546/IMG_1524.jpg?ex=66645d4d&is=66630bcd&hm=54d22e0ea9985fba234c79729bb89e0e4bf59a5cbcdbd2b64d510fd6fe4026df&=&format=webp&width=1080&height=423) 

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
