<?php include("loader.php")?>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <link rel="icon" href="/img/flyaway-logo-filled.ico" type="image/x-icon"/>
        <link rel="stylesheet" href="/index.css"/>
        <title><?php include('components/titlemode')?> - Dashboard</title>
    </head>
    <body id="root">
        <div class="sidebar-container">
            <div class="sidebar-button">

            </div>
            <div class="sidebar-item-selected sidebar-item">
                Dashboard
            </div>
            <div class="sidebar-item" onclick="window.location.href='/logs/'">Event Viewer</div>
            <div class="sidebar-item">Student Configuration</div>
            <div class="sidebar-item">Management</div>
        </div>
        <div id="content-root" class="content-root">
            <div class="content-header">
                <div class="content-header-searchbar"></div>

               <a class="content-header-account"  onclick="return confirm('Log out?')">Logout</a>
<!--                <script> if (confirm("Log out")) {-->
<!--                        window.location.href = "/logout.php";-->
<!--                    }</script>-->

            </div>
        </div>
    </body>
</html>