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
//EXAMPLE www.test.comm?task=create new app
$task = $_GET['task'] ;
$sql = "INSERT INTO tasks(task) VALUES('$task')" ;
$result = $conn->query($sql);
$resultjson["result"]= $result ;
echo json_encode($resultjson)  ;

?>