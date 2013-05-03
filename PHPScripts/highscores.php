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
$sqltotalplayed= "SELECT username as uu, win from rps order by win desc";
$totgames = mysql_query($sqltotalplayed);
while($row=mysql_fetch_assoc($totgames))
{
  $p1=$row;
$p1ans=$p1["uu"];
$p1wins=$p1["win"];
print(json_encode($p1ans));
print(json_encode(" "));
print(json_encode($p1wins));
print(json_encode("  "));
}
 mysql_close();   
?>
