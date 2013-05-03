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

//$curruser =$_POST["username"];

//print($curruser);
//print("hello");

$devid=$_POST["deviceid"];
 $sql1= "SELECT username from rps where deviceid = '$devid'";
$res = mysql_query($sql1);
 $sqltotalplayed= "SELECT username, previouschoice, avatar from location where locationid=1";
$totgames= mysql_query($sqltotalplayed);
//while($row=mysql_fetch_assoc($totgames))
  $p1=mysql_fetch_assoc($totgames);

//$p1ans=$p1["imgid"];  
$r = mysql_fetch_assoc($res);
$u = $r["username"];
$user= $p1["username"];
$img=$p1["avatar"];

$prev=$p1["previouschoice"];
$nochange=0;
if($u==$user)
{
	$nochange=1;
	$num= rand ( 1 , 3 );
	if($num==1)
	$prev="rock";
	elseif($num==2)
	$prev="scissor";
	else 
	$prev="paper";


	$user="MasterMind";
	
	$num2=rand(1,6);
	if($num2==1)
	$img= 1;
	elseif ($num2==2)
	$img=2;
	elseif ($num2==3)
	$img=3;
	elseif ($num2==4)
	$img=4;
	elseif ($num2==5)
	$img=5;
	elseif ($num2==6)
	$img=6;
	
	
}
//print("\n");
$space=",";

//if($nochange==0)
//$retvalue= $u.$space.$img;
//else
$retvalue=$u.$space.$user.$space.$img;

 print($retvalue);

//print("after encoding");

 mysql_close();   
?>
