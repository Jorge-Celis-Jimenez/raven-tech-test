# Raven test by Jorge Celis

This test involves the creation of a list of new taken from hacker news.

## Installation

1.- Clone project.

2.- Execute gradle sync.

## Requirements

### Functional Requirements
- Display a list of new taken from from hacker news.
- News should be refreshed when the user opens the app. 
- If the app is used offline, news previously fetched should be displayed.
- News can be deleted individually and must be no longer available even if news data is refreshed. 
- News content should be displayed if the user tap on a specific new. 

### Non-Functional Requirements
- News are taken from: https://hn.algolia.com/api/v1/search_by_date?query=android
- News detail view should include a webview. 

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)
