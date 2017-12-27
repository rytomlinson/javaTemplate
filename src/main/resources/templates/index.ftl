<!doctype html>
<html lang="en" class="s-app">
<head>
    <meta charset="UTF-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="" name="description">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1" name="viewport">
    <title>NAVIS InSight</title><!-- Disable tap highlight on IE -->
    <meta content="no" name="msapplication-tap-highlight"><!-- Web Application Manifest -->
    <link href="manifest.json" rel="manifest"><!-- Add to homescreen for Chrome on Android -->
    <meta content="yes" name="mobile-web-app-capable">
    <meta content="NAVIS InSight" name="application-name">
    <link href="img/touch/android-chrome-192x192.png" rel="icon" sizes="192x192">
    <!-- Add to homescreen for Safari on iOS -->
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="NAVIS InSight" name="apple-mobile-web-app-title">
    <link href="img/touch/apple-touch-icon.png" rel="apple-touch-icon">
    <link color="#f07700" href="img/touch/safari-pinned-tab.svg" rel="mask-icon"><!-- Configuration for Win8 -->
    <meta content="browserconfig.xml" name="msapplication-config"><!-- Generic Favicons -->
    <link href="img/touch/favicon-32x32.png" rel="icon" sizes="32x32" type="image/png">
    <link href="img/touch/favicon-16x16.png" rel="icon" sizes="16x16" type="image/png">
    <!-- <link rel="shortcut icon" href="/img/touch/favicon.ico"> --><!-- Color the status bar on mobile devices -->
    <meta content="#ffffff" name="theme-color">
    <link href="/fonts/source-sans-pro/source-sans-pro.css" rel="stylesheet">
    <link href="/fonts/work-sans/work-sans.css" rel="stylesheet">
    <!-- messageformat...TODO: Integrate in as a dependency we can require -->
</head>
<body class="s-app__body">
<!-- Display a message if client browser JS has been disabled. -->
<noscript>If you're seeing this message, that means <strong>JavaScript has been disabled on your browser</strong>,
    please <strong>enable JS</strong> to make this app work.
</noscript>
<div class="s-app__container">
    <div id="app"></div>
</div>
<!-- App hooks into this div -->

<!-- Source Sans Font -->
<script data-dll="true" src="${sourcePath}reactBoilerplateDeps.dll.js"></script><script type="text/javascript" src="${sourcePath}insight-client-ui.js"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600" rel="stylesheet">
</body>
</html>


