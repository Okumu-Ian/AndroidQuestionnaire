<?php
//these are the server details
//the username is root by default in case of xampp
//password is nothing by default
//and lastly we have the database named android. if your database name is different you have to change it
$servername = "sql305.epizy.com";
$username = "epiz_23867785";
$password = "ycnfJnRmwTLcK";
$database = "epiz_23867785_Results";



//creating a new connection object using mysqli
$conn = new mysqli($servername, $username, $password, $database);

//if there is some error connecting to the database
//with die we will stop the further execution by displaying a message causing the error
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}else{
echo "Success"}
?>
