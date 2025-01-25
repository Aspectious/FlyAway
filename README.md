<div align=center>
  <img src="https://raw.githubusercontent.com/Aspectious/FlyAway/e6feeb0cbe33394068fb5090a4ad3e8122408cfa/web/wwwroot/img/flyaway-logo.png" width=250/>
  <h1>Flyaway</h1>
  <p>A Solution for Attendance Purposes</p>
</div>
</br>
</br>

## About

Flyaway is a project initally aimed for automating students' early dismissal from school for CO-OP Opportunities. However, it has now evolved into a system of infrastructure supporting any use case where one needs to keep track of scanned badges. Flyaway uses three critical components to achieve it's goal.
* The Database
  - A MySQL Database contains the actual data processed by Flyaway, and it is accessed through the Backend.
* Backend Servers
  - Two servers run here, a PHP Webserver for the web interface, and a Java API Server to handle protected, pre-defined calls to the Database.
* Frontend Devices
  - Here, we have two devices, one being whatever device is monitoriong the system from it's web interface, and the other is the actual endpoint. The endpoint talks to the database through the webserver's API calls in a secure manner.
