<?php

require "init.php";

$name = $_POST['name'];
$email = $_POST['email'];
$password = $_POST['password'];

$sql_query = "insert into users values('$name','$email','$password');";

if (mysqli_query($con,$sql_query)) {
	//echo "Data Insertion Success";
}

else {
	
	//echo "Data insertion error".mysqli_error($con);
}



?>