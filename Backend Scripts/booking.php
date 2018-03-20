<?php

if($_SERVER['REQUEST_METHOD']=='POST'){

    $userid = $_POST['userid'];
    $monumentid = $_POST['monumentid'];
    $quantity = $_POST['quantity'];
    $date = $_POST['date'];
    $a=array("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
    $b=array("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z");
    $c=array('0','1','2','3','4','5','6','7','8','9');
    $d=array('!','@','#','$','%','^','&');
    shuffle($a);
    shuffle($b);
    shuffle($c);
    shuffle($d);
    $res=$a[0];
    $res.=$b[0];
    $res.=$c[0];
    $res.=$d[0];
    $random = md5($res);
    $response = array();
    require_once('init.php');
    $sql = "INSERT INTO ticket_information (user_id,monument_id,quantity,date, random)
    VALUES ('$userid','$monumentid','$quantity','$date', '$random')";
    if(mysqli_query($con,$sql)){
      $sql1 = "SELECT ticket_id FROM ticket_information  WHERE
      user_id = '$userid' AND monument_id= '$monumentid' AND random = '$random' ";
      $res = mysqli_query($con,$sql1);
      $check = mysqli_fetch_array($res);
      if(isset($check)){
        $response['message'] = "success";
        $response['userid'] = $userid;
        $response['ticketid'] = $check[0];
        $response['date'] = $date;
        $response['quantity'] = $quantity;
        $response['monumentid'] = $monumentid;
        $response['random']  = $random;
        echo json_encode($response);
      }
      else
      {
        $response['message'] = "failure";
        echo json_encode($response);
      }
    }
    else{
      $response['message'] = "failure";
      echo json_encode($response);
    }

     $con->close();
}

 ?>
