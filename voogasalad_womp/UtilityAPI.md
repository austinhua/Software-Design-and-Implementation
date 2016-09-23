# Utility API - Help Module Chat Bot

## [API](#api)
All `POST` and `PUT` requests expect a JSONObject with the `Content-Type` request header set to `application/json`.

##### Failure
All routes return a JSONObject with the following fields if an error is encountered.

| Field   | Type     | Description                  |
| ------- | -------- | ---------------------------- |
| `error` | `string` | A useful diagnostic message. |

Example error:
```
{
	"error": "This is why your request failed."
}
```

### [`Learning`](#learning)
##### [Learning object](#learning-object)
A `Learning` JSONObject with the following fields:

|   Field               | Type           | Description
| --------------------- | -------------- | ---------------------------------------------------------------------- |
|   `game_name` 				| `string`      |    `name of the game being played`                                                                      |
|   `question` 			| `string`      |     `sample question to be asked`                                                                     |
|   `response` 				| `string`      |    `sample response given`                                                                                                  |                                                                  

#### [`/learning/:game_name`](#learning)
##### GET

Returns an array of [`Learning`](#learning-object) objects for the specified game name.

##### POST
Expects a [`Learning`](#learning-object) JSONObject. Missing keys will take on `null` or default values. Adds learning object to the specified game's model.

Returns the created [`Learning`](#patient-object) JSONObject.

Example request:
```
{
	"name": "womp",
	"question": "How do I add towers?",
	"response": "Click the tower and click on the field."
}
```

##### DELETE
Returns all the deleted [`Learning`](#patient-object) objects for the specified game.

#### [`/predict/:game_name?question='given_question'`](#learning)

##### GET

Returns a String that would be the modeled response to the given_question String provided in the parameters of the get request.
