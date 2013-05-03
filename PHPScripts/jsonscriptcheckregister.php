<?php

 // PHP variable to store the host address
 $db_host  = "127.0.0.1";
 // PHP variable to store the username
 $db_uid  = "root";
 // PHP variable to store the password
 $db_pass = "";
 // PHP variable to store the Database name  
 $db_name  = "nfctrial"; 
        // PHP variable to store the result of the PHP function 'mysql_connect()' which establishes the PHP & MySQL connection  
 $db_con = mysql_connect($db_host,$db_uid,$db_pass) or die('could not connect');
 mysql_select_db($db_name);
 
 $id=$_POST["deviceid"];
 $sql1 = "SELECT * FROM rps WHERE deviceid='$id'";
 
 $result = mysql_query($sql1);
 if(mysql_num_rows($result))
 {
	$output="found";
 }
 else
 {
	$output="not found";
 }
 print($output);
 mysql_close();   
?>
