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

 
$dev =$_POST["deviceid"];

//print($curruser);
//print("hello");
 $sqltotalplayed= "SELECT win+lose+draw as tot from rps where deviceid='$dev'";
$totgames = mysql_query($sqltotalplayed);
while($row=mysql_fetch_assoc($totgames))
  $p1=$row;

$p1ans=$p1["tot"];

//print("\n");
 print(json_encode($p1ans));

//print("after encoding");


$sqlwins="SELECT win from rps where deviceid='$dev'";
$wins= mysql_query($sqlwins);

while($row=mysql_fetch_assoc($wins))
  $pp=$row;

$p2=$pp["win"];
print(json_encode(" "));
print(json_encode($p2));



$sql2= "SELECT rockluck,paperluck,scissorluck, username from rps where deviceid='$dev'";

 $lucky= mysql_query($sql2);
while($row=mysql_fetch_assoc($lucky))
  $luckyrow=$row;

if($luckyrow["rockluck"]>$luckyrow["scissorluck"]  && $luckyrow["rockluck"]>$luckyrow["paperluck"])
$luckyans="rock";
elseif ($luckyrow["rockluck"]<$luckyrow["scissorluck"]  && $luckyrow["scissorluck"]>$luckyrow["paperluck"])
$luckyans="scissors";
else
$luckyans="paper";

print(json_encode(" "));
print(json_encode($luckyans));
print(json_encode(" "));
print(json_encode($luckyrow["username"]));


$sql3= "SELECT rock,paper,scissor from rps where deviceid='$dev'";

 $l3= mysql_query($sql3);
while($row=mysql_fetch_assoc($l3))
  $l4=$row;

if($l4["rock"]>$l4["scissor"]  && $l4["rock"]>$l4["paper"])
$lans="rock";
elseif ($l4["rock"]<$l4["scissor"]  && $l4["scissor"]>$l4["paper"])
$lans="scissors";
else
$lans="paper";

print(json_encode(" "));
print(json_encode($lans));








 mysql_close();   
?>
