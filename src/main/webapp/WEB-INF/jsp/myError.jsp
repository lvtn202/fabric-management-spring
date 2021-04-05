</body>
</html>

<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en" xmlns:form="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <script type='text/javascript' src='https://code.jquery.com/jquery-2.1.4.js'></script>

    <script type='text/javascript' src='http://ajax.aspnetcdn.com/ajax/knockout/knockout-3.1.0.js'></script>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>

    <script type='text/javascript' src="../../static/js/myErrorJS.js"></script>

    <title>Error</title>
</head>
<body>
<div class="container">
    <div class="row row2">
        <div class="col-sm-4 col-logo-delete">
            <img src="https://cdn.pixabay.com/photo/2012/04/13/00/14/cross-31176_1280.png" class="image"/>
        </div>
        <div class="col-sm-8 col-icon-message">
            <div class="row row3 row31">
                <p>Unexpected error occurred : ${errorMessage}</p>
                <p>Please <span class="p-contact_admin">contact your administrator</span></p>
            </div>
            <div class="row row3 row32">
                <p>or <span class="p-back-search-project" onclick="window.location='http://localhost:8080/'">back to search project</span></p>
            </div>
        </div>
    </div>
</div>

<style>
    body {
        background: floralwhite;
    }

    .row2 {
        height: 370px;
    }

    .col-logo-delete{
        height: 365px;
    }

    .image{
        /*height: 425px;*/
        width: 250px;
        margin-top: 50px;
        margin-left: 20px;
    }

    .col-icon-message{
        height: 365px;
        font-weight: bold;
        font-family: "Segoe UI";
        font-size: 20px;
        color: #666666;
    }

    .row3{
        height: fit-content;
    }

    .row31{
        margin-top: 100px;
    }

    .row32{
        margin-top: 20px;
    }

    .p-contact_admin{
        color: #FF0000;
    }
    .p-contact_admin:hover{
        text-decoration-line: underline;
        cursor: pointer;
    }

    .p-back-search-project{
        color: #2E64FE;
    }

    .p-back-search-project:hover{
        text-decoration-line: underline;
        cursor: pointer;
    }
</style>

</body>
</html>

