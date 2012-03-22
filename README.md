Kauppalehti RSS Aggregator
==========================

This small web application fetches a few RSS feeds from the [kauppalehti.fi](http://www.kauppalehti.fi) website, combines them to one feed and renders it on a webpage.

Running the application is simple, just type check out from GitHub and type `mvn jetty:run` on command line. After Jetty has started, open `http://localhost:8080` in your browser.

The application does some caching, so the feeds are not updated every time you reload the page. The cache expires in ten minutes and it is not persistent. Restarting the application always reloads the feeds.
