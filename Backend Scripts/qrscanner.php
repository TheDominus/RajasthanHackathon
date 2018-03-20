 <?php
 if($_SERVER['REQUEST_METHOD']=='POST'){

   $userid = $_POST['userid'];
   $monumentid = $_POST['monumentid'];
   $ticketid = $_POST['ticketid'];
   $quantity = $_POST['quantity'];
   $date = $_POST['date'];
   $random = $_POST['random'];
   $response = array();
   if ($monumentid == "" || $userid == "")
   {
     $response['message'] = "failure";
     echo json_encode($response);
     exit(0);
   }
 include('init.php');
 $date1 = date("Y-m-d");
 $sql = "SELECT scanned FROM ticket_information WHERE user_id='$userid' AND monument_id ='$monumentid' AND ticket_id = '$ticketid' AND date = '$date1' AND quantity = '$quantity' AND random = '$random'";
 $res = mysqli_query($con,$sql);
 $check = mysqli_fetch_array($res);
 if(isset($check)){
   if (($check[0])){
     $response['message'] = "scanned";
     $sql1 = "SELECT firstname FROM login_information WHERE user_id = '$userid'";
     $res1 = mysqli_query($con,$sql1);
     $check1 = mysqli_fetch_array($res1);
     if(isset($check1)){
       $response['firstname'] = $check1[0];
       $sql2 = "SELECT monument FROM monument_information WHERE monument_id = '$monumentid'";
       $res2 = mysqli_query($con,$sql2);
       $check2 = mysqli_fetch_array($res2);
       if(isset($check2)){
         $response['monument'] = $check2[0];
       }
     }
   echo json_encode($response);
   }
   else{
    $response['message'] = "success";
    $sql1 = "SELECT firstname FROM login_information WHERE user_id = '$userid'";
    $res1 = mysqli_query($con,$sql1);
    $check1 = mysqli_fetch_array($res1);
    if(isset($check1)){
      $response['firstname'] = $check1[0];
      $sql2 = "SELECT monument FROM monument_information WHERE monument_id = '$monumentid'";
      $res2 = mysqli_query($con,$sql2);
      $check2 = mysqli_fetch_array($res2);
      if(isset($check2)){
        $response['monument'] = $check2[0];
      $sql3 = "UPDATE `ticket_information` SET `scanned`=1 WHERE `ticket_id`='$ticketid'";
      $res3 = mysqli_query($con,$sql3);
      sleep(2);
      echo json_encode($response);
      }
      else {
        $response['message'] = "failure";
        echo json_encode($response);
      }
    }
    else {
      $response['message'] = "failure";
      echo json_encode($response);
    }
 }
 }else{
   $response['message'] = "failure";
   echo json_encode($response);
 }
 $con->close();
 }
 ?>
