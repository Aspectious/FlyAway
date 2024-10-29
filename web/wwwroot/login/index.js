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
        h.set("Content-Type", "application/json");
        await fetch("https://localhost:8000/", {
            headers:h,
            method: "POST",
            body: JSON.stringify(request)
        }).then(function (res ) {
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
            })
        })
        console.log(request);
    }
}