<?php
$host='127.0.0.1';
	$uname='root';
	$pwd='';
	$db="virtual";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$custno=$_REQUEST['cid'];
	$id=$_REQUEST['pid'];
	$quant=$_REQUEST['qty'];
	$size=$_REQUEST['psize'];

	$flag['code']=0;
	
	$query="update ".$custno." set qty= ".$quant. "where prodid= '".$id."' and prodsize= ".$size;
	echo $query;
	if($r=mysql_query($query,$con))
	{
		$flag['code']=1;
		echo"hi";
	}

	print(json_encode($flag));
	mysql_close($con);
?>