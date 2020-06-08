<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "todoapp" ;

// Create connection
$conn = new mysqli($servername, $username, $password,$dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$result = $conn->query("select * from tasks order by id");
if($result->num_rows > 0){
	$array = array() ;

while($row = $result->fetch_assoc()){
	array_push($array,$row);

	
}
	echo json_encode($array);
}

?>