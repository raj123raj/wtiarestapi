<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>WTIA</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <style type="text/css">
        .main-form, .profile-area {
            width: 640px;
        }
        .main-form {
            margin: 50px auto 0px;
        }
        .profile-area {
            margin: 10px auto;
        }
        .main-form section, .profile-area section {
            margin-bottom: 15px;
            background: #c080c0;
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        }
        .main-form section {
            padding: 30px;
        }
        .profile-area section {
            padding: 30px 30px 30px;
        }
        .profile-area section > div {
            text-align: center;
        }
        .main-form h3 {
            margin: 0 0 15px;
        }
        .form-control, .btn {
            min-height: 38px;
            border-radius: 2px;
        }
        .btn {
            font-size: 20px;
            font-weight: inherit;
            font-family: sans-serif;
        }
        .hideElement {
            display: none;
        }
    </style>
</head>
<body>
<div class="main-form" id="main-form">
    <section>
        <h5 class="text-center">Enter Latitude</h5>
        <div class="form-group">
            <input id="latitude" type="text" class="form-control" placeholder="Enter latitude here..." required="required">
        </div>
        <h5 class="text-center">Enter Longitude</h5>
        <div class="form-group">
            <input id="longitude" type="text" class="form-control" placeholder="Enter longitude here..." required="required">
        </div>
        <div class="form-group">
            <button onclick="loadData()" class="btn btn-primary btn-block">Find TimeZone</button>
        </div>
    </section>
</div>
<div class="profile-area hideElement" id="profile-area">
    <section>
        <div id="loader" class="hideElement">
            <div class="spinner-border" role="status">
                <span class="sr-only">Loading...</span>
            </div>
        </div>
        <div id="profile" class="hideElement">
            <br><br>
            <p><strong>TimeZone: <span id="timezone_id"></strong></span></p>
        </div>
    </section>
</div>
</body>
<script>
    function loadData() {
        document.getElementById("profile-area").classList.remove("hideElement");
        document.getElementById("loader").classList.remove("hideElement");
        document.getElementById("profile").classList.add("hideElement");

        var latitude = document.getElementById("latitude").value;
        var longitude = document.getElementById("longitude").value;


        if(latitude != "" && latitude != null && longitude != "" && longitude != null) {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    var jsonResponse = JSON.parse(this.responseText);
                    document.getElementById("timezone_id").innerHTML = jsonResponse.timezone_id;
                    //document.getElementById("age").innerHTML = jsonResponse.age;
                    document.getElementById("loader").classList.add("hideElement");
                    document.getElementById("profile").classList.remove("hideElement");
                }
            };
            xhttp.open("GET", "getSateliteDetailsByLatAndLon?latitude=" + latitude + "&longitude=" + longitude, true);
            xhttp.send();
            console.log("done");
        } else {
            console.log("Enter latitude and lonitude...")
        }
    }
</script>
</html>