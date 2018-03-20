<?php
    if($_SERVER['REQUEST_METHOD']=='POST')
    {
        $userid = $_POST['userid'];
        $response = array();
        require_once('init.php');
        $sql = "SELECT ticket_id,monument_id,date,quantity,scanned,random FROM ticket_information WHERE user_id = '$userid'";
        $res = mysqli_query($con,$sql);
        while($check = mysqli_fetch_array($res)){
          array_push($response,
          array('ticketid' => $check[0],
          'monumentid'=> $check[1],
          'date' => $check[2],
          'quantity' => $check[3],
          'scanned' => $check[4],
          'random' => $check[5]
        ));
        }
        echo json_encode($response);
         $con->close();
}

 ?>
