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
$sql1= "SELECT previouschoice from location where locationid=1";
$sql2= "SELECT username from location where locationid=1";
$prevoo = mysql_query($sql1);
$prevuseroo= mysql_query($sql2);
while($row=mysql_fetch_assoc($prevoo))
$pp=$row;
$prev=$pp["previouschoice"];
while($row=mysql_fetch_assoc($prevuseroo))
$pr=$row;
$prevuser=$pr["username"];
$curruser =$_POST["username"];
$curr =$_POST["readtag"];
$res="dontknow";
$nochange=0;
$s1="";
if(strcmp($curruser, $prevuser)==0)
{
$nochange=1;
$num= rand ( 1 , 3 );
if($num==1)
$prev="rock";
elseif($num==2)
$prev="scissor";
else 
$prev="paper";	
}
if(strcmp($prev, $curr)==0)
{
$res="draw";
}
elseif((strcmp($prev, "rock")==0 && strcmp("paper", $curr)==0) ||  (strcmp($prev, "paper")==0 && strcmp("scissor", $curr)==0)  || (strcmp($prev, "scissor")==0 && strcmp("rock", $curr)==0))
{
$res="win";
}
else
{	
$res="lose";
}
$luckyprev= $prev."luck";
$lucky= $curr."luck";
if($res == "draw")
{
if($nochange==0)
{
$s1 = "UPDATE rps SET draw = draw + 1 , $prev = $prev +1 WHERE username = '$prevuser'     ";
}
$s2 = "UPDATE rps SET draw = draw + 1 , $curr = $curr +1 WHERE username = '$curruser'    ";
}
elseif($res == "win")
{
if($nochange==0)
{
$s1 = "UPDATE rps SET lose = lose + 1 , $prev = $prev +1 WHERE username = '$prevuser'     ";
}
$s2 = "UPDATE rps SET win = win + 1 , $curr= $curr +1, $lucky=$lucky +1  WHERE username ='$curruser'     ";
}
else
{
if($nochange==0)
{
$s1 = "UPDATE rps SET win = win + 1 , $prev = $prev +1, $luckyprev=$luckyprev+1 WHERE username ='$prevuser'  ";
}
$s2 = "UPDATE rps SET lose = lose + 1 , $curr= $curr +1 WHERE username ='$curruser'  ";
}
if($nochange==0)
{
$result = mysql_query($s1);
}
$result = mysql_query($s2);
$s3= "UPDATE location SET previouschoice='$curr', username='$curruser'  ";
$result = mysql_query($s3);
print(json_encode($res));
print(json_encode($prev));
print(json_encode($curr));
mysql_close();   
?>
