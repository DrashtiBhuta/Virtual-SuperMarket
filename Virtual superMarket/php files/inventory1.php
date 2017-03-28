</html>
<head>
<title> Orders </title>
</head>
<style type="text/css">
table { border-collapse: collapse; } //to remove cell spacings
table td { padding: 2px; }
tr.accepted, tr.accepted td { background-color: green; }

</style>
<body bgcolor="white" onload="loadpage()">
<?php
	$host='127.0.0.1';
	$uname='root';
	$pwd='';
	$db="virtual";
	
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	
	$r=mysql_query("select * from counterdetails",$con);
	$tablenames = array();
	while($row=mysql_fetch_array($r, MYSQL_ASSOC))
	{
		$tablenames[] = $row['custid'];
	}
	foreach($tablenames as $values)
	{
		$r=mysql_query("select * from `".$values."`",$con);
		$emparray = array();
		echo "<div style='float: left;padding-right: 45px;'> account no:".$values." <br><br>";
		echo "<table border='1' style='margin-left:10px'> <tr> <td> PID </td><td> Name </td> <td> Quantity </td> <td> Size </td><td> Status</td></tr>";
		$i=1;
		while($row=mysql_fetch_array($r, MYSQL_ASSOC))
		{
		$id="btn".$i;
		$id1="row".$i;
		$row_array['pid'] = $row['pid'];
		$row_array['pname'] = $row['pname'];
		$row_array['prodprice'] = $row['prodprice'];
		$row_array['prodqty'] = $row['prodqty'];
		$row_array['prodsize'] = $row['prodsize'];
		// and your respective cols
		//array_push($emparray,$row_array);
		if($row[status]=="complete")
		{
			echo "<tr id=".htmlspecialchars($id1)." style='background-color:green'>";
		}
		else
		{
			echo "<tr id=".htmlspecialchars($id1)." style='background-color:white'>";
		}
		echo "<td>".$row_array['pid']."</td>";
		echo "<td>".$row_array['pname']."</td>";
		echo "<td>".$row_array['prodqty']."</td>";
		echo "<td>".$row_array['prodsize']."</td>";
		echo "<td align='center'>";
		
		echo "<input type='button' value='Pending' name='txtaccept' onclick='Accept(this)' id=".htmlspecialchars($id)."></td></tr>";
		$i++;
		}
		
		$query="select Status from counterdetails where custid='".$values."'";
		$r=mysql_query($query,$con);
		while($row=mysql_fetch_array($r, MYSQL_ASSOC))
		{
			$status=$row['Status'];
			echo $row['Status'];
			echo "<tr><td> status </td>";
			echo "<td colspan='4'>".$status."</td></tr>";
		}
		echo "</table></div>";
		
	//print(json_encode(array($emparray)));
		//print(json_encode($row_array)));
	}
	function changestatus()
	{
		$host='127.0.0.1';
		$uname='root';
		$pwd='';
		$db="virtual";
	
		$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
		mysql_select_db($db,$con) or die("db selection failed");
		$query="select Status from counterdetails where custid='".$values."'";
		$r=mysql_query($query,$con);
		
		
	
	}
	mysql_close($con);
	
	?>
<script type="text/javascript">
function Accept(el)
{
	 e1 = event || window.event; // IE
    var target = e1.target || event.srcElement; // IE

    var id = target.id;
	document.getElementById(id).value="complete";
   var tr = el.parentNode.parentNode; //tr
   tr.className = "accepted";
   <?php echo changestatus();?>
   
}

</script>
</body>
</html>