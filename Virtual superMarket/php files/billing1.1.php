<!DOCTYPE html>
<html>
<body>
<style>   
    .shop
    {
        margin-top: 10px;
    }
table {
    border-collapse: collapse;
    width: 100%;
    margin-top: 10;
}
th, td {
    text-align: center;
    padding: 8px;
    width:20%;
}
tr:nth-child(even){background-color: #f2f2f2}
th {
    background-color: #E52B50;
    color: white;
    
}
.textdesign
{
    
    width: 48%;
    height: 20%;
    font-size: 40px;
    text-align: center;
}
.total
{
    width: 100%;
    height: 20%;
    font-size: 20px;
    margin-top: 1%;
    margin-left: 0%;
}
.totaltabletd
{
    text-align: center;
    padding: 8px;
    width:10%;
}
.bill
{
     border-collapse: collapse;
    width: 50%;
    margin-top: 10;
    margin-left: 50%;
}
.finalamount
{
    font-size: 50px;
    margin-left: 50%;
    margin-top: 2%;
}
</style>
<?php

echo "<div>";
   echo  "<form action=".$_SERVER['PHP_SELF']." method='post'>";
    echo "<input class='textdesign' type='text' name='accno' placeholder='enter customer number'/>";
    echo "<input class='textdesign' type='submit' name ='bill' value='Generate Bill'/>";
    echo "</form></div>";
    if(@$_POST['bill'])
{
    display();
}
?>



<?php
function display()
{ 
    echo "<div class='shop'>";
    echo "<table><tr><th>id</th><th>name</th><th>size</th><th>quantity</th><th>net price</th></tr>";
    $accno=$_POST['accno'];
    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "virtual";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
     die("Connection failed: " . $conn->connect_error);
     
}
$sql = "SELECT * FROM `".$accno."`";
$result = $conn->query($sql);
$total = "SELECT SUM(prodprice) as total FROM `".$accno."`";
$totalresult = $conn->query($total);
if ($result->num_rows > 0) {
     // output data of each row
     
     while($row = $result->fetch_assoc()) {
            echo "<tr>";
         echo "<td>". $row["pid"]. " </td><td> ". $row["pname"]. "</td><td>" . $row["prodsize"] . "</td><td> " . $row["prodqty"] . "</td><td> " . $row["prodprice"] . "</td></tr>";
     }
} else {
     echo "0 results";
}
echo "</table></div>";
?>



<div class="total">
     
<?php
//Sum of price of all items
echo " <table class='bill'>";
echo "<th class='totaltabletd'><tr><td>Price &nbsp;&nbsp;&nbsp;</td><td>";
 while($row = $totalresult->fetch_assoc()) {
     $total1 = $row["total"];
     echo " Rs. ".$total1;
     }
echo "</td></tr></th>";
// Calculation of Discount
echo "<th class='totaltabletd'><tr><td>Discount (2 %) &nbsp;&nbsp;&nbsp;</td><td>";
 $dicount=0.02;
 $discountvalue = 0.02 * $total1;
 echo "Rs.     -".$discountvalue;
echo "</td></tr></th>";
//Calculation on Tax
echo "<th class='totaltabletd'><tr><td>tax (2 %) &nbsp;&nbsp;&nbsp;</td><td>";
 $tax=0.02;
 $taxvalue = 0.02 * $total1;
 echo "Rs. ".$taxvalue;
echo "</td></tr></th></table>";
echo "<div class=finalamount> Total : "; 
$finalamount = $total1 - $discountvalue + $taxvalue;
echo "Rs. ".$finalamount;
echo "</div>";
$conn->close();

}
?>
</div>
</body>
</html>