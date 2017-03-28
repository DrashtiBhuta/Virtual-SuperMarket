<?php
	$host='127.0.0.1';
	$uname='root';
	$pwd='';
	$db="virtual";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$custno=$_REQUEST['cid'];
	$id=$_REQUEST['id'];
	
	 
	$flag['code']=0;
	$query="delete from ".$custno." where pid=".$id;
	echo $query;
	$result = mysql_query($query, $con);
	if(empty($result))
	{
		$flag['code']=1;
		echo "hi";
	}
	 
	print(json_encode($flag));
	mysql_close($con);
?>