<?php
$host='127.0.0.1';
$uname='root';
$pwd='';
$db="virtual";

$localhost = mysql_connect($host,$uname,$pword) or trigger_error(mysql_error(),E_USER_ERROR);
mysql_select_db($database,$localhost);

$custno=$_REQUEST['cid'];

$i=mysql_query("select * from ".$custno,localhost);

$num_rows = mysql_num_rows($i);
while($row = mysql_fetch_assoc($i, MYSQL_ASSOC))
{

$r[]=$row;
$check=$row['pid'];
}

if($check==NULL)
{
$r[$num_rows]="Record is not available";
print(json_encode($r));
}
else
{
$r[$num_rows]="success";
print(json_encode($r));
}

mysql_close($localhost);
?>