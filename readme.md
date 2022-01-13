Currently, this API only supports one endpoint with an integer parameter.
To use it, you send a get request to the endpoint with a number between 1 and 52.
The API will then generate a number of cards with other properties of the deck relating
to the card game poker and return the json object. It will also write a log to a database on MongoDB Atlas.

It was built with the Scala Play framework and the deck of cards api
at https://www.deckofcardsapi.com, as well as the MongoDB Scala Driver.

### How to use:

This API is designed to generate and interpret a hand of n cards according
the rules of Poker. For those unfamiliar with poker, a poker hand consists of 5
cards, with some hands containing special patterns being stronger than others.

For the purpose of this API, only a couple hands are relevant:

- High Card: A hand with no other special patterns, the highest card is compared.
- Pair/Triple(Set)/Quads: A hand with 2/3/4 of the same value of card.
- Straight: A hand with 5 cards in consecutive order (eg. Ace, 2, 3, 4, 5)
- Flush: A hand with 5 cards, all of the same suit.
- Full house: A hand with a triple and a pair

The API then generates an object with the hand and fields for each of the poker hand types.
For the special pattern fields (Straight, Flush, and Full house), The API iterates through the set of n cards and detects if a hand of 5 cards with
the special pattern listed above is present and adds it as an attribute. For the
Pairs/Triples/Quads field, it counts the number of Pairs/Triples/Quads, allowing each card to be used only once.
For the high card field, it outputs the highest card in the hand.

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

To get the last log sent MongoDB, execute `./getMongoRecords.sh`
The output log will have the request type, parameter, and output.  

Note that you will need to have `mongosh` installed.