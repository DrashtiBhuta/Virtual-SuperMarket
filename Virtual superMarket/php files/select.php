<?php
	$host='127.0.0.1';
	$uname='root';
	$pwd='';
	$db="virtual";
	
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	
	$id=$_REQUEST['id'];
	$size=$_REQUEST['size'];
	
	$r=mysql_query("select * from prodspecific where prodid='".$id."' and size= ".$size,$con);
	
	while($row=mysql_fetch_array($r))
	{
		$flag['quant']=$row['qty'];
		$flag['price']=$row['price'];
		
	}
	print(json_encode($flag));
	mysql_close($con);
?>