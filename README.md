pusher
======

Push notifications from Desktop to mobile

mobile to server

1. When the app is installed on mobile, register the device with the server

Browser to server

1. On selecting a number in browser, browser will add a context menu.
2. This context menu will have items pulled from server based on the user context.
3. On clicking a particular item which is the user device name, addon will notify the server.


server to mobile

1. Notify the PNS server based on device type
2. Provide data to mobile when mobile app checks for data.


Development tool Requirements:

1. Android
  Eclipse + ADT plugins + emulator
  Windows/Mac/Linux
  Java, JDK 6.0

2. Chrome
  Chrome browser, notepad
  HTML + JS

3. server
  Linux setup
  Java, JDK 6.0, Tomcat latest, Eclipse
  Database is AWS DynamoDB
  Ant, Ant 1.8.2
