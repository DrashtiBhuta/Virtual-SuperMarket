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
	$size=$_REQUEST['size'];

	$flag['code']=0;
	$query = "select 1 from `".$custno."`";
	$result = mysql_query($query, $con);
	if(empty($result)) 
	{
		$query="create table `".$custno."` (pid varchar(11),pname varchar(100),prodprice int(10),prodqty int(100),prodsize int(10),status varchar(10))";
		if($c=mysql_query($query,$con))
		{
			echo "table created";
		}
	}
	$query="insert into `".$custno."` (`pid`, `pname`, `prodprice`,`prodqty`, `prodsize`,`status`) values ('".$id."','".$name."',".$price.",".$quant.",".$size.",'pending')";
	echo $query;
	if($r=mysql_query($query,$con))
	{
		$flag['code']=1;
		$query="select qty from prodspecific where prodid='".$id."' and size=".$size;
		$r=mysql_query($query,$con);
		$row=mysql_fetch_array($r, MYSQL_ASSOC);
		echo $row['qty'];
		$total=$row['qty']-$quant;
		echo $total;
		$query="UPDATE `prodspecific` SET qty=".$total." where prodid='".$id."' and size=".$size;
		if($r=mysql_query($query,$con))
		{
			$query="select qty from prodspecific where prodid='".$id."' and size=".$size;
			$r=mysql_query($query,$con);
					
			while($row=mysql_fetch_array($r, MYSQL_ASSOC))
			{
				$flag['avail']= $row['qty'];
			}
			
			
		}
	{
			echo"hi";
		}
	}

	print(json_encode($flag));
	mysql_close($con);
?>