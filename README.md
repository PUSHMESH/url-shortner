# url-shortner
This project/repo is aimed to provide a shortened URL to the client. The client inputs the big url and it generates the much shorter URL possible.

CORS (Cross origin Resource Sharing) is enabled in this project to allow the client to access the API from a different domain.
Below is the explanation of the code in the project:

The corsConfigurationSource() method configures Cross-Origin Resource Sharing (CORS) for our Spring Boot application :

Purpose: Allows your API to accept requests from different origins (domains/ports), which is essential for frontend applications running on different servers to communicate with your backend.

Step-by-step explanation:

1) CorsConfiguration configuration = new CorsConfiguration();
Creates a new CORS configuration object that defines the rules for cross-origin requests.

2) setAllowedOrigins();
Specifies which domains are allowed to make requests to your API.
Here: http://localhost:3000 (React dev server) and http://localhost:4200 (Angular dev server) are whitelisted.
Only requests from these origins will be accepted.

3) setAllowedMethods()
Defines which HTTP methods are permitted: GET, POST, PUT, DELETE, and OPTIONS.
OPTIONS is used by browsers for CORS preflight requests.

4) setAllowedHeaders()
Specifies which request headers are allowed ("*" means all headers).
Important for requests with custom headers like Authorization.

5) setAllowCredentials(true)
Allows cookies and authentication credentials to be included in cross-origin requests.
Necessary for HTTP Basic Authentication in your setup.

6) setMaxAge(3600L)
Caches CORS preflight response for 3600 seconds (1 hour).
Reduces unnecessary preflight requests, improving performance.

7) UrlBasedCorsConfigurationSource
Maps the CORS configuration to URL patterns.
registerCorsConfiguration("/**", configuration) applies these rules to all endpoints.

8) Result: Your frontend applications can now make authenticated requests to your API endpoints without CORS errors.
