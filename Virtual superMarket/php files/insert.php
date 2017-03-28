<?php
$host='127.0.0.1';
	$uname='root';
	$pwd='';
	$db="virtual";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$custno=$_REQUEST['cid'];
	$price=$_REQUEST['price'];
	$id=$_REQUEST['id'];
	$quant=$_REQUEST['qty'];
	$name=$_REQUEST['name'];

	$flag['code']=0;
	$query = "select 1 from ".$custno;
	$result = mysql_query($query, $con);
	if(empty($result)) 
	{
		$query="create table ".$custno." (pid varchar(11),pname varchar(100),prodprice int(10),prodqty int(100))";
		if($c=mysql_query($query,$con))
		{
			echo "table created";
		}
	}
	$query="insert into ".$custno." (`pid`, `pname`, `prodprice`,`prodqty`) values ('".$id."','".$name."',".$price.",".$quant.")";
	echo $query;
	if($r=mysql_query($query,$con))
	{
		$flag['code']=1;
		echo"hi";
	}

	print(json_encode($flag));
	mysql_close($con);
?>