<?php
	$host='127.0.0.1';
	$uname='root';
	$pwd='';
	$db="virtual";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	
	$custno=$_REQUEST['cid'];
	$id=$_REQUEST['id'];
	$size=$_REQUEST['size'];
	
	
	$query="select qty from prodspecific where prodid= '".$id."' and size= ".$size;
	
	$r=mysql_query($query,$con);
	
	while($row=mysql_fetch_array($r, MYSQL_ASSOC))
	{
		$flag['avail'] = $row['qty'];
	}
	print(json_encode($flag));
	mysql_close($con);
?>