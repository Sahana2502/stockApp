# Stocker

An Android app that uses the [Stock Quote API](https://71iztxw7wh.execute-api.us-east-1.amazonaws.com/interview/favorite-stocks) to display popular [stock tickers](https://www.investopedia.com/terms/s/stocksymbol.asp).

The app is composed of two screens. The first screen displays a list of stocks, in which each stock is described by the symbol, company name and current stock price. Once a particular stock is selected from the list, a second screen appears displaying additional details about the stock, including the lowest and highest price of the stock on that day.

## Screenshots

<p float="left">
  <img src="https://user-images.githubusercontent.com/89879294/132279233-f4815d6d-906c-462f-946b-84232964e6e4.png" width="300" height="600"/> 
  &nbsp &nbsp &nbsp &nbsp
     <img src="https://user-images.githubusercontent.com/89879294/132056404-098db232-a034-4f17-bddd-27e8d776a360.png" width="300" height="600"/>
    &nbsp &nbsp &nbsp &nbsp
     <img src="https://user-images.githubusercontent.com/89879294/132057208-7cdd3aee-b088-49d3-8498-00ae51bdcde3.png" width="300" height="600" />
</p>


## Overview

The app does the following:

1. Fetch the stock data asynchronously from [Stock Quote API](https://71iztxw7wh.execute-api.us-east-1.amazonaws.com/interview/favorite-stocks)
2. Display the data.
3. Display details of a particular stock selected by user.
4. Poll the API for every 10 seconds and update the UI with latest stock value on both the screens(listActivity and detail activity)
5. In case of network failure/errors, an alert is displayed.


To achieve this, there are four different components in this app:


1. `Stock` - Model object responsible for encapsulating the attributes for each individual stock
2. `StockAdapter` - An adapter class for mapping each `Stock` to a particular view layout
3. `StockListActivity` - An activity for fetching and deserializing the data, configuring the adapter.
4. `StockDetailActivity` - An activity for providing stock details.

The activities execute asynchronous calls to the API requests and retrieving the response for every 10 seconds(as stock prices change frequently)

## Building the Stocker App

First, clone the repo:

`git clone https://github.com/Sahana2502/stockApp.git`

### Android Studio 

(The app was built using Android Studio and tested on Android P)

* Open Android Studio and select `File->Open...` or from the Android Launcher select `Import project (Eclipse ADT, Gradle, etc.)` and navigate to the root directory of your project.
* Select the directory
* Click 'OK' to open the the project in Android Studio.
* A Gradle sync should start, or force a sync and build the 'app' module as needed.

### Gradle (command line)

* Build the APK: `./gradlew build`

## Running the Stocker App

Connect an Android device to your desktop/laptop.

### Android Studio

* Select `Run -> Run 'app'` (or `Debug 'app'`) from the menu bar
* Select the device you wish to run the app on and click 'OK'

## Libraries

This app leverages the following third-party library:

 * [OkHttp](https://square.github.io/okhttp/recipes/) - For asynchronous network requests

## Enhancements

As the application requires low level network operations, OkHttp was choosen as it provides fairly simple and straight-forward procedure.The library supports asynchronous and synchronous calls. It also provides advanced features such as response caching, connection pooling, and recovery from connection failure.

* HTTP/2 can be enabled for achieving multiplexing, header compression and other security provisions.
* For more powerful and efficient network operations, Retrofit/Volley can be implemented.
 
