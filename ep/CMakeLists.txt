cmake_minimum_required(VERSION 3.29)
project(ep)

set(CMAKE_CXX_STANDARD 20)

include(FetchContent)
FetchContent_Declare(cpr GIT_REPOSITORY https://github.com/libcpr/cpr.git
                         GIT_TAG bb01c8db702fb41e5497aee9c0559ddf4bf13749) # Replace with your desired git commit from: https://github.com/libcpr/cpr/releases
FetchContent_MakeAvailable(cpr)

add_executable(ep src/main.cpp)
target_link_libraries(ep cpr::cpr)