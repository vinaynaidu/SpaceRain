# Space Rain

HU's very own bot for slack.

## Installation

* Clojure project.. make an uberjar and `java -jar` it.
* To push to heroku, `git push heroku master` - or the branch that needs to be pushed. Will need to setup heroku in origin


## Supported commands:


| Command | Description | Usage | Notes |
|---|---|---|---|
| `define` | prints the dictionary definition of the term | `/hu define lazy` | |
| `urban` | looks up given word/phrase on urban dictionary and returns random definition | `/hu urban wat` | |
| `pugme` | posts a picture of a pug | `/hu pugme` | |
| `test` | posts back the request object recieved from slack. useful for debugging channel specific info | `/hu test` | |

## Troubleshooting

**Bot not responding?**  
Heroku seems to put inactive applications to sleep. You'll need to wake it up by visiting the bots homepage at [spacerain.herokuapp.com](spacerain.herokuapp.com). When that's done, bot should be responsive again.

**spacerain.heroku.com says application error?**
Application has crashed. You'll need to `cd` into the project dir and run `heroku restart`. That should put things back on track.

**Want to check app logs?**
`cd` into app directory and either `heroku logs` to see latest log or `heroku logs --tail` to see live logging.
