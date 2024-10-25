#include <cpr/cpr.h>
#include <iostream>

int main()
{
    while(true){
        int id;
        std::cout << "Enter id: ";
        std::cin >> id;
        if (id == -1){
            break;
        }

        cpr::SslOptions sslOpts = cpr::Ssl(cpr::ssl::VerifyHost{false}, cpr::ssl::VerifyPeer{false},cpr::ssl::VerifyStatus{false});
        auto response = cpr::Get(cpr::Url{"https://localhost:8000/validate?id=" + std::to_string(id)}, sslOpts);
        std::cout << response.text << std::endl;

    }
    return 0;
}