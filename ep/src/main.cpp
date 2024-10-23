#include <cpr/cpr.h>
#include <iostream>

int main() {
    cpr::SslOptions sslOpts = cpr::Ssl(cpr::ssl::VerifyHost{false}, cpr::ssl::VerifyPeer{false},cpr::ssl::VerifyStatus{false});
    auto response = cpr::Get(cpr::Url{"https://localhost:8000/validate?id=845435"}, sslOpts);
    std::cout << response.text << std::endl;
    return 0;
}