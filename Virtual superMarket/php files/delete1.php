<?php
$host='127.0.0.1';
	$uname='root';
	$pwd='';
	$db="virtual";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	
	$custno=$_REQUEST['cid'];
	print $custno;
	$pid=$_REQUEST['id'];
	$size=$_REQUEST['size'];

	$order=$_REQUEST['order'];
	print $pid;
	$delflag=1;
	$query="update `".$custno."` set deleteflag=".$delflag." where pid='".$pid."' and prodsize= ".$size;
	print $query;
	
	if($r=mysql_query($query,$con)) 
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
?>