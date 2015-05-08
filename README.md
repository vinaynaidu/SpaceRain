# Space Rain

HU's very own bot for slack.

## Installation

* Clojure project.. make an uberjar and `java -jar` it.
* To push to heroku, `git push heroku master` - or the branch that needs to be pushed. Will need to setup heroku in origin


## Supported commands:


| Command | Description | Usage | Notes |
|---|---|---|---|
| `define` | prints the dictionary definition of the term | `/hu define lazy` | |  
| `pugme` | posts a picture of a pug | `/hu pugme` | |   
| `test` | posts back the request object recieved from slack. useful for debugging channel specific info | `/hu test` | |  
