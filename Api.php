<?php

require_once 'DbConnect.php';

 //an array to display response
 $response = array();

 //if it is an api call
 //that means a get parameter named api call is set in the URL
 //and with this parameter we are concluding that it is an api call
 if(isset($_GET['apicall'])){

 switch($_GET['apicall']){

 case 'send':

 //this part will handle the voting

 if(isTheseParametersAvailable(array('user','q1','q2','q3','q4','q5','q6','q7','q8','q9','q10'))){

 //getting the values
 $user = $_POST['user'];
 $q1 = $_POST['q1'];
$q2 = $_POST['q2'];
$q3 = $_POST['q3'];
$q4 = $_POST['q4'];
$q5 = $_POST['q5'];
$q6 = $_POST['q6'];
$q7 = $_POST['q7'];
$q8 = $_POST['q8'];
$q9 = $_POST['q9'];
$q10 = $_POST['q10'];
 //checking if the user is already exist with this username or email
 //as the email and username should be unique for every user
 $stmt = $conn->prepare("SELECT user FROM answers WHERE user = ?");
 $stmt->bind_param("s", $user);
 $stmt->execute();
 $stmt->store_result();

 //if the user already exist in the database
 if($stmt->num_rows > 0){
 $response['error'] = true;
 $response['message'] = 'User already answered';
 $stmt->close();
 }else{

 //if user is new creating an insert query
 $stmt = $conn->prepare("INSERT INTO answers (user, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
 $stmt->bind_param("sssssssssss", $user, $q1 ,$q2, $q3 ,$q4 ,$q5 ,$q6 ,$q7, $q8 , $q9 , $q10);

 //if the user is successfully added to the database
 if($stmt->execute()){
 //adding the user data in response
 $response['error'] = false;
 $response['message'] = 'Data saved';
 $response['user'] = $user;
 }
 else
 {
     echo $stmt->error;
 }
 }

 }else{
 $response['error'] = true;
 $response['message'] = 'required parameters are not available';
 }


 break;
 

 case 'get':

 //this part will handle the votes

 if(isTheseParametersAvailable(array('user'))){
//getting values
$user = $_POST['user'];

//creating the query
$stmt = $conn->prepare("SELECT * FROM answers");

$stmt->execute();

$stmt->store_result();

//if the user exist with given credentials
if($stmt->num_rows > 0){
$stmt->bind_result($id,  $user, $q1 ,$q2, $q3 ,$q4 ,$q5 ,$q6 ,$q7, $q8 , $q9 , $q10);


$votes = array(
);

while($stmt->fetch()) {
              $temp = array();
              $temp['id'] = $id;
              $temp['user'] = $user;
              $temp['q1'] = $q1;
              $temp['q2'] = $q2;
              $temp['q3'] = $q3;
              $temp['q4'] = $q4;
              $temp['q5'] = $q5;
              $temp['q6'] = $q6;
              $temp['q7'] = $q7;
              $temp['q8'] = $q8;
              $temp['q9'] = $q9;
              $temp['q10'] = $q10;
              array_push($votes, $temp);
    }


$response['error'] = false;
$response['message'] = 'Here are the answers';
$response['votes'] = $votes;
}else{
//if the user not found
$response['error'] = false;
$response['message'] = 'No answers sent';
}
}

 break;

 default:
 $response['error'] = true;
 $response['message'] = 'Invalid Operation Called';
 }

 }else{
 //if it is not api call
 //pushing appropriate values to response array
 $response['error'] = true;
 $response['message'] = 'Invalid API Call';
 }

 //displaying the response in json structure
 echo json_encode($response);



 function isTheseParametersAvailable($params){

  //traversing through all the parameters
  foreach($params as $param){
  //if the paramter is not available
  if(!isset($_POST[$param])){
  //return false
  return false;
  }
  }
  //return true if every param is available
  return true;
  }

 ?>
