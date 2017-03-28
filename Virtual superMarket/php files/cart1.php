<?php
	$host='127.0.0.1';
	$uname='root';
	$pwd='';
	$db="virtual";
	
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	
	$custno=$_REQUEST['cid'];
	$id=$_REQUEST['id'];
	
	$r=mysql_query("select * from `".$custno."` where deleteflag = 0",$con);
	$emparray = array();
	while($row=mysql_fetch_array($r, MYSQL_ASSOC))
	{
		$row_array['pid'] = $row['pid'];
		$row_array['pname'] = $row['pname'];
		$row_array['prodprice'] = $row['prodprice'];
		$row_array['prodqty'] = $row['prodqty'];
		$row_array['prodsize'] = $row['prodsize'];
		// and your respective cols
		array_push($emparray,$row_array);
	}
	print(json_encode(array('cart'=> $emparray)));
	mysql_close($con);
?>