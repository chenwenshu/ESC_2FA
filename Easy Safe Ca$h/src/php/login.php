<?php

require "init.php";


$email = $_POST['email'];
$password = $_POST['password'];

$sql_query = "select name from users where email like '$email' and password like '$password';";

$result = mysqli_query($con,$sql_query);

if (mysqli_num_rows($result) > 0) {
	
	$row = mysqli_fetch_assoc($result);
	$name = $row["name"];
	echo $name;
	
	
}

else {
	echo "Login Failed! Please try again";
	
	
}

?>