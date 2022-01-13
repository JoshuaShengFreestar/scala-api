Currently, this API only supports one endpoint with an integer parameter.
To use it, you send a get request to the endpoint with a number between 1 and 52.
The API will then generate a number of cards with other properties of the deck relating
to the card game poker and return the json object. It will also write a log to a database on MongoDB Atlas.

It was built with the Scala Play framework and the deck of cards api
at https://www.deckofcardsapi.com, as well as the MongoDB Scala Driver.

### Installation:
- Clone this git repo into a folder.
- Navigate inside the folder with this repo and run the command `sbt run` in the terminal
- Now that the API is running locally, you can query it by calling
`$curl localhost:9000/scala-api/{number of cards}`, where number of cards is an integer between 1 and 52

Ex: `curl -v localhost:9000/scala-api/1`

Response:

    `{"cards": [{"suit":"D","value":6}],
    "hasStraight":false,
    "hasFlush":false,
    "highCard":6,
    "hasFullHouse":false,
    "pairs":0,
    "triples":0,
    "quads":0}`

### Testing: 
Tests are run using SBT, and are located in the test folder. To run all unit tests, execute the command`sbt test` .