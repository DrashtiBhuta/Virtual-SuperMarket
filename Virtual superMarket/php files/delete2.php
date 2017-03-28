<?php
	$host='127.0.0.1';
	$uname='root';
	$pwd='';
	$db="virtual";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	
	$custno="c1001";
	print $custno;
	$pid="1046";
	$size=500;

	$order=3;
	print $pid;
	
	$query="delete from  ".$custno." where pid='".$pid."' and prodsize= ".$size;
	print $query;
	mysql_query($query,$con);
	if (mysql_affected_rows() > 0) 
	{
		$flag['code']=1;
		$query="select qty from prodspecific where prodid= '".$pid."' and size= ".$size;
		$r=mysql_query($query,$con);
		$row=mysql_fetch_array($r);
			$qty2=$row['qty'];
			echo $qty2;
			$qty1=$qty2 + $order;
			$flag['order']=$qty1;
		
		$query1="update prodspecific set qty= ".$qty1." where prodid= '".$pid."' and size= ".$size;
		if($r=mysql_query($query1,$con))
		{
			echo "updated";
		}
		echo "deleted";
	}
	else 
	{
		$flag['code']=0;
		echo " not deleted";
	}

	print(json_encode($flag));
	mysql_close($con);