#include <cpr/cpr.h>
#include <iostream>

int main()
{
    while(true){
        int id;
        std::cout << "Enter id: ";
        std::cin >> id;
        if (id == -1)
        {
            break;
        }
            std::string query = "{\"sendrecord\":\"" + std::to_string(id) + "\"}";
            std::cout << query;
            cpr::SslOptions sslOpts = cpr::Ssl(cpr::ssl::VerifyHost{false}, cpr::ssl::VerifyPeer{false},cpr::ssl::VerifyStatus{false});

            cpr::Response r = cpr::Post(cpr::Url{"https://192.168.1.193:8000/"},


                       cpr::Body{query},
                       cpr::Header{{"Content-Type", "application/json"}},sslOpts);
            std::cout << r.text << std::endl;

        /*
         * {
         *   "args": {},
         *   "data": "",
         *   "files": {},
         *   "form": {
         *     "key": "value"
         *   },
         *   "headers": {
         *     ..
         *     "Content-Type": "application/x-www-form-urlencoded",
         *     ..
         *   },
         *   "json": null,
         *   "url": "http://www.httpbin.org/post"
         * }
         */


    }
    return 0;
}