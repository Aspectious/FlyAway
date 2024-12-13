/**
 * @typedef {Object} AuthToken
 * @property {String} code
 * @property {String} exp
 *
 * @typedef {Object} AuthResponse
 * @property {String} result
 * @property {String} sessionID
 * @property {AuthToken} token
 * @property {String} username
 */
async function login() {
    document.getElementById("resp-banner").style.backgroundColor = "#ffdc67";
    document.getElementById("resp-banner").innerText = "Connecting..."
    document.getElementById("resp-banner").hidden = false;
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
        h.set("Content-Type", "application/json");
        document.getElementById("resp-banner").innerText = "Authenticating..."
        await fetch("https://localhost:8000/", {
            headers:h,
            method: "POST",
            body: JSON.stringify(request)
        }).then(function (res ) {
            document.getElementById("resp-banner").innerText = "Retrieving..."
            var reader = res.body.getReader();
            return new ReadableStream({
                start(controller) {
                    return pump();

                    function pump() {
                        return reader.read().then(({done, value}) => {
                            // When no more data needs to be consumed, close the stream
                            if (done) {
                                controller.close();
                                return;
                            }
                            // Enqueue the next data chunk into our target stream
                            controller.enqueue(value);
                            return pump();
                        });
                    }
                },
            });
        }).then((stream) => {
            return new Response(stream);
        }).then(resp => {
            resp.json().then((foo) => {
                console.log(foo);
                if (foo.AuthResponse == undefined || foo.AuthResponse == null) {
                    document.getElementById("resp-banner").style.backgroundColor = "#ff4a4a";
                    document.getElementById("resp-banner").innerText = "Authentication Failure.";
                    console.log("User Authenticated Failure!");
                } else {
                    if (foo.AuthResponse.result == "200") {
                        document.getElementById("resp-banner").style.backgroundColor = "#00d412";
                        document.getElementById("resp-banner").innerText = "Success! Logged in as " + foo.AuthResponse.username;
                        console.log("User Authenticated Successfully!");
                        setTimeout(() => {
                            window.location.replace("/login/result.php?token=" + foo.AuthResponse.token.code);
                        } ,1000)

                    } else {
                        document.getElementById("resp-banner").style.backgroundColor = "#ff4a4a";
                        document.getElementById("resp-banner").innerText = "Authentication Failure.";
                        console.log("User Authenticated Failure!");
                    }
                }
            })
        })
        console.log(request);
    }
}