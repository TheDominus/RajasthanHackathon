<?php
 if($_SERVER['REQUEST_METHOD']=='POST'){

   $email = $_POST['email'];
   $password = $_POST['password'];
   $response = array();
   if ($password == "" || $email == "")
   {
     $response['message'] = "failure";
     echo json_encode($response);
     exit(0);
   }
  $password = md5($password);
 include('init.php');
 $sql = "SELECT * FROM login_information WHERE email ='$email' AND password ='$password'";
 $res = mysqli_query($con,$sql);
 $check = mysqli_fetch_array($res);
 if(isset($check)){
    $response['message'] = "success";
    $response['userid'] = $check[0];
    $response['firstname'] =$check[1];
    $response['lastname'] =$check[2];
    echo json_encode($response);
 }else{
   $response['message'] = "failure";
   echo json_encode($response);
 }
 $con->close();
 }
 ?>
