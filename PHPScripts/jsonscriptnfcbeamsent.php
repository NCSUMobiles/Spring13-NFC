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

$id=$_POST["id"];
$deviceid=$_POST["deviceid"];
$sql1 = "SELECT tag1,tag2,user1,user2,result FROM beam WHERE id='$id'";
do
{
$result = mysql_query($sql1);
}while(!mysql_num_rows($result));
//$output="";
//$row=mysql_fetch_assoc($result);
$row=mysql_fetch_assoc($result);

//$sql3 = "UPDATE rps SET $row[result] = '$row[result]'+1 , $row[tag1] = '$row[tag1]' +1 WHERE deviceid = '$deviceid' ";
//$updateRes = mysql_query($sql3); 
$sql2 = "DELETE FROM beam WHERE id='$id'";
$n = mysql_query($sql2);


print(json_encode($row));
mysql_close();   
?>
