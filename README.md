# Translator
A basic android app architecture

okhttp3
gson
butterknife
glide
event-bus

Package List:
App: Base components of App, Such as Activity, Fragment, Application, etc

Data: Const data
    -HttpRequestPool: All the http request will be list here
Net: Http related, we use ¡°httpOk¡± here to handle the http request.

Utils: Log, Toast, Dialog etc
    -image: To handle the display and loading of picture by Glide

View: We can divid the whole app into some blocks by UI, such as main, help, settings,etc, each component should be put into this folder

