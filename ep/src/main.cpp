#include <cpr/cpr.h>
#include <iostream>

int main() {
    auto response = cpr::Get(cpr::Url{"https://api.github.com/repos/whoshuu/cpr/contributors"});
    std::cout << response.text << std::endl;
    return 0;
}