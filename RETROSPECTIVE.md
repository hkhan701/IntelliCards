## IntelliCards Retrospective

### Project Improvements

- Discuss a part of your project that has not been as successful as you would have liked, and how it can be improved in this iteration 

A part of our project that hasn't been as successful as we'd hoped is the amount of coupling in the application. Initially, it seemed easier to continuously add features and link them together. However, as we enter iteration 3, the complexity of this approach has become apparent. Changes to one class affect other classes, leading to a snowball effect of modifications. This has introduced more bugs and extended our debugging time. Implementing new features has become more time-consuming, making the need for refactoring clear.

We can address this issue by implementing a design strategy that organizes our code, making feature delivery and testing more modifiable. Refactoring should focus on reducing coupling between classes and ensuring that some methods are used strictly within their own class. One approach could be the introduction of design patterns such as Dependency Injection or the use of interfaces to decouple components. By isolating changes to specific areas of the code, we can minimize the ripple effect of modifications, making our application more robust and easier to maintain.

Another area where our project has fallen short is in our work output. During the initial planning, we had high ambitions and aimed to implement more features than originally intended. As we progressed, it became clear that we hadn't considered the structure of our project adequately. For example, implementing features like a small social network where users can share their progress and achievements, along with a comment system for kudos, didn't align well with our project design. These features, while exciting, proved to be unrealistic given our current framework and time constraints.

Through the first and second iterations, we learned the importance of planning before starting a feature, rather than diving in blindly and getting trapped in complicated logic. Each new feature needs to be carefully evaluated not just for its feasibility but also for its alignment with the overall project goals and architecture.

To improve this, we need to ensure ample preparation and planning before each user story, rather than relying on a general idea. Group discussions at the start of each iteration can help us determine if a feature is realistic to implement within the given time. We should discuss how to integrate it into the back-end layer, the logic layer, and the front-end layer. These discussions should include all members of our project, ensuring that every perspective is considered and that the feature can be smoothly integrated across all layers of the application.

One specific area to address is where we have implemented the Facade pattern, such as in the UpdateFlashcardService. By clearly identifying where and how patterns like Facade are used, we can streamline interactions between components and simplify the overall design.

### Improvement Goals and Success Criteria 

- Determine concrete (and realistic) ways of improvement, and decide how its success will be evaluated at the end of the iteration (measurable and objective)

We can evaluate the success of our refactoring by looking at the time estimates for Features 7, 8, and 9 in Iteration 3. The goal is to see how reducing coupling speeds up the integration of new features. Tracking these time estimates will provide concrete data on the effectiveness of our refactoring efforts. If we see a reduction in the time taken to implement new features, it will indicate that our approach is working.

Additionally, we need to refactor our code, particularly in the presentation and logic layers, to conform to the single responsibility principle and pursue low coupling. This means each class should have one and only one reason to change, which simplifies both development and maintenance.

We will measure success by the ratio of classes with a single responsibility compared to all classes. A higher ratio will indicate that we have succeeded in our improvement efforts. Furthermore, we will track the number of bugs reported and the time taken to resolve them. A decrease in both metrics will signify that our refactoring has led to a more stable and maintainable codebase.

We can finally measure the success of our planning by seeing how many user stories go deleted or unimplemented during the implementation process of our project compared to the first 2 iterations. If we have little to no user stories go unimplemented or deleted that means we have succeeded in our goal of planning better for realistic user story implementation.

Overall, these steps will not only enhance our project's current functionality but also pave the way for more sustainable and scalable development in the future.

### Project Velocity Graphs

Here are two charts which show the project velocity - in terms of worked hours and completed user stories - from the two previous iterations.

![Alt text](images/ProjectVelocityTime.png) ![Alt text](images/ProjectVelocityStories.png)

In our first iteration, we overestimated the amount of user stories we would be able to complete. We also overestimated how much time we would be able to work. However, in the second iteration we had a better understanding of our capabilities, and we were able to produce more deliverables overall.
