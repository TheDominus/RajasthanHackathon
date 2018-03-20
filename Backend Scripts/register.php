<?php

    if($_SERVER['REQUEST_METHOD']=='POST')
    {
        $password =$_POST['password'];
        $firstname = $_POST['firstname'];
        $lastname =$_POST['lastname'];
        $email = $_POST['email'];
        $phone =$_POST['phone'];
        $response = array();
        if ($password == "" || $email == "")
       {
           $response['message'] = "failure";
           echo json_encode($response);
           exit(0);
       }
        $password = md5($password);
        require_once('init.php');

        $sql = "INSERT INTO login_information (firstname,lastname,password,phone,email)
        VALUES ('$firstname','$lastname','$password','$phone','$email')";
        if(mysqli_query($con,$sql)){
          $response['message'] = "success";
          echo json_encode($response);
        }
        else{
          $response['message'] = "failure";
          echo json_encode($response);
        }

         $con->close();
}

 ?>
