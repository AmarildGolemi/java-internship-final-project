package com.lhind.application.swagger;

public class SwaggerConstant {
    public static final String SECURITY_REFERENCE = "Token Access";
    public static final String AUTHORIZATION_DESCRIPTION = "Limited API Permission Based On User's Role";
    public static final String AUTHORIZATION_SCOPE = "Limited";
    public static final String CONTACT_EMAIL = "email@outlook.com";
    public static final String CONTACT_URL = "https://www.contact.com";
    public static final String CONTACT_NAME = "Trip Management API Support";
    public static final String API_TITLE = "Trip Management open API";
    public static final String API_DESCRIPTION = "System to manage a user's trips and the flights approved by said trip. " +
            "Based on user's role, different APIs are authorized to be used to preserve the integrity of the data. " +
            "Only the admin can make CRUD operations on the users and flights, which are provided by some dummy data during initialization. " +
            "Also the admin can approve or reject submitted trips. In contrast the user can log in with the username provided beforehand, " +
            "and has the possibility of managing it's trips. CRUD operations can be performed on the trips whilst they're statues are still CREATED. " +
            "Once SEND FOR APPROVAL, and APPROVED by the admin, the user may not be able to modify this trip's data anymore, but can book flights for said trip. " +
            "The flights are available to be found by a filter, or suggested automatically by the API by the specific trip data." +
            "</br></br><h3> **Note**: This API requires an API KEY, please log into your account to access your key.";
    public static final String TERM_OF_SERVICE = "Your term of service will go here";
    public static final String API_VERSION = "1.1";
    public static final String LICENSE = "Apache License 2.1.0";
    public static final String LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";
    public static final String SECURE_PATH = "/*/.*";
    public static final String USER_API_TAG = "User Service";
    public static final String FLIGHT_API_TAG = "Flight Service";
    public static final String LOGOUT_API_TAG = "Logout Service";
    public static final String TRIP_API_TAG = "Trip Service";
    public static final String USER_TRIP_API_TAG = "User Trip Service";
    public static final String TRIP_FLIGHT_API_TAG = "Trip Flight Service";

}
