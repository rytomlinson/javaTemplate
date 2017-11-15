<!doctype html>
<html lang="en">
<head>
    <!-- The first thing in any HTML file should be the charset -->
    <meta charset="utf-8">
    <!-- Make the page mobile compatible -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Allow installing the app to the homescreen -->
    <link rel="manifest" href="manifest.json">
    <meta name="mobile-web-app-capable" content="yes">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <title>NAVIS - Insight</title>
</head>
<body>
<!-- Display a message if JS has been disabled on the browser. -->
<noscript>If you're seeing this message, that means <strong>JavaScript has been disabled on your browser</strong>, please <strong>enable JS</strong> to make this app work.</noscript>

<!-- The app hooks into this div -->
<div id="app"></div>
<!-- A lot of magic happens in this file. HtmlWebpackPlugin automatically includes all assets (e.g. bundle.js, main.css) with the correct HTML tags, which is why they are missing in this HTML file. Don't add any assets here! (Check out webpackconfig.js if you want to know more) -->
<script data-dll="true" src="${sourcePath}reactBoilerplateDeps.dll.js"></script><script type="text/javascript" src="${sourcePath}qvue-client-ui.js"></script></body>
</html>

