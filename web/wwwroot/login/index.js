
async function login() {
    var username = document.getElementById("uname").value;
    var password = document.getElementById("pwd").value;
    if (username == "" || username == null || password == "" || password == null) {
        console.error("No Username or Password!");
    } else {
        password = new Hashes.SHA256().hex(document.getElementById("pwd").value);

        var request = {
            "LoginRequest": {
                "sessionID": sessionID,
                "username": username,
                "password": password
            }
        }
        console.log(request);
        let h = new Headers();
        h.set("Accept", "application/json");
        h.set("Access-Control-Allow-Origin", "*");
        h.set("Content-Type", "application/json");
        const response = await fetch("https://localhost:8000/", {
            headers:h,
            method: "POST",
            body: JSON.stringify(request)
        });

        console.log(request);
    }
}