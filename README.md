
#### Requirements:
- This calendar has only one day. So to make things simple - events have only start and end time (no dates)
- The day starts at 07:00 and ends at 19:00. Take that into consideration when finding available time slots.
- Feel free to go above and beyond if you have ideas for extra features!


### Example
Attached is an example calendar file `calendar.csv`:
```
Alice,"Morning meeting",08:00,09:30
Alice,"Lunch with Jack",13:00,14:00
Alice,"Yoga",16:00,17:00
Jack,"Morning meeting",08:00,08:50
Jack,"Sales call",09:00,09:40
Jack,"Lunch with Alice",13:00,14:00
Jack,"Yoga",16:00,17:00
Bob,"Morning meeting",08:00,09:30
Bob,"Morning meeting 2",09:30,09:40
Bob,"Q3 review",10:00,11:30
Bob,"Lunch and siesta",13:00,15:00
Bob,"Yoga",16:00,17:00
```

For this input, and for a meeting of 60 minutes which Alice & Jack should attend the following output is expected:

```
Available slot: 07:00
Available slot: 10:00
Available slot: 11:00
Available slot: 12:00
Available slot: 14:00
Available slot: 15:00
Available slot: 17:00
Available slot: 18:00
```

### Must Give high Attention to Code Quality:
Please address the following aspects in your code:
- Clean coding, if you're familiar with [SOLID Principals](https://en.wikipedia.org/wiki/SOLID) try to work by it as much as possible.
- Meaningfull naming for your classes and APIs.
- Clear and understood relationship between your classes.
- Object oriented design
- Error handling
- Validations
- logging (System.out.print is good enough as well)
- Testing, Testing Testing
 
### Getting Started

You will need [Maven](https://maven.apache.org/) installed to run the commands below.

You will have to run `mvn clean install` inside the directory to download and install required dependencies.

We have created the application's entry point for you. The entry point file is `src/main/java/io/daily/App.java`.
To execute the app, you can run `mvn compile exec:java`

