## IntelliCards Architecture

### Sketch

![Intellicards architecture image](https://media.discordapp.net/attachments/1245156917194788969/1246330674344886344/image.png?ex=666296e4&is=66614564&hm=1f81088569fcf13bec05bf39cc6001642c2579cf39afb615951577a9e4944490&=&format=webp&quality=lossless&width=1080&height=424)

### UI/Presentation

- MainActivity
- ProfileActivity
- CreateFlashcardActivity
- EditFlashcardActivity
- ViewFlashcardSetActivity
- CreateFlashcardSetActivity
- RecoverFlashcardActivity

### Business/Logic

- FlashcardManager
- FlashcardSetManager

### Data/Persistence

- FlashcardPersistence
- FlashcardSetPersistence
- ImagePersistence

### Domain Specific Objects

- Flashcard
- FlashcardSet
