## PROBLEM 2 - MARS ROVER

#### Why Java?

- I am most comfortable with it right now
- I felt I could easily showcase some OO patterns, TDD and stuff
- I had one evening to do this, and I was setup for it already

#### Why such overkill?

I could have done it all in one long script, or gone overboard with lots of little classes and unit tests, I chose the
latter.

### Building it

inside the same directory as this file

`build.sh`
or
`mvn clean package`

### Running it

still in this directory (and after building it)

`run.sh <input_file>`
or
`java -jar target/marsrovers-1.0-SNAPSHOT.jar -f <input_file>`