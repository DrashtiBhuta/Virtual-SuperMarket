<! DOCTYPE html>
<head>
<title> Orders </title>
</head>
<style type="text/css">
<style>
DIV.table 
{
    display:table;
	width:48%;

	
}
FORM.tr, DIV.tr
{
    display:table-row;
	width:48%;
	
	
}
.status
{
  width:48%;
  height:5%;
  color: #fff;
  background-color:black;
  position:relative;  
}
SPAN.td
{
	vertical-align:top;
    display:table-cell;
	border: 1px solid black;
	width:18%;
	max-width:18%;
	
}

</style>

</style>
<body bgcolor="white" onload="loadpage()">
<?php
	$page = $_SERVER['PHP_SELF'];
	$sec = "10";
	header("Refresh: $sec; url=$page");
	$host='127.0.0.1';
	$uname='root';
	$pwd='';
	$db="virtual";
	
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	
	$r=mysql_query("select * from counterdetails where bagready='no'",$con);
	$tablenames = array();
	while($row=mysql_fetch_array($r, MYSQL_ASSOC))
	{
		$tablenames[] = $row['custid'];
	}
	if(@$_POST['txtaccept'])
	{
		 change();
		
	}
	if(@$_POST['btnredy'])
	{
		 statupdate();
		
	}
	
	$i=1;
	foreach($tablenames as $values)
	{
		
		$r=mysql_query("select * from `".$values."`",$con);
		$emparray = array();
		echo "<div style='float:left; width:48%; margin-left:1px;'><p style='font-size:x-large';> account no:".$values."</p>";
		echo "<div class='table'>";
		echo "<div class='tr'><span class='td'>PID</span>";
							echo "<span class='td'>Name</span>";
							echo "<span class='td'>Qunatity</span>";
							echo"<span class='td' >Size</span>";
							echo "<span class='td'>Status</span>";
							echo "</div>";
	
		
		while($row=mysql_fetch_array($r, MYSQL_ASSOC))
		{
		$pid=$row['pid'];
		
		$pname= $row['pname'];
		$prodprice = $row['prodprice'];
		$prodqty = $row['prodqty'];
		$prodsize = $row['prodsize'];
		$status = $row['status'];
		$delflag=$row['deleteflag'];
		$temp="completed";
		$temp1=1;
			
		if($delflag==$temp1)
		{
			echo  "<form class='tr' style='background-color:#E52B50;color:white;' action=".$_SERVER['PHP_SELF']." method='POST'>";
	
		}
		else if(strcmp($status,$temp)==0)
		{
			echo  "<form class='tr' style='background-color:#4CAF50; color:white;' action=".$_SERVER['PHP_SELF']." method='POST'>";
		}
		else{
			echo  "<form class='tr' style='background-color:white' action=".$_SERVER['PHP_SELF']." method='POST'>";
		}
		//echo "<form action=".$_SERVER['PHP_SELF']." method='POST'>";
		
		echo "<input type='hidden' name='account' value='".$values."' />";
		echo "<span class='td'><input type='hidden' name='pid' value='".$pid."'/>".$pid."</span>";
        echo "<span class='td'><input type='hidden' name='pname' value='".$pname."'/>".$pname."</span>";
        echo "<span class='td'><input type='hidden' name='qty' value='".$prodqty."'/>".$prodqty."</span>";
		echo "<span class='td'><input type='hidden' name='size' value='".$prodsize."'/>".$prodsize."</span>";
		echo "<span class='td'><input type='submit' value='".$status."' name='txtaccept'/></span>";
		echo "</form>";
		
		
		}
		$query="select Status from counterdetails where custid='".$values."'";
		$r=mysql_query($query,$con);
		while($row=mysql_fetch_array($r, MYSQL_ASSOC))
		{
			$status1=$row['Status'];
			
			
			
	
		}
		echo "<form  action=".$_SERVER['PHP_SELF']." method='POST' >";
			//echo "Customer Status</span>";
			//echo "<span class='td'>".$status1."</span>";
			echo "<input type='hidden' name='account' value='".$values."' />";
			echo "<input  type='submit' class='status' value='bag ready' name='btnredy'/></center></form>";
		
		$i=$i+1;
	//print(json_encode(array($emparray)));
		//print(json_encode($row_array)));
	
		echo "</div></div>";
	}
	mysql_close($con);
	function change()
	{
		
		echo "hello";
		$pid=$_POST['pid'];
		echo "pid :".$pid;
		$custid=$_POST['account'];
		echo "account:".$custid;
		$size=$_POST['size'];
		echo "size:".$size;
		$host='127.0.0.1';
		$uname='root';
		$pwd='';
		$db="virtual";
		
	
		//echo "<br><br> prodid:".$cid;
		$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
		mysql_select_db($db,$con) or die("db selection failed");
		
		
		if($r=mysql_query("UPDATE `".$custid."` SET status='completed' where pid='".$pid."' and prodsize=".$size,$con))
		{
			echo "updated";
		}
		else{ echo "try again";}
		
	}
	function statupdate()
	{
		
		$custid=$_POST['account'];
		echo "account:".$custid;
		

		$host='127.0.0.1';
		$uname='root';
		$pwd='';
		$db="virtual";
		
	
		//echo "<br><br> prodid:".$cid;
		$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
		mysql_select_db($db,$con) or die("db selection failed");
		
		
		if($r=mysql_query("UPDATE counterdetails SET bagready='yes' where custid='".$custid."'",$con))
		{
			echo "updated";
		}
		else{ echo "try again";}
		
	}
	?>
	
</body>

</html>