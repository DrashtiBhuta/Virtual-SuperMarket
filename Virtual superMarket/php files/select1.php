<?php
	$host='127.0.0.1';
	$uname='root';
	$pwd='';
	$db="virtual";
	
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	
	$id=$_REQUEST['id'];
	
	$r=mysql_query("select * from prodmaster m,prodspecific s where m.prodid='".$id."' and m.prodid=s.prodid",$con);
	$emparray = array();
	while($row=mysql_fetch_array($r, MYSQL_ASSOC))
	{
		$row_array['desc'] = $row['prodesc'];
		$row_array['pname'] = $row['prodname'];
		$row_array['psize'] = $row['size'];// and your respective cols
		array_push($emparray,$row_array);
		
	}
	print(json_encode(array('cart'=> $emparray)));
	mysql_close($con);
?>