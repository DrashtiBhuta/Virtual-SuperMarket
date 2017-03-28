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
	$previousq=$_REQUEST['prevqty'];
	$diff=abs($previousq-$quant);
	$flag['code']=0;
	$price=$price*$quant;
	$query="select * from prodspecific where prodid='".$id."' and size=".$size;
	$r=mysql_query($query,$con);
	while($row=mysql_fetch_array($r))
	{
		
		$price=$row['price'];
		$avail=$row['qty'];
		
	}
	$price1=$price*$quant;
	$query="UPDATE `".$custno."` SET prodqty=".$quant.",prodprice=".$price1." where pid='".$id."' and prodsize=".$size;
	echo $query;
	if($r=mysql_query($query,$con))
	{
		$flag['code']=1;
		echo"hi";
		if($quant>$previousq)
		{
			$avail=$avail-$diff;
		}
		else
		{
			$avail=$avail+$diff;
		}
		$query="UPDATE prodspecific SET qty=".$avail." where prodid='".$id."' and size=".$size;
		echo $query;
		if($r=mysql_query($query,$con))
		{}
		
	}

	print(json_encode($flag));
	mysql_close($con);
?>