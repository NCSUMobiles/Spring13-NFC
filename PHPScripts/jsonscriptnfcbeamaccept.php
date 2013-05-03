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
$tag1=$_POST["tag1"];
$tag2=$_POST["tag2"];
$oppdev=$_POST["oppdeviceid"];
$dev=$_POST["deviceid"];
$res="";
$resOppo="";
$luck="";
if(strcmp($tag1, $tag2)==0)
{
$res="draw";
$resOppo="draw";
}
elseif((strcmp($tag1, "rock")==0 && strcmp("paper", $tag2)==0) ||  
(strcmp($tag1, "paper")==0 && strcmp("scissor", $tag2)==0)  || 
(strcmp($tag1, "scissor")==0 && strcmp("rock", $tag2)==0))
{
$res="win";
$luck=$tag2."luck";
$sqlluck1 = "UPDATE rps SET $luck = $luck +1 WHERE deviceid = '$oppdev' ";
$updateluck1 = mysql_query($sqlluck1); 
$resOppo="lose";
}
else
{
$res="lose";
$luck=$tag1."luck";
$sqlluck2 = "UPDATE rps SET $luck  = $luck +1 WHERE deviceid = '$dev' ";
$updateluck2 = mysql_query($sqlluck2); 
$resOppo="win";
}

$sqluserself= "SELECT username FROM rps WHERE deviceid=$dev";

$self = mysql_query($sqluserself);
$row=mysql_fetch_assoc($self);
$self = $row["username"];
$sqluseropp="SELECT username FROM rps WHERE deviceid=$oppdev";
$opp = mysql_query($sqluseropp);
$row=mysql_fetch_assoc($opp);
$opp = $row["username"];

$sql = "INSERT INTO  beam	VALUES (".$_POST["id"].",'$self','$opp','".$_POST["tag1"]."','".$_POST["tag2"]."','$res')";
$result = mysql_query($sql);
$sql3 = "UPDATE rps SET $res = $res+1 , $tag2 = $tag2 +1 WHERE deviceid = '$oppdev' ";
$updateRes3 = mysql_query($sql3); 
$sql4 = "UPDATE rps SET $resOppo = $resOppo+1 , $tag1 = $tag1 +1 WHERE deviceid = '$dev' ";
$updateRes4 = mysql_query($sql4); 
$rr = $res.",".$self.",".$opp;
print($rr);
mysql_close();   
?>