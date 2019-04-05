<?php
#Get the username and password from headers.
$username = $_SERVER[PHP_AUTH_USER"];
$password = $_SERVER[PHP_AUTH_PW"];

if ($username == "bob" && $password == "password") {
	echo "true";
} elseif ($username == "janet" && $password == "password") {
	echo "true";
} elseif ($username == "riku" && $password == "password") {
	echo "true";
} elseif ($username == "bill" && $password == "password") {
	echo "true";
} elseif ($username == "jack" && $password == "password") {
	echo "true";
} elseif ($username == "bob" && $password == "password") {
	echo "true";
} else {
	echo "false"
}
?>